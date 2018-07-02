package com.gaussic.util;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleDeviceData {
    /**
     * 读取URL数据，返回JSONArray数据
     * @param requestUrl
     * @param requestMethod
     * @return
     */
    public static JSONArray httpRequest(String requestUrl, String requestMethod) {
        JSONArray jsonArray;
        String jsonPStr = getUrlData(requestUrl, requestMethod);
        String jsonStr = handleJsonP(jsonPStr);
        jsonArray = new JSONArray(jsonStr);
        return jsonArray;
    }

    /**
     * jsonP数据转换为json数据
     * @param jsonPStr
     * @return
     */
    private static String handleJsonP(String jsonPStr){
        int startIndex = jsonPStr.indexOf("(");
        int endIndex = jsonPStr.lastIndexOf(")");
        String jsonStr = "" + jsonPStr.substring(startIndex + 1, endIndex) + "";
        return jsonStr;
    }
    /**
     * 读取URL数据，返回字符串
     * @param requestUrl
     * @param requestMethod
     * @return
     */
    private static String getUrlData(String requestUrl, String requestMethod) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            // http协议传输
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
