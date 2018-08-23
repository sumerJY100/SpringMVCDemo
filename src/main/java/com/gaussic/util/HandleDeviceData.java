package com.gaussic.util;

import org.json.JSONArray;

import java.io.*;
import java.net.*;

public class HandleDeviceData {
    /**
     * 读取URL数据，返回JSONArray数据
     *
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
     *
     * @param jsonPStr
     * @return
     */
    private static String handleJsonP(String jsonPStr) {
        int startIndex = jsonPStr.indexOf("(");
        int endIndex = jsonPStr.lastIndexOf(")");
        String jsonStr = "" + jsonPStr.substring(startIndex + 1, endIndex) + "";
        return jsonStr;
    }

    /**
     * 读取URL数据，返回字符串
     *
     * @param requestUrl
     * @param requestMethod
     * @return
     */
    private static String getUrlData(String requestUrl, String requestMethod) {
        StringBuffer buffer = new StringBuffer();
        boolean connectFlag = false;
//        try {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            //TODO IP地址格式不正确，需要正确的返回数据，并记录相应的数据
            System.out.println("IP地址格式不正确：" + requestUrl);
//            e.printStackTrace();
        }
        // http协议传输
        HttpURLConnection httpUrlConn = null;
        try {
            httpUrlConn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {

//            e.printStackTrace();
        }
        httpUrlConn.setDoOutput(true);
        httpUrlConn.setDoInput(true);
        httpUrlConn.setUseCaches(false);
        // 设置请求方式（GET/POST）
        try {
            httpUrlConn.setRequestMethod(requestMethod);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        if ("GET".equalsIgnoreCase(requestMethod)) {
            try {
                httpUrlConn.setConnectTimeout(1500);
                httpUrlConn.connect();
                connectFlag = true;
            } catch (NoRouteToHostException e) {

//                e.printStackTrace();


            }catch (SocketTimeoutException socketTimeoutException) {
                httpUrlConn.disconnect();
                //TODO 超时异常
//                System.out.println("超时异常：connect timed out,连接地址：" + requestUrl);
            } catch (ConnectException connectException) {
                // 连接超时异常处理
//                System.out.println("连接超时异常,连接地址：" + requestUrl);
//                System.out.println(httpUrlConn);
            } catch (SocketException e) {
//                e.printStackTrace();
            }catch(IOException s){

            }
        }
        if (connectFlag) {
            // 将返回的输入流转换成字符串
            InputStream inputStream = null;
            try {
                inputStream = httpUrlConn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            try {
                while ((str = bufferedReader.readLine()) != null) buffer.append(str);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 释放资源
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = null;
            httpUrlConn.disconnect();
        } else {
            buffer.append("josnp([])");
        }
//        System.out.println("buffer:" + buffer.toString());
        return buffer.toString();
    }
}
