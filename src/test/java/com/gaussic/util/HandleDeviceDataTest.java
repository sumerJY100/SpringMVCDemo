package com.gaussic.util;

import com.gaussic.dataGet.WindDataPojo;
import com.gaussic.util.HandleDeviceData;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;

public class HandleDeviceDataTest {
    public static void main(String[] args) {
        String url = "http://192.168.1.41:8080/windJsonProject/json.jsp";
//        JSONArray jo = HandleDeviceData.httpRequest(url, "GET");
//        WindDataPojo windDataPojo = new WindDataPojo(jo);
//        System.out.println(windDataPojo);



         String urlFinal = new String();
        BeanUtils.copyProperties(url,urlFinal);
        System.out.println("urlFinal:" + urlFinal);
    }
}
