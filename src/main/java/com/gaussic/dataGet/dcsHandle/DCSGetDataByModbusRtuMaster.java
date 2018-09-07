package com.gaussic.dataGet.dcsHandle;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.*;
import com.gaussic.model.dcsRemote.DcsRemotePointPojo;
import com.gaussic.repository.*;
import com.gaussic.util.modbus4j.Modbus4jRtuMasterUtil;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName DCSGetDataByModbusRtuMaster
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/3 11:18
 * @Version 1.0
 */
@Component
public class DCSGetDataByModbusRtuMaster {
    @Autowired
    private DevicePointRealtimeRepository devicePointRealtimeRepository;
    @Autowired
    private DevicePointHistory1Repository devicePointHistory1Repository;
    @Autowired
    private DevicePointHistory2Repository devicePointHistory2Repository;
    @Autowired
    private DevicePointHistory3Repository devicePointHistory3Repository;
    @Autowired
    private DevicePointHistory4Repository devicePointHistory4Repository;
    @Autowired
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private DcsRemotePointRepository dcsRemotePointRepository;

    public static ModbusMaster modbusMaster;

    /**
     * 单例获取modbusMaster对象
     *
     * @return
     */
    public static synchronized ModbusMaster getModBusMaster(DeviceDcsPojo deviceDcsPojo) {
//        LocalTime now = LocalTime.now();
//        System.out.println("modbusMaster: geneator++++++++" + modbusMaster + ",      " + now);
        //TODO 连接出现异常时进行处理
        //TODO 出现异常的时候，需要记录日志
        if (modbusMaster == null) {
//            DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1l);

            modbusMaster = Modbus4jRtuMasterUtil.getModbusRtuMaster(deviceDcsPojo);

            try {
                modbusMaster.init();
//            } catch(PortInUseException e){
//                e.printStackTrace();
            } catch (ModbusInitException e) {
                //TODO 记录 创建 modbusmaster 失败 ，返回 null
                System.out.println("modbus 生成异常" + e.getClass().getName());
                String trackMessage = e.getMessage();
                if (trackMessage.contains("PortInUseException")) {
                    //todo 端口被占用异常处理
                    System.out.println("端口被占用");
                }
                if (trackMessage.contains("NoSuchPortException")) {
                    //TODO 没有相应的端口异常处理，记录日志，记录到告警信息
                    System.out.println("没有相应的端口映射");
                }
                modbusMaster = null;
                e.printStackTrace();
            }
        }
//        System.out.println("modbusMaster: geneator end-------" + modbusMaster + ",            " + now);
        return modbusMaster;
    }

