package com.gaussic.util;

import com.gaussic.dataGet.windPojoHandle.WindDataPojo;
import com.gaussic.dataGet.windPojoHandle.WindPojoToEntityUtil;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;

public class HandleDeviceDataTest {
    public static void main(String[] args) {
        String url = "http://172.18.33.43:8080/pulver";
        JSONArray jo = HandleDeviceData.httpRequest(url, "GET");
        WindDataPojo windDataPojo = new WindDataPojo(jo);
        System.out.println(windDataPojo);



//        WindDataPojo windDataPojo = new WindDataPojo(jo);
//        System.out.println("1111111111111111windDataPojo:" + windDataPojo + ",--------" + windDataPojo.getSpeedValue());
        //03、更新 coalPipingEntity对象的风速与密度
//        coalPipingEntity = WindPojoToEntityUtil.updateRealData(now.getTime(),coalPipingEntity,windDataPojo);


         String urlFinal = new String();
        BeanUtils.copyProperties(url,urlFinal);
        System.out.println("urlFinal:" + urlFinal);
    }
}
