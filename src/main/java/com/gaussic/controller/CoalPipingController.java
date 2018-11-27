package com.gaussic.controller;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcs.DevicePointRealtimePojo;
import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.*;
import com.gaussic.repository.*;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.dcs.DcsHistoryService;
import com.gaussic.util.HandlDcsHistoryListUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Controller
public class CoalPipingController {
    @Autowired
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private CoalMillRepository coalMillRepository;
    @Autowired
    private CoalPipingHistoryRepositoryA coalPipingHistoryRepositoryA;
    @Autowired
    private CoalPipingHistoryRepositoryB coalPipingHistoryRepositoryB;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;
    @Autowired
    private CoalPipingHistoryRepositoryD coalPipingHistoryRepositoryD;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;

    @Autowired
    private DcsHistoryService dcsHistoryService;
    private List<? extends CoalPipingHistory> list;

    /**
     * 获取A磨当前浓度实时数据，格式为{time:1111,data[ADensity,B,C,D]}
     *
     * @return
     */
    @RequestMapping(value = "/getRealTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getRealTimeData(@RequestParam(value = "latestTime", required = false) Long latestTime) {
        //TODO 需要进行时间的判定，判断获取的数据是否是最新的数据，如果数据一致不更新，则产生新的时间，数据为0的数据。浓度数据为0，
        //TODO 相对数据则需要重新规划定义
        //TODO 通过hibernate数据分组功能，将数据分组，使用视图的方式，将数据形成新的实体，就不需要对数据进行单独处理了。
        //TODO 数据采集与处理的时间达到了1.9秒，需要处理


        CoalPipingEntity coalPipingEntityForA = coalPipingRepository.findOne(11L);
        CoalPipingEntity coalPipingEntityForB = coalPipingRepository.findOne(12L);
        CoalPipingEntity coalPipingEntityForC = coalPipingRepository.findOne(13L);
        CoalPipingEntity coalPipingEntityForD = coalPipingRepository.findOne(14L);

        long currentTime = getCurrentTime(latestTime, coalPipingEntityForA.getpTime().getTime(), coalPipingEntityForB.getpTime().getTime(), coalPipingEntityForC.getpTime().getTime(), coalPipingEntityForD.getpTime().getTime());

        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("density",coalPipingEntityForA.getpDencity());
//        jsonObject.put("velocity",coalPipingEntityForA.getpVelocity());

        jsonObject.put("time", coalPipingEntityForA.getpTime().getTime());
        JSONArray tempArrayData = new JSONArray();
        tempArrayData.put(coalPipingEntityForA.getpDencity());
        tempArrayData.put(coalPipingEntityForB.getpDencity());
        tempArrayData.put(coalPipingEntityForC.getpDencity());
        tempArrayData.put(coalPipingEntityForD.getpDencity());
        jsonObject.put("data", tempArrayData);

        return jsonObject.toString();
    }


    public static LocalDateTime getLocalDateTimeFromDate(Date date) {
        LocalDateTime localDateTime = null;
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }















    /**
     * 获取A磨风速的实时的相对数据
     *
     * @return
     */
    @RequestMapping(value = "/getMillAVelocityRealTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillAVelocityRealTimeData() {
        CoalPipingEntity coalPipingEntityForA = coalPipingRepository.findOne(11L);
        CoalPipingEntity coalPipingEntityForB = coalPipingRepository.findOne(12L);
        CoalPipingEntity coalPipingEntityForC = coalPipingRepository.findOne(13L);
        CoalPipingEntity coalPipingEntityForD = coalPipingRepository.findOne(14L);
        JSONObject jsonObject = new JSONObject();
        //TODO 相对值计算
        float avg = (coalPipingEntityForA.getpVelocity() + coalPipingEntityForB.getpVelocity() + coalPipingEntityForC.getpVelocity() + coalPipingEntityForD.getpVelocity()) / 4;

        jsonObject.put("time", coalPipingEntityForA.getpTime().getTime());
        JSONArray tempArrayData = new JSONArray();

        tempArrayData.put(coalPipingEntityForA.getpVelocity() - avg);
        tempArrayData.put(coalPipingEntityForB.getpVelocity() - avg);
        tempArrayData.put(coalPipingEntityForC.getpVelocity() - avg);
        tempArrayData.put(coalPipingEntityForD.getpVelocity() - avg);
        jsonObject.put("data", tempArrayData);
        return jsonObject.toString();
    }

