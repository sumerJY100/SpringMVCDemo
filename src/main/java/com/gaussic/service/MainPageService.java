package com.gaussic.service;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcs.DevicePointRealtimePojo;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.CoalMillRepository;
import com.gaussic.repository.CoalPipingRepository;
import com.serotonin.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class MainPageService {


    //显示原始数据
    public static final Integer DATA_SHOW_TYPE_ORIGINAL = 1;
    //显示平滑处理数据
    public static final Integer DATA_SHOW_TYPE_AVG = 2;


    @Autowired
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private CoalMillRepository coalMillRepository;
    @Autowired
    private CoalPipingHistoryService<? extends CoalPipingHistory> coalPipingHistoryService;

    /**
     * 获取四台磨的密度与风速的实时数据，包含磨煤机的磨煤量
     *
     * @return  String JSONObject {time:_,
     *  millA:{coalCurrent:_,coalCount:_,
     *      pipe1:{pipeName:_,pipeId:_,density:_,Velocity:_},pipe2:{},pipe3:{},pipe4:{}},
     *  millB:{},
     *  millC:{},
     *  millD:{}
     * }
     */
    public String getAllMillRealTimeData() {
        JSONObject jsonObject = new JSONObject();
        JSONObject millAJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(1L, 11, 12, 13, 14);
        JSONObject millBJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(2L, 21, 22, 23, 24);
        JSONObject millCJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(3L, 31, 32, 33, 34);
        JSONObject millDJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(4L, 41, 42, 43, 44);

        jsonObject.put("time", new Date().getTime()/1000 *1000);
        jsonObject.put("millA", millAJsonObj);
        jsonObject.put("millB", millBJsonObj);
        jsonObject.put("millC", millCJsonObj);
        jsonObject.put("millD", millDJsonObj);

        return jsonObject.toString();
    }

    /**获取A磨得4根粉管的实时数据
     *
     * 此方法为临时方法，已经停用，并且磨煤机的【磨煤量数据为临时的随机数据】
     *
     * @return  String  JSONObject
     *          { time:_, millData:_,millCurrent:_,
     *              pipe1:{density:_,Velocity:_},
     *              pipe2:{},pipe3:{},pipe4:{}
     *          }
     */

    /*public String getMillARealTimeData() {
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
    }*/

    /**
     * 根据磨煤机的编号，返回当前磨煤机对应的所有风管的风速、密度与磨煤量数据【实时数据】
     *  一台磨的数据
     *  数据经过了平滑均值处理，提取了当前时间【30秒 - 230秒】的数据进行处理，获取最后30个数据再进行平均，得到最终的浓度与风速
     * @param mill  A B C D
     * @return  String JSONObject {time:_,millCurrent:_,millData:_,
     * millA:{pipe1:{density:_,Velocity:_},pipe2:{}, pipe3:{},pipe4:{}}}
     */
    public String getMillRealTimeData(String mill) {
        JSONObject jsonObject = new JSONObject();

        if(null != mill){
            long millId = 1;
            CoalMillEntity coalMillEntity = null;
            if(mill.equals("A")){

            }else if(mill.equals("B")){
                millId = 2;
            }else if(mill.equals("C")){
                millId = 3;
            }else if(mill.equals("D")){
                millId = 4;
            }
            coalMillEntity = coalMillRepository.findOne(millId);
            long pipe1Id = millId*10 + 1L,pipe2Id = millId*10 + 2,pipe3Id = millId* 10 + 3,pipe4Id = millId*10+4;
            //生成1台磨的实时数据，此数据进行平滑处理
            JSONObject millAJsonObj = getMillRealTimeDataToJsonObjByMillIdAndPipeIds(millId, pipe1Id,pipe2Id,pipe3Id, pipe4Id);
            jsonObject.put("millA", millAJsonObj);
            jsonObject.put("time", new Date().getTime());
            //磨煤机的磨煤量数据
            float millData = getMillCount(coalMillEntity);
            //磨煤机的电流数据
            float millCurrent = getMillCurrent(coalMillEntity);
            // 磨煤机的磨煤量数据
            jsonObject.put("millData", millData/100);
            //磨煤机的电流数据
            jsonObject.put("millCurrent", millCurrent);
        }



        /*

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
        }*/
        return jsonObject.toString();
    }

    /**
     * 获取单根粉管的实时风速与实时磨煤量,封装成jsonObject
     *
     * @param coalMillAPiping1Entity    CoalPipingEntity
     * @return  JSONObject {pipeId:_,pipeName:_,density:_,Velocity:_}
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
     * @param coalPipingId  coalPipingId
     * @return  JSONObject {pipeId:_,pipeName:_,density:_,Velocity:_}
     */
    private JSONObject getMillPipingJsonObj(Long coalPipingId) {
        //TODO 改变数据的显示方式
        JSONObject jsonObject =getMillPipingJsonObj(coalPipingId, DATA_SHOW_TYPE_ORIGINAL);
//        JSONObject jsonObject =getMillPipingJsonObj(coalPipingId, DATA_SHOW_TYPE_AVG);
        return jsonObject;
    }

    /**
     * 获取单根粉管【实时】风速与实时磨煤量，封装成jsonObject
     *
     * @param coalPipingId  Long coalPipingId
     * @param handleType   DATA_SHOW_TYPE_ORIGINAL使用原始数据，
     *                     DATA_SHOW_TYPE_AVG使用平滑均值处理后的数据
     * @return  JSONObject {pipeId:_,pipeName:_,density:_,Velocity:_}
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
     * @param coalPipingId Long coalPipingId
     * @return  JSONObject {pipeId:_,pipeName:_,density:_,Velocity:_}
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
     * 查询磨煤机的磨煤量数据，磨煤量的实时数据
     *
     * @param coalMillEntity 磨煤机entity
     * @return  浮点数据
     */
    public static  Float getMillCount(CoalMillEntity coalMillEntity) {
        DevicePointPojo devicePointPojoForCoalCount = coalMillEntity.getDeviceDcsPojoForCoalCount();
        return getDcsRealTimeValue(devicePointPojoForCoalCount);
    }

    /**
     * 查询磨煤机的磨煤机电流数据，磨煤机电流的实时数据
     *
     * @param coalMillEntity 磨煤机entity
     * @return float
     */
    public static  Float getMillCurrent(CoalMillEntity coalMillEntity) {
        DevicePointPojo devicePointPojoForMillCurrent = coalMillEntity.getDeviceDcsPojoForCurrent();
        return getDcsRealTimeValue(devicePointPojoForMillCurrent);
    }

    /**
     * 获取DCS某个点的最新数据，
     * 如果当前时间与DCS更新的时间间隔大于15分钟，则返回-1，表示通讯中断了，无数据
     * @param devicePointPojo   DevicePointPojo
     * @return float
     */
    public static Float getDcsRealTimeValue(DevicePointPojo devicePointPojo){
        Float millCurrent = 0f;
        if (null != devicePointPojo) {
            DevicePointRealtimePojo devicePointRealtimePojoForCoalCurrent = devicePointPojo
                    .getDevicePointRealtimePojo();
            if (null != devicePointRealtimePojoForCoalCurrent) {
                LocalDateTime localDateTimeNow = LocalDateTime.now();
                LocalDateTime localDateTimeForDevicePointRealTime = devicePointRealtimePojoForCoalCurrent.getrTime()
                        .toLocalDateTime();
                Duration duration = Duration.between(localDateTimeNow,localDateTimeForDevicePointRealTime);
                if(Math.abs(duration.toMinutes()) > 15) {
                    millCurrent = -1f;
                }else{
                    millCurrent = devicePointRealtimePojoForCoalCurrent.getPointValue();
                }
            }
        }
        return millCurrent;
    }


    /**
     * 生成一台磨得实时数据，包含4个粉管的风速、密度，磨煤机的磨煤量、磨煤机电流
     *  获取【30秒-230秒】之前的数据，进行平滑处理，返回最近30个数据的平均值
     *  2018-11-13 浓度与风速进行数据处理，得到真实的浓度与风速
     * @param coalMillId    coalMillId
     * @param pipe1Id       pipe1Id
     * @param pipe2Id       pipe2Id
     * @param pipe3Id       pipe3Id
     * @param pipe4Id       pipe4Id
     * @return  {coalCount:_,coalCurrent:_,pipe1:{Velocity:_,density:_},pipe2:{},pipe3:{},pipe4:{}}
     */
    public JSONObject getMillRealTimeDataToJsonObjByMillIdAndPipeIds(Long coalMillId, long pipe1Id, long pipe2Id, long pipe3Id, long pipe4Id) {

        JSONObject millAJsonObj = new JSONObject();

        CoalMillEntity coalMillEntity = coalMillRepository.findOne(coalMillId);
        //下面的方法为查询实时数据的方法
        JSONObject millPipe1JsonObj = getMillPipingJsonObj(pipe1Id);
        JSONObject millPipe2JsonObj = getMillPipingJsonObj(pipe2Id);
        JSONObject millPipe3JsonObj = getMillPipingJsonObj(pipe3Id);
        JSONObject millPipe4JsonObj = getMillPipingJsonObj(pipe4Id);

        //下面的方法为：查询200个数据，平滑处理30个数据，得到的最后一个点的数据
        LocalDateTime now = LocalDateTime.now();

        Timestamp end = Timestamp.valueOf(now.minusSeconds(30));
        Timestamp begin = Timestamp.valueOf(now.minusSeconds(230));
        List<? extends  CoalPipingHistory> coalPipingHistoryList = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation(coalMillId, begin, end);
        //将风速与密度封装成float[]数组对象，对应v1，d1等数据，并进行了平滑均值处理
        Map<String, float[]> map = PipeDataHandleServer.getHandlerDensityAndVelocityData(coalPipingHistoryList);

        //磨煤机的管道数据
        if (null != map) {
            //数据进行最后30个数据均值处理
            millPipe1JsonObj.put("Velocity", getLatestValue(map.get("v1")));
            millPipe1JsonObj.put("density", getLatestValue(map.get("d1")));
            millPipe2JsonObj.put("Velocity", getLatestValue(map.get("v2")));
            millPipe2JsonObj.put("density", getLatestValue(map.get("d2")));
            millPipe3JsonObj.put("Velocity", getLatestValue(map.get("v3")));
            millPipe3JsonObj.put("density", getLatestValue(map.get("d3")));
            millPipe4JsonObj.put("Velocity", getLatestValue(map.get("v4")));
            millPipe4JsonObj.put("density", getLatestValue(map.get("d4")));
        }
        //磨煤机的磨煤量
        millAJsonObj.put("coalCount", getMillCount(coalMillEntity));
        //磨煤机电流
        millAJsonObj.put("coalCurrent", getMillCurrent(coalMillEntity));
        millAJsonObj.put("pipe1", millPipe1JsonObj);
        millAJsonObj.put("pipe2", millPipe2JsonObj);
        millAJsonObj.put("pipe3", millPipe3JsonObj);
        millAJsonObj.put("pipe4", millPipe4JsonObj);
        ///*******************浓度与风速，更正为真实的浓度与风速******begin*****************/
        PipeDataHandleServer.updatePipeDensityAndVelocity(millAJsonObj);
        ///*******************浓度与风速，更正为真实的浓度与风速******end*****************/
        return millAJsonObj;
    }


    /**
     * 获取最新的一个数据
     * 默认使用30个数据的平均数
     * @param arr   float[] arr 数组
     * @return  float 处理后的数据
     */
    public static float getLatestValue(float[] arr){
        int type = DATA_SHOW_TYPE_AVG;
        return getLatestValue(arr,type);
    }
    /**
     * 数据均值处理，返回一个float数据
     * type==1的时候，取最后一个数据
     * 当type == 2的时候，取最后30个数据进行均值
     * @param arr   float[] arr 数组
     * @param type int type type==1的时候，使用最近的数据，不进行处理
     *                      type==2时，使用最近30个数据的平均数
     * @return  float 处理后的数据
     */
    public static float getLatestValue(float[] arr,int type) {
        // 重要的数据点，type==1为取最后一个数据，type==2为最后30个数据取均值
//        int type = 2;
        float result = 0;
        if(DATA_SHOW_TYPE_ORIGINAL == type) {
            if (null != arr && arr.length > 0) {
                int length = arr.length;
                result = arr[length - 1];
            }
        }else if(DATA_SHOW_TYPE_AVG == type){
            if(null != arr ) {
                if (arr.length > 30) {
                    int length = arr.length;
                    float[] arrForAvg = Arrays.copyOfRange(arr, length - 30, length);
                    float total = 0f;
                    for (float anArrForAvg : arrForAvg) {
                        total += anArrForAvg;
                    }
                    result = total/arrForAvg.length;
                }
            }
        }
//        System.out.println("latestvalue:" + result);
        return result;
    }
}
