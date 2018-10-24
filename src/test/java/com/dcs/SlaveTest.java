package com.dcs;

import com.gaussic.util.modbus4j.RtuSerialPortWrapper;

/**
 * @ClassName SlaveTest
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/10/23 12:32
 * @Version 1.0
 */
public class SlaveTest {
    protected static final String commPortId = "COM2";
    protected static final int baudRate = 9600,
            flowControlIn = 0,
            flowControlOut = 0,
            dataBits = 8,
            stopBits = 1,
            parity = 1;
    protected static RtuSerialPortWrapper getWrapper() {
        return new RtuSerialPortWrapper(commPortId, baudRate, flowControlIn, flowControlOut, dataBits, stopBits, parity);
    }
}
