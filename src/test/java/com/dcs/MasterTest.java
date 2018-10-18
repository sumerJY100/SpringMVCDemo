package com.dcs;

import com.gaussic.util.modbus4j.RtuSerialPortWrapper;
import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;

/**
 * @ClassName MasterTest
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/5 15:55
 * @Version 1.0
 */
public class MasterTest {
    private static final String comPortId = "COM" + 2;
    private static final int baundRate = 9600,
            flowControlIn = 0,
            flowControlOut = 0,
            dataBits = 8,
            stopBits = 0,
            parity = 0;
    private static final int slaveId = 1,
            writeStartOffSet = 144,
            writeDataSize = 10;
    private static final int readOffSet = 100,
            readSize = 10;

    public static void main(String[] args) {
        new MasterTest().testSendValue();
    }

    private void testSendValue() {
        RtuSerialPortWrapper wrapper = generatoreWrapper();
        ModbusMaster master = new ModbusFactory().createRtuMaster(wrapper);
        master.setTimeout(500);
        try {
            master.init();
            /**接收数据*/
            BatchRead<String> batchRead = getBaseLocators();
            BatchResults<String> result = master.send(batchRead);
            outReadData(result);
            /***发送数据*/
//            short[] shortArray = getShortData(writeDataSize);
//            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, writeStartOffSet, shortArray);
//            ModbusResponse response = master.send(request);
//            outWriteData(shortArray);
            /**关闭连接*/
            master.destroy();
        } catch (ModbusTransportException | ModbusInitException e) {
            e.printStackTrace();
        }
        catch( ErrorResponseException e){
            e.printStackTrace();
        }
    }

    private void outWriteData(short[] shortArray) {
        int size = shortArray.length;
        for (int i = 0; i < size; i++) {
            String offSet = String.valueOf(writeStartOffSet + i);
            System.out.println("W_offset:" + offSet + "---V:" + shortArray[i]);
        }
    }

    /**
     * 输出读取的数据
     */
    private void outReadData(BatchResults<String> result) {
        for (int i = 0; i < readSize; i++) {
            String key = String.valueOf(readOffSet + i);
            int v = result.getIntValue(key);
            System.out.println("R_offset:" + key + "---V: " + v);
        }
    }

    private RtuSerialPortWrapper generatoreWrapper() {
        return new RtuSerialPortWrapper(comPortId, baundRate, flowControlIn, flowControlOut,
                dataBits, stopBits, parity);
    }

    private BatchRead<String> getBaseLocators() {
        BatchRead<String> batch = new BatchRead<>();
        for (int i = 0; i < readSize; i++) {
            int offSet = readOffSet + i;
            BaseLocator<Number> l1 = getBaseLocator(slaveId, offSet);
            batch.addLocator(String.valueOf(offSet), l1);
        }
        return batch;
    }

    private BaseLocator<Number> getBaseLocator(int slaveId, int offset) {
        return BaseLocator.holdingRegister(slaveId, offset, DataType.TWO_BYTE_INT_UNSIGNED);
    }

    private short[] getShortData(int size) {
        short[] shortArray = new short[size];
        for (int i = 0; i < size; i++) {
            shortArray[i] = (short) (Math.random() * 100);
        }
        return shortArray;
    }
}
