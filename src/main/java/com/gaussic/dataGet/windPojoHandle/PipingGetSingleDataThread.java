package com.gaussic.dataGet.windPojoHandle;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.util.HandleDeviceData;
import com.serotonin.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class PipingGetSingleDataThread extends Thread {

    private CoalPipingEntity coalPipingEntity;
    private Date now;

    public PipingGetSingleDataThread() { }
    public PipingGetSingleDataThread(CoalPipingEntity coalPipingEntity, Date now) {
        this.coalPipingEntity = coalPipingEntity;
        this.now = now;
    }

    public void run() { updateData("url"); }

    /**
     * 更新数据,，返回需要更新的对象【已经更新保存到数据库】
     * 更新coalPipingEntity实时数据
     */
    public void updateData(final String url) {
//        CoalPipingSetEntity setEntity = coalPipingEntity.getCoalPipingSetEntity();
//        String url = coalPipingEntity.getCoalPipingSetEntity().getsUrl();
        // 直接读取数据库中的存储的URL地址
//        String url = "http://192.168.1.43:8080/windJsonProject/json.jsp";
        //01、获取设备的jsonp数据，返回一个JsonArray对象
        //TODO 关键处理方法
        /**************关键处理方法***********************/
        JSONArray jo = new JSONArray();
        try {




        //02、jsonArray对象转换为 中间对象“windDataPojo"
        //错误信息录入到Windpojo里面
        WindDataPojo windDataPojo = getWindPojo(url);
//        System.out.println("1111111111111111windDataPojo:" + windDataPojo + ",--------" + windDataPojo.getSpeedValue());
        //03、更新 coalPipingEntity对象的风速与密度
        coalPipingEntity = WindPojoToEntityUtil.updateRealData(now.getTime(),coalPipingEntity,windDataPojo);
//        System.out.println("coalPipintEntity:" + coalPipingEntity.getpDencity());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 单线程 获取wind数据
     * @param url   url
     * @return      windDataPojo
     */
    private WindDataPojo getWindPojo(String url){
        JSONArray joTemp = HandleDeviceData.httpRequest(url, "GET");
        return new WindDataPojo(joTemp);
    }

    /**
     * 多线程，获取wind数据
     * @param url url
     * @return  windDataPojo
     */
    private WindDataPojo getWindPojoThreads(String url){
        WindDataPojo windDataPojoTarget = null;
        try {
            CountDownLatch countDownLatch = new CountDownLatch(5);
            List<WindDataPojo> windDataPojoList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    JSONArray joTemp = HandleDeviceData.httpRequest(url, "GET");
//                    System.out.println(joTemp.toString());
                    windDataPojoList.add(new WindDataPojo(joTemp));
                    countDownLatch.countDown();
                }).start();

            }
            countDownLatch.await();
            //10个jsonArry平均
//            System.out.println("windDataPojoList.size():" + windDataPojoList.size());
            Double xAvg = windDataPojoList.stream().filter(windDataPojo -> windDataPojo.getDensityXValue() != null && Double.valueOf(windDataPojo.getDensityXValue()) > 500000).mapToDouble(windPojo -> Double.valueOf(windPojo.getDensityXValue())).average().orElse(0);
            Double yAvg = windDataPojoList.stream().filter(windDataPojo -> windDataPojo.getDensityYValue() != null && Double.valueOf(windDataPojo.getDensityYValue()) > 500000).mapToDouble(windPojo -> Double.valueOf(windPojo.getDensityYValue())).average().orElse(0);
            Double vAvg = windDataPojoList.stream().filter(windDataPojo -> windDataPojo.getSpeedValue() != null && Double.valueOf(windDataPojo.getSpeedValue()) > 0).mapToDouble(windPojo -> Double.valueOf(windPojo.getSpeedValue())).average().orElse(0);


             windDataPojoTarget = windDataPojoList.stream().filter(windDataPojo -> windDataPojo
                    .getDensityXValue() != null).findFirst().orElse(null);
            if (null != windDataPojoTarget) {
                windDataPojoTarget.setDensityXValue(xAvg.toString());
                windDataPojoTarget.setDensityYValue(yAvg.toString());
                windDataPojoTarget.setSpeedValue(vAvg.toString());


//                System.out.println(windDataPojoTarget.getPipeNumValue());
                if (windDataPojoTarget.getPipeNumValue().equals("PulverizedCoalPiping:1")) {
                    System.out.println("xAvg:" + xAvg + ",yAvg:" + yAvg + ",vAvg:" + vAvg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return windDataPojoTarget;
    }

    public CoalPipingEntity getCoalPipingEntity() { return coalPipingEntity; }

    public void setCoalPipingEntity(CoalPipingEntity coalPipingEntity) { this.coalPipingEntity = coalPipingEntity; }

    public Date getNow() { return now; }

    public void setNow(Date now) { this.now = now; }
}
