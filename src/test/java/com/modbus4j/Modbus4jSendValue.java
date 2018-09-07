package com.modbus4j;

import com.gaussic.util.modbus4j.RtuSerialPortWrapper;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;

/**
 * @ClassName Modbus4jSendValue
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/5 15:55
 * @Version 1.0
 */
public class Modbus4jSendValue {
    public static void main(String[] args) {
        new Modbus4jSendValue().testSendValue();
    }

    public void testSendValue(){
        String comPortId = "COM" + 2;
//        comPortId = "COM9";
//        int baudRate = deviceDcsPojo.getDeviceBoundRate();
//        int flowControlIn = deviceDcsPojo.getDeviceFlowControlIn();
//        int flowControlOut = deviceDcsPojo.getDeviceFlowControlOut();
//        int dataBits = deviceDcsPojo.getDeviceDataBits();
//        int stopBits = deviceDcsPojo.getDeviceStopBits();
//        int parity = deviceDcsPojo.getDeviceParity();

        int baundRate = 9600;
        int flowControlIn = 0;
        int flowControlOut = 0;
        int dataBits = 8;
        int stopBits = 0;
        int parity = 0;

        RtuSerialPortWrapper wrapper = new RtuSerialPortWrapper(comPortId, baundRate, flowControlIn, flowControlOut,
                dataBits, stopBits, parity);

        ModbusMaster master = new ModbusFactory().createRtuMaster(wrapper);
        try {
            master.init();
        } catch (ModbusInitException e) {
            e.printStackTrace();
        }
        BaseLocator<Number> baseLocator =BaseLocator.holdingRegister(1, 104, DataType.TWO_BYTE_INT_UNSIGNED);
        BaseLocator<Number> baseLocator2 =BaseLocator.holdingRegister(1, 108, DataType.TWO_BYTE_INT_UNSIGNED);
        try {
            Number number = master.getValue(baseLocator);
            System.out.println("number:" + number);
            master.setValue(baseLocator2,102.0f);
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        }
    }
}
