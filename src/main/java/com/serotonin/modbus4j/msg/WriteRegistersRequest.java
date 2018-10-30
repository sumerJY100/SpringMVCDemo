/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.serotonin.modbus4j.msg;

import com.serotonin.modbus4j.Modbus;
import com.serotonin.modbus4j.ProcessImage;
import com.serotonin.modbus4j.base.ModbusUtils;
import com.serotonin.modbus4j.code.FunctionCode;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.sero.util.queue.ByteQueue;

import java.time.Duration;
import java.time.Instant;

public class WriteRegistersRequest extends ModbusRequest {
    private int startOffset;
    private byte[] data;

    public WriteRegistersRequest(int slaveId, int startOffset, short[] sdata) throws ModbusTransportException {
        super(slaveId);
        this.startOffset = startOffset;
        data = convertToBytes(sdata);
    }

    @Override
    public void validate(Modbus modbus) throws ModbusTransportException {
        ModbusUtils.validateOffset(startOffset);
        int registerCount = data.length / 2;
        if (registerCount < 1 || registerCount > modbus.getMaxWriteRegisterCount())
            throw new ModbusTransportException("Invalid number of registers: " + registerCount, slaveId);
        ModbusUtils.validateEndOffset(startOffset + registerCount -1);
    }

    WriteRegistersRequest(int slaveId) throws ModbusTransportException {
        super(slaveId);
    }

    @Override
    protected void writeRequest(ByteQueue queue) {
        ModbusUtils.pushShort(queue, startOffset);
        ModbusUtils.pushShort(queue, data.length / 2);
        ModbusUtils.pushByte(queue, data.length);
//        System.out.println("-01-");
        queue.push(data);
    }

    @Override
    ModbusResponse handleImpl(ProcessImage processImage) throws ModbusTransportException {
        short[] sdata = convertToShorts(data);
//        Instant instant0 = Instant.now();
        processImage.writeHoldingRegister(startOffset,sdata);
        //TODO 修改源码
//        for (int i = 0; i < sdata.length; i++)
//            processImage.writeHoldingRegister(startOffset + i, sdata[i]);

 /*       Instant instant1 = Instant.now();
        Duration duration = Duration.between(instant0,instant1);
        System.out.println("数量："+sdata.length+",间隔：" + duration.getSeconds() + ","+duration.getNano());
*/
        return new WriteRegistersResponse(slaveId, startOffset, sdata.length);
    }

    @Override
    public byte getFunctionCode() {
        return FunctionCode.WRITE_REGISTERS;
    }

    @Override
    ModbusResponse getResponseInstance(int slaveId) throws ModbusTransportException {
        return new WriteRegistersResponse(slaveId);
    }

    @Override
    protected void readRequest(ByteQueue queue) {
        startOffset = ModbusUtils.popUnsignedShort(queue);
        ModbusUtils.popUnsignedShort(queue); // register count not needed.
        data = new byte[ModbusUtils.popUnsignedByte(queue)];
        queue.pop(data);
    }
}