    /**
     * 获取A磨风速的实时绝对数据
     *
     * @return
     */
    @RequestMapping(value = "/getMillAVelocityRealTimeDataForAbsolute", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillAVelocityRealTimeDataForAbsolute() {
        CoalPipingEntity coalPipingEntityForA = coalPipingRepository.findOne(11L);
        CoalPipingEntity coalPipingEntityForB = coalPipingRepository.findOne(12L);
        CoalPipingEntity coalPipingEntityForC = coalPipingRepository.findOne(13L);
        CoalPipingEntity coalPipingEntityForD = coalPipingRepository.findOne(14L);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time", coalPipingEntityForA.getpTime().getTime());
        JSONArray tempArrayData = new JSONArray();
        tempArrayData.put(coalPipingEntityForA.getpVelocity());
        tempArrayData.put(coalPipingEntityForB.getpVelocity());
        tempArrayData.put(coalPipingEntityForC.getpVelocity());
        tempArrayData.put(coalPipingEntityForD.getpVelocity());
        jsonObject.put("data", tempArrayData);
        return jsonObject.toString();
    }

    /**
     * 获取当前最近的时间
     *
     * @param time
     * @param time1
     * @param time2
     * @param time3
     * @return
     */
    private long getCurrentTime(long latestTime, long time, long time1, long time2, long time3) {
        //依据当前时间确定，如果寻找到最新的数据时间再10秒内，则时间为制定的time，否则为
        long now = new Date().getTime();
        Math.max(latestTime, time);
        return 1L;
    }

    /**
     * h获取A磨的15分钟历史浓度数据
     *
     * @return 返回json格式字符串，格式为 [{time:1111,data[ADensity,B,C,D]}]
     */
    @RequestMapping(value = "/getInitTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getInitTimeData() {
        Timestamp laestTimestamp = getLatestTimeStamp();
        List<AcoalPipingHistoryEntity> list = coalPipingHistoryRepositoryA.findByHTimeAfterOrderByHTimeAsc(laestTimestamp);
        JSONArray jsonArray = generateInitDataJsonArray(list);
        return jsonArray.toString();
    }





    /**
     * 返回当前时间15分钟前的时间点
     * @return
     */
    private Timestamp getLatestTimeStamp(){
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - 15);
        return new Timestamp(c.getTimeInMillis());
    }

    private JSONArray generateVelocityInitDataJsonArray(List<? extends  CoalPipingHistory> list){
        return generateDensityOrVelocityInitDataJsonArray(list,"velocity");
    }

    private JSONArray generateDensityInitDataJsonArray(List<? extends  CoalPipingHistory> list){
        return generateDensityOrVelocityInitDataJsonArray(list,"density");
    }


//    private JSONArray generateDensityInitDataJsonArray(List<? extends  CoalPipingHistory> list){
//        return generateDensityOrVelocityInitDataJsonArray(list,"density");
//    }
    private JSONArray generateDensityOrVelocityInitDataJsonArray(List<? extends CoalPipingHistory> list,String
            densityOrVelocity) {
        JSONArray jsonArray = new JSONArray();
        if(densityOrVelocity.equals("density")){
            jsonArray = generateInitDataJsonArray(list);
        }else if(densityOrVelocity.equals("velocity")){
            if (null != list) {
                for (CoalPipingHistory coalPipingHistory : list) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("time", coalPipingHistory.gethTime().getTime());
                    JSONArray jsonArrayForData = new JSONArray();
                    jsonArrayForData.put(coalPipingHistory.gethPipeAVelocity());
                    jsonArrayForData.put(coalPipingHistory.gethPipeBVelocity());
                    jsonArrayForData.put(coalPipingHistory.gethPipeCVelocity());
                    jsonArrayForData.put(coalPipingHistory.gethPipeDVelocity());
                    jsonObject.put("data", jsonArrayForData);

                    jsonArray.put(jsonObject);
                }
            }
        }

        return jsonArray;
    }
    private JSONArray generateInitDataJsonArray(List<? extends CoalPipingHistory> list) {
        JSONArray jsonArray = new JSONArray();
        if (null != list) {
            for (CoalPipingHistory coalPipingHistory : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("time", coalPipingHistory.gethTime().getTime());
                JSONArray jsonArrayForData = new JSONArray();
                jsonArrayForData.put(coalPipingHistory.gethPipeADencity());
                jsonArrayForData.put(coalPipingHistory.gethPipeBDencity());
                jsonArrayForData.put(coalPipingHistory.gethPipeCDencity());
                jsonArrayForData.put(coalPipingHistory.gethPipeDDencity());
                jsonObject.put("data", jsonArrayForData);

                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }

    /*@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    pubic Date getBeginTime(){
        return beginTime;
    }*/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sf, true));
    }


}

