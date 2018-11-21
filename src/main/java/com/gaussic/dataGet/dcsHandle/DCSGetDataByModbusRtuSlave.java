package com.gaussic.dataGet.dcsHandle;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.DeviceDcsPojo;
import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcs.DevicePointRealtimePojo;
import com.gaussic.model.dcsRemote.DcsRemotePointPojo;
import com.gaussic.model.dcs_history.H001Pojo;
import com.gaussic.model.dcs_history.H002Pojo;
import com.gaussic.model.dcs_history.H003Pojo;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.*;
import com.gaussic.repository.dcs_history.H000Rep;
import com.gaussic.repository.dcs_history.H001Rep;
import com.gaussic.repository.dcs_history.H002Rep;
import com.gaussic.repository.dcs_history.H003Rep;
import com.gaussic.service.*;
import com.gaussic.service.dcs.DcsHistoryService;
import com.gaussic.util.modbus4j.Modbus4jSendValues;
import com.gaussic.util.modbus4j.RtuSerialPortWrapper;
import com.serotonin.modbus4j.*;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.serial.SerialPortWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @ClassName DCSGetDataByModbusRtuSlave，当前类在项目启动以后，启动一次
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/19 14:36
 * @Version 1.0
 */
@Component
public class DCSGetDataByModbusRtuSlave implements InitializingBean {
    //public class DCSGetDataByModbusRtuSlave  {
    private static ModbusSlaveSet modbusSlaveSet;
    private static BasicProcessImage processImage;

    @Autowired
    private DcsHistoryService dcsHistoryService;
    /**
     * 是否启动 从站读取
     */
    private static final boolean runSlave = true;

    @Autowired
    private DeviceDcsRepository deviceDcsRepository;
    @Autowired
    private DcsRemotePointRepository dcsRemotePointRepository;
    @Autowired
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private DevicePointRealtimeRepository devicePointRealtimeRepository;
    @Autowired
    private DevicePointRepository devicePointRepository;
    @Autowired
    private TDcsService tDcsService;
    @Autowired
    private DevicePointRealtimeService devicePointRealtimeService;


    private static Map<Integer, Integer> registersDataTypeMap = new HashMap<>();


    private static List<DevicePointPojo> devicePointPojoList = null;
    private static Map<Integer, DevicePointPojo> devicePointPojoMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
//        List<DevicePointPojo> devicePointPojoListAll = devicePointRepository.findAll();
//        devicePointPojoListAll.forEach((DevicePointPojo d) ->{
//            String tableName = "H_";
//            String pointAddress = String.format("%03s",d.getPointAddress());
//            tableName += pointAddress;
//            d.setPointHistoryDeviceTableName(tableName);
//            devicePointRepository.saveAndFlush(d);
//        });


        if (runSlave) {
            devicePointPojoList = devicePointRepository.findByPointNameNotLike("");
            devicePointPojoList.forEach((p) -> {
                Integer address = Integer.parseInt(p.getPointAddress());
                devicePointPojoMap.put(address, p);
            });

            System.out.println("初始化执行一次————————————————————————————————");
            DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1L);
            //TODO 从站地址，固定为1
            int slaveId = 1;
            SerialPortWrapper wrapper = generatorSerialPortWrapper(deviceDcsPojo);
            modbusSlaveSet = new ModbusFactory().createRtuSlave(wrapper);
            processImage = initProcessImage(slaveId);
            modbusSlaveSet.addProcessImage(processImage);

