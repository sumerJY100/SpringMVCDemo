package com.dcs2;

import com.gaussic.dataGet.dcsHandle.DCSDataHandle;
import com.gaussic.util.modbus4j.RtuSerialPortWrapper;
import com.serotonin.modbus4j.BasicProcessImage;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.ProcessImageListener;
import com.serotonin.modbus4j.base.SlaveAndRange;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import org.hibernate.type.DateType;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SlaveTest
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/20 9:26
 * @Version 1.0
 */
public class SlaveTest {

    private static final String commPortId = "COM1";
    private static final int baudRate = 9600,
            flowControlIn = 0,
            flowControlOut = 0,
            dataBits = 8,
            stopBits = 1,
            parity = 1;
    /**
     * 主站读取与写入的数据，需要全部初始化
     */
    private static final int slaveId = 1,
            startOffsetForImage1 = 0,
            dataSizeForImage1 = 1000;


    static Map<Integer,Integer> dataTypeMap = new HashMap<>();
    static{
        dataTypeMap.put(2,DataType.TWO_BYTE_INT_UNSIGNED);
        dataTypeMap.put(1,DataType.TWO_BYTE_INT_SIGNED);
    }

    public static void main(String[] args) {

        RtuSerialPortWrapper wrapper = getWrapper();

        ModbusSlaveSet rtuSlave = new ModbusFactory().createRtuSlave(wrapper);
        BasicProcessImage processImage2 = generatorProcessImage(startOffsetForImage1,dataSizeForImage1);

        rtuSlave.addProcessImage(processImage2);

        new Thread(() -> {
            try {
                rtuSlave.start();
            } catch (ModbusInitException e) {
                e.printStackTrace();
            }
        }).start();

        /*while (true) {
            try {
                synchronized (rtuSlave) {
                    rtuSlave.wait(500);
                }
                for (ProcessImage processImg : rtuSlave.getProcessImages()) {
                    updateProcessImage((BasicProcessImage) processImg);
                }
            } catch (IllegalDataAddressException | InterruptedException e) {
//            } catch ( InterruptedException e) {
                e.printStackTrace();
            }
        }*/

    }

    private static BasicProcessImage generatorProcessImage(int startOffsetForImage, int dataSizeForImage) {
        BasicProcessImage processImage = new BasicProcessImage(slaveId);
        short[] readDataArray = generatorDCSReadData(dataSizeForImage);
        //AO

        processImage.setHoldingRegister(startOffsetForImage, readDataArray);
        for(int i=0;i<dataSizeForImage;i++){
            processImage.setHoldingRegister(startOffsetForImage + i, DCSDataHandle.sendData(1));

            int dataType = DataType.TWO_BYTE_INT_SIGNED;
//            int dataType = DataType.TWO_BYTE_INT_UNSIGNED;
//            dataType = DataType.Two


            Number v = readDataArray[i];

//            v = 65535;

            processImage.setNumeric(RegisterRange.HOLDING_REGISTER,startOffsetForImage+i,dataType,v);
            processImage.setNumeric(RegisterRange.INPUT_REGISTER,startOffsetForImage+i,dataType,v);
        }
        //AI
//        for(int i=0;i<dataSizeForImage;i++){
//            processImage.setInputRegister(startOffsetForImage + i, DCSDataHandle.sendData(-10));
//        }
        ProcessImageListener processImageListener = generatoerProcessImageListener();
        processImage.addListener(processImageListener);
        return processImage;
    }











    private static short[] generatorDCSReadData(int dataSizeForImage) {
        short[] shortArray = new short[dataSizeForImage];
        for (int i = 0; i < dataSizeForImage; i++) {
            //TODO 所有的数据初始化为55
            shortArray[i] = 5;
        }
        return shortArray;
    }




    private static ProcessImageListener generatoerProcessImageListener() {
        return new ProcessImageListener() {
            @Override
            public void coilWrite(int offset, boolean oldValue, boolean newValue) {
                System.out.println("offset" + offset + ",oldValue:" + oldValue + ",newValue:" + newValue);
            }
            @Override
            public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
                try {
                    Integer dataType = dataTypeMap.get(offset);
                    if(null != dataType) {
                        if (dataType == DataType.TWO_BYTE_INT_SIGNED) {
                            System.out.println("有符号 输出值为：" + newValue);
                        } else if (dataType == DataType.TWO_BYTE_INT_UNSIGNED) {
                            String hex = String.format("%x", newValue);
                            Integer v = Integer.valueOf(hex, 16);
                            System.out.println("无符号 输出值为：" + v);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("offset" + offset + ",oldValue:" + oldValue + ",newValue:" + newValue);
            }

            @Override
            public void holdingRegisterWrite(int offset, short[] value) {

            }
        };
    }


    private static RtuSerialPortWrapper getWrapper() {
        return new RtuSerialPortWrapper(commPortId, baudRate, flowControlIn, flowControlOut, dataBits, stopBits, parity);
    }
}