    /**
     * @return
     * @Description DCS数据，读取数据
     * @Author jiangyong xia
     * @Date 10:57 18/9/3
     * @Param
     **/
    public void handleDCS(DeviceDcsPojo deviceDcsPojo, LocalDateTime localDateTime) {
        //一、读取DCS的数据
        //01-查询DCS表
        //02-根据DCS表，查询此设备的点设备地址
        //03-根据设备信息，点号，读取数据
        //04-分析数据，录入实时数据表
        //05-分析数据，录入历史数据库
        //二、写数据到DCS
        //1、读取数据库中的pipe实时风速与密度
        //2、将数据发送给DCS


        try {
            CoalPipingEntity coalPipingEntity = coalPipingRepository.findOne(11L);
//            DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1l);
            List<DevicePointPojo> devicePointPojoList = deviceDcsPojo.getDevicePointPojoList();
            devicePointPojoList.stream().forEach((point) -> {
                point.getDevicePointRealtimePojo();
            });
            int slaveId = Integer.parseInt(deviceDcsPojo.getDeviceAddress());
            DevicePointHistory1Pojo devicePointHistory1Pojo = new DevicePointHistory1Pojo(localDateTime);
            DevicePointHistory2Pojo devicePointHistory2Pojo = new DevicePointHistory2Pojo(localDateTime);
            DevicePointHistory3Pojo devicePointHistory3Pojo = new DevicePointHistory3Pojo(localDateTime);
            DevicePointHistory4Pojo devicePointHistory4Pojo = new DevicePointHistory4Pojo(localDateTime);
            new Thread(() -> {
                handelDcsData(deviceDcsPojo, devicePointPojoList, slaveId, localDateTime, devicePointHistory1Pojo, devicePointHistory2Pojo, devicePointHistory3Pojo, devicePointHistory4Pojo);
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param devicePointPojoList
     * @param slaveId
     * @param localDateTime
     * @param devicePointHistory1Pojo
     * @param devicePointHistory2Pojo
     * @param devicePointHistory3Pojo
     * @param devicePointHistory4Pojo
     * @Description 处理DCS数据，线程处理
     */
    private void handelDcsData(DeviceDcsPojo deviceDcsPojo, List<DevicePointPojo> devicePointPojoList, int slaveId, LocalDateTime localDateTime, DevicePointHistory1Pojo devicePointHistory1Pojo, DevicePointHistory2Pojo devicePointHistory2Pojo, DevicePointHistory3Pojo devicePointHistory3Pojo, DevicePointHistory4Pojo devicePointHistory4Pojo) {
//        DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1L);
        try {
            ModbusMaster modBusMaster = DCSGetDataByModbusRtuMaster.getModBusMaster(deviceDcsPojo);
//        List<DevicePointRealtimePojo> devicePointRealtimePojoList = new ArrayList<>();
            /********读取数据*********/
            getValue(modBusMaster,devicePointPojoList,slaveId,localDateTime,devicePointHistory1Pojo, devicePointHistory2Pojo,devicePointHistory3Pojo,devicePointHistory4Pojo);
            /********设置数据******************/
            sendValue(modBusMaster);

        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void getValue(ModbusMaster modBusMaster,List<DevicePointPojo> devicePointPojoList, int slaveId, LocalDateTime localDateTime, DevicePointHistory1Pojo devicePointHistory1Pojo, DevicePointHistory2Pojo devicePointHistory2Pojo, DevicePointHistory3Pojo devicePointHistory3Pojo, DevicePointHistory4Pojo devicePointHistory4Pojo){
        if (null != modBusMaster) {
            //TODO 使用线程锁定，线程的数量需要进行限制，当DCS的点数量多时，使用了一个modbus master 对象，会造成卡死现象。
            CountDownLatch countDownLatch = new CountDownLatch(devicePointPojoList.size());
            devicePointPojoList.stream().forEach((point) -> {

                if (null == point.getDevicePointRealtimePojo()) {
                    DevicePointRealtimePojo devicePointRealtimePojoTemp = new DevicePointRealtimePojo();
                    devicePointRealtimePojoTemp.setDevicePointPojo(point);
                    devicePointRealtimePojoTemp.setrTime(Timestamp.valueOf(localDateTime));
                    devicePointRealtimeRepository.saveAndFlush(devicePointRealtimePojoTemp);
                    point.setDevicePointRealtimePojo(devicePointRealtimePojoTemp);
                }
                DevicePointRealtimePojo devicePointRealtimePojo = point.getDevicePointRealtimePojo();
                new Thread(() -> {
                    try {
                        //TODO 数据读取异常时，需要进行异常处理
                        BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, Integer.parseInt(point.getPointAddress()), DataType.TWO_BYTE_INT_UNSIGNED);
                        Number pointValue = modBusMaster.getValue(loc);
                        //更新实时数据

                        devicePointRealtimePojo.setPointValue(pointValue.floatValue());
                        devicePointRealtimePojo.setrTime(Timestamp.valueOf(localDateTime));
                        //TODO 更新DCS的实时数据
                        devicePointRealtimeRepository.saveAndFlush(devicePointRealtimePojo);
                        //TODO 数据需要保存到数据库【未编写完成】
//                        devicePointRealtimePojoList.add(devicePointRealtimePojo);
                        //TODO 更新历史数据的实体类，未更新到数据库
                        DeviceDcsUtil.updateHistoryDate(point, pointValue, devicePointHistory1Pojo, devicePointHistory2Pojo,
                                devicePointHistory3Pojo, devicePointHistory4Pojo);

                    } catch (ModbusTransportException e) {
//                        System.out.println("数据返回错误");
                        String trackMessage = e.getMessage();
                        if (trackMessage.contains("TimeoutException")) {
                            //TODO 数据读取超时异常处理
//                            System.out.println("数据读取超时异常");
                        }
//                            e.printStackTrace();
                        System.out.println("异常处理");
                    } catch (ErrorResponseException e) {
                        //todo 异常处理
//                        System.out.println("ErrorResponseException 异常");
                        e.printStackTrace();
                    }

                    countDownLatch.countDown();
                }).start();
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //TODO 更新DCS数据的实时数据
//        DevicePointRealtimeRepository.update
        //TODO 当modbusmaster返回为null是，需要将DCS数据，默认为0 录入到数据库中。
        devicePointHistory1Repository.saveAndFlush(devicePointHistory1Pojo);
        devicePointHistory2Repository.saveAndFlush(devicePointHistory2Pojo);
        devicePointHistory3Repository.saveAndFlush(devicePointHistory3Pojo);
        devicePointHistory4Repository.saveAndFlush(devicePointHistory4Pojo);

    }

    public void sendValue(ModbusMaster modBusMaster){
        try {
            if (null != modBusMaster) {
                List<DcsRemotePointPojo> dcsRemotePointPojos = dcsRemotePointRepository.findAll();
                dcsRemotePointPojos.stream().forEach((dcsRemotePointPojo) -> {
                    CoalPipingEntity coalPipingEntity = dcsRemotePointPojo.getCoalPipingEntity();
                    //TODO 初始化，录入数据到数据库
                    if (null != coalPipingEntity) {
                        dcsRemotePointPojo.setCurrTime(coalPipingEntity.getpTime());
                        if (dcsRemotePointPojo.getDensityOrVelocity() == 1) {
                            dcsRemotePointPojo.setCurrentValue(coalPipingEntity.getpVelocity());
                        } else if (dcsRemotePointPojo.getDensityOrVelocity() == 0) {
                            dcsRemotePointPojo.setCurrentValue(coalPipingEntity.getpDencity());
                        }
                        dcsRemotePointRepository.saveAndFlush(dcsRemotePointPojo);
                    }
                    //TODO 新建数据库表，DCS写入地址
                    //TODO 读取数据库总数据
                    //todo 采集粉管数据时，将实时数据录入到DCS实时数据中
                    int address = Integer.parseInt(dcsRemotePointPojo.getAddress());
                    int slaveIdForRemote = dcsRemotePointPojo.getSlaveId();
                    BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveIdForRemote, address, DataType.TWO_BYTE_INT_UNSIGNED);
                    try {
                        /*******************设置浓度与密度到远端DCS********************************/
                        modBusMaster.setValue(loc, dcsRemotePointPojo.getCurrentValue());
                    } catch (ModbusTransportException e) {
                        e.printStackTrace();
                    } catch (ErrorResponseException e) {
                        e.printStackTrace();
                    }

                });
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
