package com.gaussic.service;

import com.gaussic.model.dcs.DeviceDcsPojo;
import com.gaussic.repository.DeviceDcsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DcsService
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/7 14:39
 * @Version 1.0
 */
@Component
public class DcsService {

    @Autowired
    private DeviceDcsRepository deviceDcsRepository;

    private static List<String> attrsList = new ArrayList<>();
    static{
        attrsList.add("deviceName");
        attrsList.add("deviceAddress");
        attrsList.add("devicePort");
        attrsList.add("deviceBoundRate");
        attrsList.add("deviceNote");
        attrsList.add("deviceNum");
        attrsList.add("deviceFlowControlIn");
        attrsList.add("deviceFlowControlOut");
        attrsList.add("deviceDataBits");
        attrsList.add("deviceStopBits");
        attrsList.add("deviceParity");
    }

    public void saveDeviceDcsWithMainAttr(DeviceDcsPojo deviceDcsPojo) {
        if (null != deviceDcsPojo && null != deviceDcsPojo.getDeviceId()) {
            DeviceDcsPojo deviceDcsPojoFromDb = deviceDcsRepository.findOne(deviceDcsPojo.getDeviceId());
            for (int i = 0; i < attrsList.size(); i++) {
                setNewAttributeValue(deviceDcsPojo, deviceDcsPojoFromDb,attrsList.get(i));
            }
            deviceDcsRepository.saveAndFlush(deviceDcsPojoFromDb);
        }
    }

    private void setNewAttributeValue(DeviceDcsPojo deviceDcsPojoOld, DeviceDcsPojo deviceDcsPojoNew, String
            attributeName) {
        try {
            Class c = deviceDcsPojoOld.getClass();
            String attr = getFirstUpperString(attributeName);
            String setMethodName = "set" + attr;
            String getMethodName = "get" + attr;
            Method getMethod = c.getMethod(getMethodName);
            Class paramTypeClass = getMethod.getReturnType();
            Method setMethod = c.getMethod(setMethodName, paramTypeClass);
            if (!getMethod.invoke(deviceDcsPojoOld).equals(getMethod.invoke(deviceDcsPojoNew))) {
//                paramTypeClass.getSimpleName()
                if(paramTypeClass.getSimpleName().equals("Integer")) {
                    setMethod.invoke(deviceDcsPojoNew,(Integer)getMethod.invoke(deviceDcsPojoOld));
                }else if(paramTypeClass.getSimpleName().equals("String")){
                    setMethod.invoke(deviceDcsPojoNew,(String)getMethod.invoke(deviceDcsPojoOld));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFirstUpperString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1,
                str.length() );
    }
}