            new Thread(() -> {
                try {
                    modbusSlaveSet.start();
                } catch (ModbusInitException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                while (true) {
                    try {
                        synchronized (modbusSlaveSet) {
                            modbusSlaveSet.wait(500);
                        }
                        for (ProcessImage processImg : modbusSlaveSet.getProcessImages()) {
//                        updateProcessImage((BasicProcessImage) processImg);
                            updateProcessImageFromDb((BasicProcessImage) processImg);
                        }
                    } catch (IllegalDataAddressException | InterruptedException e) {
//            } catch ( InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 封装一个SerialPortWrapper对象，用于生成一个modbusRtuSlave对象
     *
     * @param deviceDcsPojo
     * @return
     */
    private SerialPortWrapper generatorSerialPortWrapper(DeviceDcsPojo deviceDcsPojo) {
        String commPortId = "COM" + deviceDcsPojo.getDevicePort();
//        commPortId = "COM2";
        int baudRate = deviceDcsPojo.getDeviceBoundRate();
        int flowControlIn = deviceDcsPojo.getDeviceFlowControlIn();
        int flowControlOut = deviceDcsPojo.getDeviceFlowControlOut();
        int dataBits = deviceDcsPojo.getDeviceDataBits();
        int stopBits = deviceDcsPojo.getDeviceStopBits();
        int parity = deviceDcsPojo.getDeviceParity();

        //TODO 需要处理 处理结束，2018-10-16，前台页面设置参数，parity的类型为byte，添加后可以正常修改
//        parity = 1;
//        commPortId = "COM1";
//        baudRate = 9600;
//        flowControlIn = 0;
//        flowControlOut = 0;
//        dataBits = 8;
//        stopBits = 1;
//        parity = 1;

        SerialPortWrapper wrapper = new RtuSerialPortWrapper(commPortId, baudRate, flowControlIn, flowControlOut,
                dataBits, stopBits, parity);
        return wrapper;
    }

    /**
     * 初始化ProcessImage
     *
     * @param slaveId
     * @return
     */
    private BasicProcessImage initProcessImage(int slaveId) {
        BasicProcessImage processImage = new BasicProcessImage(slaveId);

        List<DevicePointPojo> devicePointPojoList = devicePointRepository.findAll();
        devicePointPojoList.forEach((p) -> {
            registersDataTypeMap.put(Integer.valueOf(p.getPointAddress()), p.getDataTyper());
        });

        short[] data = new short[1000];
        for (int i = 0; i < data.length; i++) {
            data[i] = 5;
        }
        processImage.setHoldingRegister(0, data);
        processImage.setInputRegister(0, data);
        ProcessImageListener processImageListener = generatorProcessImageLisener();
//        initProcessImageData(processImage);
        processImage.addListener(processImageListener);
        return processImage;

    }

    private void initProcessImageData(BasicProcessImage processImage) {
        for (int i = 100; i < 200; i++) {
            processImage.setHoldingRegister(i, (short) (Math.random() * 100));
        }
    }

    /**
     * 生成一个ProcessImageListener对象，处理DCS发送过来的数据
     *
     * @return
     */
    private ProcessImageListener generatorProcessImageLisener() {
        ProcessImageListener processImageListener = new ProcessImageListener() {
            @Override
            public void coilWrite(int offset, boolean oldValue, boolean newValue) {
//                System.out.println("offset" + offset + ",oldValue:" + oldValue + ",newValue:" + newValue);
            }

            @Override
            public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
//                System.out.println("hodingRegister:offset" + offset + ",oldValue:" + oldValue + ",newValue:" +
//                        newValue);
                try {
                    Integer dataType = registersDataTypeMap.get(offset + 1);
                    int v = getValue(offset, newValue);

                    int value = v;
                    //TODO 临时保存数据到临时数据库
//                tDcsService.saveDcsPoint(offset,v);
                    //更新DCS点的实时数据
                    updateDcsRealTimeValues(offset + 1, v);
                    //更新历史数据库
                    //    dcsHistoryService.save(offset,v,Timestamp.valueOf(LocalDateTime.now()));
                    //TODO 处理DCS发送过来的数据
//                handleAIData( offset, newValue);

                    // 保存DCS发送过来的数据到临时数据表
                    dcsHistoryService.save(offset, new int[]{newValue}, Timestamp.valueOf(LocalDateTime.now()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void holdingRegisterWrite(int offset, short[] value) {
                try {
                    int[] arr = new int[value.length];
                    for (int i = 0; i < value.length; i++) {
                        int v = getValue(offset + i, value[i]);
                        arr[i] = v;
                    }
                    //更新DCS点的实时数据
                    updateDcsRealTimeValues(offset, arr);
                    //更新历史数据库
                /*for(int i=0;i<value.length;i++) {
                    dcsHistoryService.save(offset +i, value[i], Timestamp.valueOf(LocalDateTime.now()));
                }*/
                    // 保存DCS发送过来的数据到临时数据表
                    dcsHistoryService.save(offset, arr, Timestamp.valueOf(LocalDateTime.now()));
//                dcsHistoryService.save(offset ,arr,Timestamp.valueOf(LocalDateTime.now()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        return processImageListener;
    }


    public int getValue(int offset, short newValue) {
        Integer dataType = registersDataTypeMap.get(offset + 1);
        int v = newValue;
        if (null != dataType) {
            if (dataType == DataType.TWO_BYTE_INT_SIGNED) {
//                        System.out.println("有符号 输出值为：" + newValue);
                v = newValue;
            } else if (dataType == DataType.TWO_BYTE_INT_UNSIGNED) {
                String hex = String.format("%x", newValue);
                v = Integer.valueOf(hex, 16);
//                        System.out.println("无符号 输出值为：" + v);
            }
        }
        return v;
    }

    private void updateDcsRealTimeValues(int offset, int[] newValue) {
        offset = offset + 1;
        List<DevicePointRealtimePojo> devicePointRealtimePojoList = new ArrayList<>();
        for (int i = 0; i < newValue.length; i++) {
            DevicePointPojo devicePointPojo = devicePointPojoMap.get(offset + i);
            if (null != devicePointPojo) {
                DevicePointRealtimePojo devicePointRealtimePojo = devicePointPojo.getDevicePointRealtimePojo();
                devicePointRealtimePojo.setPointValue((float) newValue[i]);
                devicePointRealtimePojo.setrTime(Timestamp.valueOf(LocalDateTime.now()));
                devicePointRealtimePojo.setDevicePointPojo(devicePointPojo);
//                devicePointRealtimeRepository.saveAndFlush(devicePointRealtimePojo);
                devicePointRealtimePojoList.add(devicePointRealtimePojo);
            }
        }
        //TODO 批量保存
        try {
            devicePointRealtimeRepository.save(devicePointRealtimePojoList);
//            devicePointRealtimeService.batchUpdate(devicePointRealtimePojoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        devicePointRealtimePojoList.clear();

    }

    /**
     * 更新实时数据
     *
     * @param offset
     * @param newValue
     */
    private void updateDcsRealTimeValues(int offset, int newValue) {
        List<DevicePointPojo> devicePointPojoList = devicePointRepository.findByPointAddress(String.valueOf(offset));
        if (null != devicePointPojoList && devicePointPojoList.size() > 0) {
            DevicePointPojo devicePointPojo = devicePointPojoList.get(0);
            //2、生成一个或者查询到一个devicePointReal，并更更新数据
            DevicePointRealtimePojo devicePointRealtimePojo = null;
            List<DevicePointRealtimePojo> devicePointRealtimePojoList = devicePointRealtimeRepository
                    .findByDevicePointPojo(devicePointPojo);
            if (null != devicePointRealtimePojoList && devicePointRealtimePojoList.size() > 0) {
                devicePointRealtimePojo = devicePointRealtimePojoList.get(0);
            } else {
                devicePointRealtimePojo = new DevicePointRealtimePojo();
            }
            if (null != devicePointRealtimePojo) {
                devicePointRealtimePojo.setPointValue((float) newValue);
                devicePointRealtimePojo.setrTime(Timestamp.valueOf(LocalDateTime.now()));
                devicePointRealtimePojo.setDevicePointPojo(devicePointPojo);
                devicePointRealtimeRepository.saveAndFlush(devicePointRealtimePojo);
            }
        }
    }

    /**
     * 处理DCS发送过来的数据
     *
     * @param offset
     * @param newValue
     */
    private void handleAIData(int offset, short newValue) {
        /**1、根据offset查询数据库中数据，得到一个 devicePointPojo
         * 2、更新devicePointPojo对象对应的devicePointReal的数据
         * 3、根据devicePointPojo这个对象，产生一个DevciePointHistory_Pojo对象【根据当前时间生成】
         * 4、对这个DevciePointHistory_Pojo对象进行赋值。，并保存
         * */
        updateDcsRealTimeValues(offset, newValue);
        //TODO 3、产生一个DevciePointHistory_Pojo对象【根据当前时间生成】
        //TODO 4、对这个DevciePointHistory_Pojo对象进行赋值。，并保存


    }


    public void updatePipeDensityAndVelocity() {
        synchronized (modbusSlaveSet) {
            //TODO slaveId 固定为1
            ProcessImage processImage = modbusSlaveSet.getProcessImage(1);

            List<DcsRemotePointPojo> dcsRemotePointPojos = dcsRemotePointRepository.findAll();
            List<CoalPipingEntity> coalPipingEntityList = coalPipingRepository.findAll();
            updateDcsRemoteValues(dcsRemotePointPojos, coalPipingEntityList);

            dcsRemotePointPojos.forEach((dcsRemotePointPojo -> {
                processImage.setHoldingRegister(Integer.parseInt(dcsRemotePointPojo.getAddress()), dcsRemotePointPojo
                        .getCurrentValue().shortValue());
            }));
        }
    }

    /**
     * @return
     * @Description 更新dcsRemote的数据
     * @Author jiangyong xia
     * @Date 13:29 18/9/20
     * @Param
     **/
    public List<DcsRemotePointPojo> updateDcsRemoteValues(List<DcsRemotePointPojo> dcsRemotePointPojoList, List<CoalPipingEntity>
            coalPipingEntityList) {
        dcsRemotePointPojoList.forEach((dcsRemotePointPojo -> {
            coalPipingEntityList.forEach((coalPipingEntity -> {
                if (coalPipingEntity.getId() == dcsRemotePointPojo.getCoalPipingEntity().getId()) {
                    dcsRemotePointPojo.setCurrTime(coalPipingEntity.getpTime());
                    if (dcsRemotePointPojo.getDensityOrVelocity() == 1) {
                        dcsRemotePointPojo.setCurrentValue(coalPipingEntity.getpVelocity());
                    } else if (dcsRemotePointPojo.getDensityOrVelocity() == 0) {
                        dcsRemotePointPojo.setCurrentValue(coalPipingEntity.getpDencity());
                    }
                    dcsRemotePointRepository.saveAndFlush(dcsRemotePointPojo);
                }
            }));
        }));
        return dcsRemotePointPojoList;
    }

    void updateProcessImage(BasicProcessImage processImage) throws IllegalDataAddressException {

//        int hr16Value = processImage.getNumeric(RegisterRange.HOLDING_REGISTER, 16, DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED).intValue();
//        if(hr16Value != twoByteIntUnsignedSwapped){
//            throw new RuntimeException("Test failed on TWO_BYTE_INT_UNSIGNED_SWAPPED. Expected " + twoByteIntUnsignedSwapped + " but was: " + hr16Value);
//        }
        processImage.setHoldingRegister(0, (short) (Math.random() * 100));
        for (int i = 0; i < 1000; i++) {
            processImage.setHoldingRegister(1 + i, (short) (Math.random() * 100));
            processImage.setInputRegister(1 + i, (short) (Math.random() * 100));
//            processImage.setHoldingRegister(startOffsetForImage1+i, (short) 0);
//            processImage.setInputRegister(startOffsetForImage1 + i, (short) 0);
        }
//        System.out.println("更新数据" + (i++));


    }

    /**
     * 更新数据，从数据库中读取的数据
     *
     * @param processImage
     */
    private void updateProcessImageFromDb(BasicProcessImage processImage) throws IllegalDataAddressException {
        try {
            List<DcsRemotePointPojo> dcsRemotePointPojoList = dcsRemotePointRepository.findAll();
            for (DcsRemotePointPojo dcsRemotePointPojo : dcsRemotePointPojoList) {
//            float densityOrVelocity = dcsRemotePointPojo.getDensityOrVelocity();
//            CoalPipingEntity coalPipingEntity = dcsRemotePointPojo.getCoalPipingEntity();

//            Float value = 0f;
//            if(0 == densityOrVelocity){
//                 value = coalPipingEntity.getpVelocity();
//            }else if(1 == densityOrVelocity){
//                 value = coalPipingEntity.getpDencity();
//            }
//            dcsRemotePointPojo.setCurrentValue(value);
//            dcsRemotePointPojo.setCurrTime(coalPipingEntity.getpTime());
//            dcsRemotePointRepository.saveAndFlush(dcsRemotePointPojo);
                float value = dcsRemotePointPojo.getCurrentValue();
                //经过处理发送到DCS
                short[] currentValue = DCSDataHandle.sendData(value);

//            System.out.println("value:"+ currentValue[0]+","+currentValue[1]);
//                System.out.println("currentValue:" + currentValue[0] + "," + currentValue[1]);
                int currentAddress = Integer.parseInt(dcsRemotePointPojo.getAddress());
//                System.out.println("currentAddress:" + currentAddress + ",currentValue.length:" + currentValue.length);
                currentValue[1] = 0;
                currentValue[0] = 5500;

                short v = DCSDataHandle.handleValue(value);
                processImage.setHoldingRegister(currentAddress, v);


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
