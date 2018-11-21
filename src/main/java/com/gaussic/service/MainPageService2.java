package com.gaussic.service;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcs.DevicePointRealtimePojo;
import com.gaussic.model.dcsRemote.DcsRemotePointPojo;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.CoalMillRepository;
import com.gaussic.repository.CoalPipingRepository;
import com.gaussic.repository.DcsRemotePointRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class MainPageService2 {


    //显示原始数据
    public static final Integer DATA_SHOW_TYPE_ORIGINAL = 1;
    //显示平滑处理数据
    public static final Integer DATA_SHOW_TYPE_AVG = 2;


    @Autowired
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private CoalMillRepository coalMillRepository;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;

    /**
     * 获取四台磨的密度与风速的实时数据，包含磨煤机的
     *
     * @return
     */
    public String getAllMillRealTimeData() {
        JSONObject jsonObject = new JSONObject();
        JSONObject millAJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(1L, 11, 12, 13, 14);
        JSONObject millBJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(2L, 21, 22, 23, 24);
        JSONObject millCJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(3L, 31, 32, 33, 34);
        JSONObject millDJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(4L, 41, 42, 43, 44);

        jsonObject.put("time", new Date().getTime());
        jsonObject.put("millA", millAJsonObj);
        jsonObject.put("millB", millBJsonObj);
        jsonObject.put("millC", millCJsonObj);
        jsonObject.put("millD", millDJsonObj);

        return jsonObject.toString();
    }

    /**获取A磨得4根粉管的实时数据
     *
     * 此方法为临时方法，已经停用，并且磨煤机的磨煤量数据为临时的随机数据
     *
     * @return
     */
    public String getMillARealTimeData() {
        JSONObject jsonObject = new JSONObject();
        JSONObject millAPipe1JsonObj = getMillPipingJsonObj(11L);
        JSONObject millAPipe2JsonObj = getMillPipingJsonObj(12L);
        JSONObject millAPipe3JsonObj = getMillPipingJsonObj(13L);
        JSONObject millAPipe4JsonObj = getMillPipingJsonObj(14L);
        JSONObject millAJsonObj = new JSONObject();
        millAJsonObj.put("pipe1", millAPipe1JsonObj);
        millAJsonObj.put("pipe2", millAPipe2JsonObj);
        millAJsonObj.put("pipe3", millAPipe3JsonObj);
        millAJsonObj.put("pipe4", millAPipe4JsonObj);
        jsonObject.put("time", new Date().getTime());
        //临时数据
        jsonObject.put("millData", (int) (Math.random() * 100) + 30);
        jsonObject.put("millA", millAJsonObj);
        return jsonObject.toString();
    }

    /**
     * 根据磨煤机的编号，返回当前磨煤机对应的所有风管的风速、密度与磨煤量数据
     *
     * @param mill
     * @return
     */
    public String getMillRealTimeData(String mill) {

        JSONObject jsonObject = new JSONObject();
        if (null != mill) {
            Float millData = 0f, millCurrent = 0f;
            long pipe1Id = 11L, pipe2Id = 12L, pipe3Id = 13L, pipe4Id = 14L;
            CoalMillEntity coalMillEntity = null;
            if (mill.equals("A")) {
                coalMillEntity = coalMillRepository.findOne(1L);
            } else if (mill.equals("B")) {
                pipe1Id = 21L;
                pipe2Id = 22L;
                pipe3Id = 23L;
                pipe4Id = 24L;
                coalMillEntity = coalMillRepository.findOne(2L);
            } else if (mill.equals("C")) {
                pipe1Id = 31L;
                pipe2Id = 32L;
                pipe3Id = 33L;
                pipe4Id = 34L;
                coalMillEntity = coalMillRepository.findOne(3L);
            } else if (mill.equals("D")) {
                pipe1Id = 41L;
                pipe2Id = 42L;
                pipe3Id = 43L;
                pipe4Id = 44L;
                coalMillEntity = coalMillRepository.findOne(4L);
            }
            //磨煤机的磨煤量数据
            millData = getMillCount(coalMillEntity);
            //磨煤机的电流数据
            millCurrent = getMillCurrent(coalMillEntity);
            //四根粉管的数据
            JSONObject millAPipe1JsonObj = getMillPipingJsonObj(pipe1Id);
            JSONObject millAPipe2JsonObj = getMillPipingJsonObj(pipe2Id);
            JSONObject millAPipe3JsonObj = getMillPipingJsonObj(pipe3Id);
            JSONObject millAPipe4JsonObj = getMillPipingJsonObj(pipe4Id);

            JSONObject millAJsonObj = new JSONObject();
            millAJsonObj.put("pipe1", millAPipe1JsonObj);
            millAJsonObj.put("pipe2", millAPipe2JsonObj);
            millAJsonObj.put("pipe3", millAPipe3JsonObj);
            millAJsonObj.put("pipe4", millAPipe4JsonObj);
            jsonObject.put("time", new Date().getTime());
            // 磨煤机的磨煤量数据
            jsonObject.put("millData", millData);
            //磨煤机的电流数据
            jsonObject.put("millCurrent", millCurrent);

            jsonObject.put("millA", millAJsonObj);
        }
        return jsonObject.toString();
    }

    /**
     * 获取单根粉管的实时风速与实时磨煤量,封装成jsonObject
     *
     * @param coalMillAPiping1Entity
     * @return
     */
    private JSONObject getMillPipingJsonObj(CoalPipingEntity coalMillAPiping1Entity) {
        JSONObject millAPipe1JsonObj = new JSONObject();
        millAPipe1JsonObj.put("pipeId", coalMillAPiping1Entity.getId());
        millAPipe1JsonObj.put("pipeName", coalMillAPiping1Entity.getpName());
        millAPipe1JsonObj.put("density", coalMillAPiping1Entity.getpDencity());
        millAPipe1JsonObj.put("Velocity", coalMillAPiping1Entity.getpVelocity());
        return millAPipe1JsonObj;
    }

    /**
     * 获取单根粉管的实时风速与实时磨煤量,封装成jsonObject
     *
     * @param coalPipingId
     * @return
     */
    private JSONObject getMillPipingJsonObj(Long coalPipingId) {
        //TODO 改变数据的显示方式
        JSONObject jsonObject =getMillPipingJsonObj(coalPipingId, DATA_SHOW_TYPE_ORIGINAL);
//        JSONObject jsonObject =getMillPipingJsonObj(coalPipingId, DATA_SHOW_TYPE_AVG);
        return jsonObject;
    }

    /**
     * 获取单根粉管的实时风速与实时磨煤量，封装成jsonObject
     *
     * @param coalPipingId
     * @param handleType   DATA_SHOW_TYPE_ORIGINAL使用原始数据，DATA_SHOW_TYPE_AVG使用平滑均值处理后的数据
     * @return
     */
    private JSONObject getMillPipingJsonObj(Long coalPipingId, int handleType) {
        if (handleType == DATA_SHOW_TYPE_ORIGINAL) {
            CoalPipingEntity coalPipingEntity = coalPipingRepository.findOne(coalPipingId);
            return getMillPipingJsonObj(coalPipingEntity);
        } else if (handleType == DATA_SHOW_TYPE_AVG) {
            return getMillPipingJsonObjForAvg(coalPipingId);
        } else {
            return null;
        }
    }

    /**
     * 获取单根粉管的历史平滑数据后的实时数据，将数据平滑处理，封装成jsonObject
     *
     * @param coalPipingId
     * @return
     */
    private JSONObject getMillPipingJsonObjForAvg(Long coalPipingId) {
        //TODO 未进行
        // TODO 此方法将查询200秒内的数据，使用平滑均值滤波的方法进行处理数据
        //TODO 下一步进行处理
        JSONObject jsonObject = new JSONObject();
        CoalPipingEntity coalPipingEntity = coalPipingRepository.findOne(coalPipingId);
        jsonObject.put("pipeId", coalPipingEntity.getId());
        jsonObject.put("pipeName", coalPipingEntity.getpName());
        jsonObject.put("density", coalPipingEntity.getpDencity());
        jsonObject.put("Velocity", coalPipingEntity.getpVelocity());
        return jsonObject;
    }



    /**
     * 查询磨煤机的磨煤量数据
     *
     * @param coalMillEntity
     * @return
     */
    public static  Float getMillCount(CoalMillEntity coalMillEntity) {
        Float millValue = 0f;
        DevicePointPojo devicePointPojoForCoalCount = coalMillEntity.getDeviceDcsPojoForCoalCount();

        if (null != devicePointPojoForCoalCount) {
            DevicePointRealtimePojo devicePointRealtimePojoForCoalCount = devicePointPojoForCoalCount.getDevicePointRealtimePojo();
            if (null != devicePointRealtimePojoForCoalCount) {
                millValue = devicePointRealtimePojoForCoalCount.getPointValue();
            }
        }
        return millValue;
    }

    /**
     * 查询磨煤机的磨煤机电流数据
     *
     * @param coalMillEntity
     * @return
     */
    public static  Float getMillCurrent(CoalMillEntity coalMillEntity) {
        DevicePointPojo devicePointPojoForMillCurrent = coalMillEntity.getDeviceDcsPojoForCurrent();
        Float millCurrent = 0f;
        if (null != devicePointPojoForMillCurrent) {
            DevicePointRealtimePojo devicePointRealtimePojoForCoalCurrent = devicePointPojoForMillCurrent
                    .getDevicePointRealtimePojo();
            if (null != devicePointRealtimePojoForCoalCurrent) {
                millCurrent = devicePointRealtimePojoForCoalCurrent.getPointValue();
            }
        }
        return millCurrent;
    }

    @Autowired
    private DcsRemotePointRepository dcsRemotePointRepository;

    /**
     * 生成一台磨得实时数据，包含4个粉管的风速、密度，磨煤机的磨煤量、磨煤机电流
     *  2018-11-13 浓度与风速进行数据处理，得到真实的浓度与风速
     * @param coalMillId
     * @param pipe1Id
     * @param pipe2Id
     * @param pipe3Id
     * @param pipe4Id
     * @return
     */
    public JSONObject getMillRealTimeDataToJsonObjByMillIdAndPipeIds(Long coalMillId, long pipe1Id, long pipe2Id, long pipe3Id, long pipe4Id) {

        JSONObject millAJsonObj = new JSONObject();

        CoalMillEntity coalMillEntity = coalMillRepository.findOne(coalMillId);
        //下面的方法为查询实时数据的方法
        JSONObject millPipe1JsonObj = getMillPipingJsonObj(pipe1Id);
        JSONObject millPipe2JsonObj = getMillPipingJsonObj(pipe2Id);
        JSONObject millPipe3JsonObj = getMillPipingJsonObj(pipe3Id);
        JSONObject millPipe4JsonObj = getMillPipingJsonObj(pipe4Id);

        List<DcsRemotePointPojo> dcsRemotePointPojoList = dcsRemotePointRepository.findAll();

        Map<Long,List<DcsRemotePointPojo>> dcsMap = new HashMap<>();
        for(DcsRemotePointPojo dcsRemotePointPojo:dcsRemotePointPojoList){
            Long pipeId = dcsRemotePointPojo.getCoalPipingEntity().getId();
            if(null != dcsMap.get(pipeId)){

            }else{
                List<DcsRemotePointPojo> list = new ArrayList<>();
                dcsMap.put(pipeId,list);
            }
            dcsMap.get(pipeId).add(dcsRemotePointPojo);
        }
        setDensityAndVelocity(dcsMap,pipe1Id,millPipe1JsonObj);
        setDensityAndVelocity(dcsMap,pipe2Id,millPipe2JsonObj);
        setDensityAndVelocity(dcsMap,pipe3Id,millPipe3JsonObj);
        setDensityAndVelocity(dcsMap,pipe4Id,millPipe4JsonObj);



        //磨煤机的磨煤量
        millAJsonObj.put("coalCount", getMillCount(coalMillEntity));
        //磨煤机电流
        millAJsonObj.put("coalCurrent", getMillCurrent(coalMillEntity));


        millAJsonObj.put("pipe1", millPipe1JsonObj);
        millAJsonObj.put("pipe2", millPipe2JsonObj);
        millAJsonObj.put("pipe3", millPipe3JsonObj);
        millAJsonObj.put("pipe4", millPipe4JsonObj);

        /*******************浓度与风速，更正为真实的浓度与风速******begin*****************/
        PipeDataHandleServer.updatePipeDensityAndVelocity(millAJsonObj);
        /*******************浓度与风速，更正为真实的浓度与风速******end*****************/



        return millAJsonObj;
    }

    private void setDensityAndVelocity(Map<Long,List<DcsRemotePointPojo>> dcsMap,long pipe1Id,JSONObject
            millPipe1JsonObj){
        List<DcsRemotePointPojo> dcsRemotePointPojoList1 = dcsMap.get(pipe1Id);
        for(DcsRemotePointPojo dcsRemotePointPojo:dcsRemotePointPojoList1){
            if(dcsRemotePointPojo.getDensityOrVelocity() == 0){
                float velocity = dcsRemotePointPojo.getCurrentValue();
                millPipe1JsonObj.put("Velocity",velocity);
            }else if(dcsRemotePointPojo.getDensityOrVelocity() == 1){
                float density = dcsRemotePointPojo.getCurrentValue();
                millPipe1JsonObj.put("density",density);
            }
        }
    }



    /*public static void main(String[] args) {
        float f = 11;
        float operatorValue = 1.8f;
        float distance = 5.0f;
        float pipe1VelocityReal = ((int)(distance/(f * operatorValue) * 1000))/10f;
        System.out.println(pipe1VelocityReal);
    }*/
    /**
     * 数据均值处理，返回一个float数据
     * type==1的时候，取最后一个数据
     * 当type == 2的时候，取最后30个数据进行均值
     * @param arr
     * @return
     */
    public static float getLatestValue(float[] arr) {
        //TODO 重要的数据点，1位取最后一个数据，2位最后30个数据取均值
        int type = 2;
        float result = 0;
        if(type == 1) {

            if (null != arr && arr.length > 0) {
                int length = arr.length;
                result = arr[length - 1];
            }
        }else if(type == 2){
            if(null != arr ) {
                if (arr.length > 30) {
                    int length = arr.length;
                    float[] arrForAvg = Arrays.copyOfRange(arr, length - 30, length);
                    float total = 0f;
                    for(int i=0;i<arrForAvg.length;i++){
                        total+=arrForAvg[i];
                    }
                    result = total/arrForAvg.length;
                }
            }
        }
//        System.out.println("latestvalue:" + result);
        return result;
    }
}
