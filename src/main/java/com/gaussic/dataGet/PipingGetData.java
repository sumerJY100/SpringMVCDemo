package com.gaussic.dataGet;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.*;
import com.gaussic.model.history.CoalPipingHistory;

import com.gaussic.repository.*;
import com.gaussic.service.CoalMillService;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.CoalPipingService;

import com.gaussic.util.DeviceDcsUtil;
import com.gaussic.util.modbus4j.Modbus4jRtuMasterUtil;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class PipingGetData extends Thread {
    @Resource
    private CoalPipingRepository coalPipingRepository;
    @Resource
    private CoalMillRepository coalMillRepository;
    @Autowired
    private CoalMillPipingSetRepository coalMillPipingSetRepository;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;
    @Autowired
    private CoalPipingService coalPipingService;
    @Autowired
    private CoalMillService coalMillService;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;
    @Autowired
    private DeviceDcsRepository deviceDcsRepository;
    @Autowired
    private DevicePointRepository devicePointRepository;
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

    @Override
    public void run() {
        this.getData();
    }

    private static  ModbusMaster modbusMaster;

    /**
     * 单例获取modbusMaster对象
     * @return
     */
    public synchronized  ModbusMaster getModBusMaster(){
        System.out.println("modbusMaster: " +modbusMaster);
        //TODO 连接出现异常时进行处理
        //TODO 出现异常的时候，需要记录日志
        if(modbusMaster == null){
            DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1l);

            modbusMaster = Modbus4jRtuMasterUtil.getModbusRtuMaster(deviceDcsPojo);
            try {
                modbusMaster.init();
            } catch (ModbusInitException e) {
                e.printStackTrace();
            }
        }
        return modbusMaster;
    }



    public void getData() {
        List<CoalMillEntity> coalMillEntityList = null;
        try {
            coalMillEntityList = coalMillService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date now = new Date();

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("数据采集DCS");
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
            DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1l);
            List<DevicePointPojo> devicePointPojoList = deviceDcsPojo.getDevicePointPojoList();

            ModbusMaster modbusMaster = this.getModBusMaster();

            int slaveId = Integer.parseInt(deviceDcsPojo.getDeviceAddress());
            DevicePointHistory1Pojo devicePointHistory1Pojo = new DevicePointHistory1Pojo(localDateTime);
            DevicePointHistory2Pojo devicePointHistory2Pojo = new DevicePointHistory2Pojo(localDateTime);
            DevicePointHistory3Pojo devicePointHistory3Pojo = new DevicePointHistory3Pojo(localDateTime);
            DevicePointHistory4Pojo devicePointHistory4Pojo = new DevicePointHistory4Pojo(localDateTime);
            devicePointPojoList.stream().forEach((point) -> {
                BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, Integer.parseInt(point.getPointAddress()), DataType.TWO_BYTE_INT_UNSIGNED);
                try {
                    Number pointValue = modbusMaster.getValue(loc);
                    //更新实时数据
                    DevicePointRealtimePojo devicePointRealtimePojo = point.getDevicePointRealtimePojo();
                    devicePointRealtimePojo.setPointValue(pointValue.floatValue());
                    devicePointRealtimePojo.setrTime(Timestamp.valueOf(localDateTime));
                    devicePointRealtimeRepository.saveAndFlush(devicePointRealtimePojo);
                    //更新历史数据库数据
                    DeviceDcsUtil.updateHistoryDate(point,pointValue,devicePointHistory1Pojo,devicePointHistory2Pojo,
                            devicePointHistory3Pojo,devicePointHistory4Pojo);

                } catch (ModbusTransportException e) {
                    e.printStackTrace();
                } catch (ErrorResponseException e) {
                    e.printStackTrace();
                }

            });
            devicePointHistory1Repository.saveAndFlush(devicePointHistory1Pojo);
            devicePointHistory2Repository.saveAndFlush(devicePointHistory2Pojo);
            devicePointHistory3Repository.saveAndFlush(devicePointHistory3Pojo);
            devicePointHistory4Repository.saveAndFlush(devicePointHistory4Pojo);


            List<CoalPipingEntity> coalPipingEntityList = coalPipingRepository.findAll();
            coalPipingEntityList.stream().forEach((pipe)->{
                //TODO 新建数据库表，DCS写入地址
                //TODO 读取数据库总数据
                //todo 采集粉管数据时，将实时数据录入到DCS实时数据中
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //TODO 后期进行历史数据优化，例如长时间没有数据，可以对数据进行删除，或记录数据的状态。在查询的时候，不至于某一段时间没有数据
        //生成四条历史记录，每台磨煤机一条历史记录
        try {
            List<CoalPipingEntity> coalPipingEntityListAll = new ArrayList<>();
            final CountDownLatch countDownLatch = new CountDownLatch(16);
            List<CoalPipingHistory> coalPipingHistorieList = new ArrayList<>();
            for (CoalMillEntity coalMillEntity : coalMillEntityList) {
                //生成一条coalPipingHistory数据
                CoalPipingHistory coalPipingHistory = coalPipingHistoryService.generatorHistory(coalMillEntity, now);
                coalPipingHistorieList.add(coalPipingHistory);
                List<CoalPipingEntity> coalPipingEntityList_temp = coalMillEntity.getCoalPipingEntityList();

                for (int m = 0; m < coalPipingEntityList_temp.size(); m++) {
                    CoalPipingEntity coalPipingEntity = coalPipingEntityList_temp.get(m);
                    String url = coalPipingEntity.getCoalPipingSetEntity().getsUrl();
                    final String urlFinal = new String(url);
                    new Thread(() -> {
                        PipingGetSingleDataThread pipingGetSingleDataThread = new PipingGetSingleDataThread();
                        pipingGetSingleDataThread.setCoalPipingEntity(coalPipingEntity);
                        pipingGetSingleDataThread.setNow(now);
                        //采集数据，并更新实时数据【已经更新保存到数据库】
                        pipingGetSingleDataThread.updateData(urlFinal);
                        //更新历史数据，根据piping的location进行判定
                        coalPipingHistoryService.updateHistory(coalPipingEntity, coalPipingHistory);
                        //TODO 历史数据录入，进行判定告警信息
                        countDownLatch.countDown();
                    }).start();

                }

//                coalPipingHistoryService.updateHistory(coalPipingEntityList_temp, coalPipingHistory);
            }

            //16个线程结束以后执行的代码
            countDownLatch.await();

            //更新 coalPipingEntity,更新coalPipingHistory数据
            for (CoalPipingHistory coalPipingHistory : coalPipingHistorieList) {
                coalPipingHistoryService.updateCoalPipingHistory(coalPipingHistory.gethCoalMillId(), coalPipingHistory);
            }
            for (CoalMillEntity coalMillEntity : coalMillEntityList) {
                List<CoalPipingEntity> coalPipingEntityList = coalMillEntity.getCoalPipingEntityList();
                for (CoalPipingEntity coalPipingEntity : coalPipingEntityList) {
                    coalPipingRepository.saveAndFlush(coalPipingEntity);
                }
            }

//            coalPipingRepository.batchUpate(coalPipingEntityListAll);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
