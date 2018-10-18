package com.gaussic.dataGet.dcsHandle;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.DeviceDcsPojo;
import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcs.DevicePointRealtimePojo;
import com.gaussic.model.dcsRemote.DcsRemotePointPojo;
import com.gaussic.repository.*;
import com.gaussic.service.TDcsService;
import com.gaussic.util.modbus4j.Modbus4jSendValues;
import com.gaussic.util.modbus4j.RtuSerialPortWrapper;
import com.serotonin.modbus4j.*;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.serial.SerialPortWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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

    /**是否启动 从站读取*/
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

    @Override
    public void afterPropertiesSet() {
        if(runSlave) {
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

        SerialPortWrapper wrapper = new RtuSerialPortWrapper(commPortId, baudRate, flowControlIn, flowControlOut,
                dataBits, stopBits, parity);
        return wrapper;
    }

    private BasicProcessImage initProcessImage(int slaveId) {
        BasicProcessImage processImage = new BasicProcessImage(slaveId);
        short[] data = new short[1000];
        for(int i=0;i<data.length;i++){
            data[i ] = 0;
        }
        processImage.setHoldingRegister(0, data);
        processImage.setInputRegister(0,data);
        ProcessImageListener processImageListener = generatorProcessImageLisener();
//        initProcessImageData(processImage);
        processImage.addListener(processImageListener);
        return processImage;

    }

    private void initProcessImageData(BasicProcessImage processImage) {
        for(int i=100;i<200;i++){
            processImage.setHoldingRegister(i,(short)(Math.random()* 100));
        }
    }

    private ProcessImageListener generatorProcessImageLisener() {
        ProcessImageListener processImageListener = new ProcessImageListener() {
            @Override
            public void coilWrite(int offset, boolean oldValue, boolean newValue) {
                System.out.println("offset" + offset + ",oldValue:" + oldValue + ",newValue:" + newValue);
            }

            @Override
            public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
                System.out.println("hodingRegister:offset" + offset + ",oldValue:" + oldValue + ",newValue:" +
                        newValue);
                //TODO 临时保存数据到临时数据库
                tDcsService.saveDcsPoint(offset,newValue);
                //TODO 处理DCS发送过来的数据
//                handleAIData( offset, newValue);
//
//
//                List<DevicePointPojo> deviceDcsPojoList = devicePointRepository.findByPointAddress(String.valueOf(offset));
//                if(null != deviceDcsPojoList &&deviceDcsPojoList.size() >0) {
//                    DevicePointPojo devicePointPojo = deviceDcsPojoList.get(0);
//                    List<DevicePointRealtimePojo> devicePointRealtimePojoList = devicePointRealtimeRepository
//                            .findByDevicePointPojo(devicePointPojo);
//                    if(null != devicePointRealtimePojoList && devicePointRealtimePojoList.size() >0){
//                        DevicePointRealtimePojo devicePointRealtimePojo = devicePointRealtimePojoList.get(0);
//                        //TODO 时间需要进行设定
////                        devicePointRealtimePojo.setrTime();
//                        devicePointRealtimePojo.setPointValue((float) newValue);
//                        devicePointRealtimeRepository.saveAndFlush(devicePointRealtimePojo);
//                        //TODO 保存历史数据，未编写
//                    }
//                }
            }
        };
        return processImageListener;
    }

    /**
     * 处理DCS发送过来的数据
     * @param offset
     * @param newValue
     */
    private void handleAIData(int offset, short newValue) {
        /**1、根据offset查询数据库中数据，得到一个 devicePointPojo
         * 2、更新devicePointPojo对象对应的devicePointReal的数据
         * 3、根据devicePointPojo这个对象，产生一个DevciePointHistory_Pojo对象【根据当前时间生成】
         * 4、对这个DevciePointHistory_Pojo对象进行赋值。，并保存
         * */
        //1、根据offset查询数据库中数据，得到一个 devicePointPojo
        List<DevicePointPojo> devicePointPojoList = devicePointRepository.findByPointAddress(String.valueOf(offset));
        if(null != devicePointPojoList && devicePointPojoList.size() > 0){
            DevicePointPojo devicePointPojo = devicePointPojoList.get(0);
            //2、生成一个或者查询到一个devicePointReal，并更更新数据
            DevicePointRealtimePojo devicePointRealtimePojo = null;
            List<DevicePointRealtimePojo> devicePointRealtimePojoList = devicePointRealtimeRepository
                    .findByDevicePointPojo(devicePointPojo);
            if(null != devicePointRealtimePojoList &&devicePointRealtimePojoList.size() > 0){
                devicePointRealtimePojo = devicePointRealtimePojoList.get(0);
            }else{
                devicePointRealtimePojo = new DevicePointRealtimePojo();
            }
            if(null != devicePointRealtimePojo) {
                devicePointRealtimePojo.setPointValue((float) newValue);
                devicePointRealtimePojo.setrTime(Timestamp.valueOf(LocalDateTime.now()));
                devicePointRealtimePojo.setDevicePointPojo(devicePointPojo);
                devicePointRealtimeRepository.saveAndFlush(devicePointRealtimePojo);
            }
            //TODO 3、产生一个DevciePointHistory_Pojo对象【根据当前时间生成】
            //TODO 4、对这个DevciePointHistory_Pojo对象进行赋值。，并保存

        }
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
        processImage.setHoldingRegister(0,(short)(Math.random() * 100));
        for(int i=0;i<1000;i++){
            processImage.setHoldingRegister(1+i,(short)(Math.random() * 100));
            processImage.setInputRegister(1 + i,(short)(Math.random() * 100));
//            processImage.setHoldingRegister(startOffsetForImage1+i, (short) 0);
//            processImage.setInputRegister(startOffsetForImage1 + i, (short) 0);
        }
//        System.out.println("更新数据" + (i++));


    }

    /**
     * 更新数据，从数据库中读取的数据
     * @param processImage
     */
    private void updateProcessImageFromDb(BasicProcessImage processImage) throws IllegalDataAddressException {
        List<DcsRemotePointPojo> dcsRemotePointPojoList = dcsRemotePointRepository.findAll();
        for(DcsRemotePointPojo dcsRemotePointPojo:dcsRemotePointPojoList){
            int densityOrVelocity = dcsRemotePointPojo.getDensityOrVelocity();
            CoalPipingEntity coalPipingEntity = dcsRemotePointPojo.getCoalPipingEntity();
            Float value = 0f;
            if(0 == densityOrVelocity){
                 value = coalPipingEntity.getpVelocity();
            }else if(1 == densityOrVelocity){
                 value = coalPipingEntity.getpDencity();
            }
            dcsRemotePointPojo.setCurrentValue(value);
            dcsRemotePointPojo.setCurrTime(coalPipingEntity.getpTime());
            dcsRemotePointRepository.saveAndFlush(dcsRemotePointPojo);

            short[] currentValue = DCSDataHandle.sendData(value);
//            System.out.println("value:"+ currentValue);
//            System.out.println("currentValue:" + currentValue);
            int currentAddress = Integer.parseInt(dcsRemotePointPojo.getAddress());
            processImage.setHoldingRegister(currentAddress,currentValue);




        }
    }

}
