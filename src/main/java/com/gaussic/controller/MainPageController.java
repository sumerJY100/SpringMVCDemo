package com.gaussic.controller;


import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.*;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.MainPageService;

import com.gaussic.service.PipeDataHandleServer;
import com.gaussic.util.HandlDcsHistoryListUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class MainPageController {

    @Autowired
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private CoalMillRepository coalMillRepository;
    @Autowired
    private MainPageService mainPageService;
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

    public MainPageController() {
    }

    /**
     * 获取四台磨的密度与风速的实时数据，并返回每台磨得磨煤量数据与磨煤机的电流数据
     *
     * @return
     */
    @RequestMapping(value = "/getAllMillRealTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllMillRealTimeData() {
        //TODO 使用视图进行查询，避免一次性查询16个表的实时数据
        //todo 根据前台反馈的最新时间，返回最新的数据，如果没有，需要重新制定返回规则！！！！！！
        return mainPageService.getAllMillRealTimeData();
    }

    @RequestMapping(value = "/getMillARealTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillARealTimeData() {
        return mainPageService.getMillARealTimeData();
    }

    /**
     * 根据millLocation，查询15分钟内的4根粉管的浓度数据
     * @param millLocation
     * @return
     */
    @RequestMapping(value="/getDensityRealTime15MinutesDataByMill",method=RequestMethod.GET,produces="text/html;" +
            "charset=UTF-8")
    @ResponseBody
    public String getDensityRealTime15MinutesDataByMill(@RequestParam(value="mill",required = false)String
                                                                millLocation){
//        Timestamp latestTimeStamp = getLatestTimeStamp();
//        List<? extends  CoalPipingHistory> list = findCoalPipingHistoryByMillTypeAndLastesTime(latestTimeStamp,
//                millLocation);
        LocalDateTime now = LocalDateTime.now();
        Timestamp begin = Timestamp.valueOf(now.minusMinutes(15));
        Timestamp end = Timestamp.valueOf(now);
        List<CoalPipingHistory> coalPipingHistoryList = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation(millLocation,
                begin,end);
        List<H000Pojo_Base> h000Pojo_baseList = coalPipingHistoryService.findMillDataHistoryByMillLocation
                (millLocation,begin,end);
        HandlDcsHistoryListUtil.getMilHistoryListNoChange(coalPipingHistoryList, h000Pojo_baseList);
        PipeDataHandleServer.updatePipeDensityAndVelocityWithAvgHandle(coalPipingHistoryList);
        List<CoalPipingHistory> resultList = coalPipingHistoryList.subList(0,coalPipingHistoryList.size()-50);
        return generateDensityInitDataJsonArray(resultList).toString();
    }

    /**
     * 根据millLocation，查询15分钟内的4根粉管的风速数据
     * @param millLocation
     * @return
     */
    @RequestMapping(value="/getVelocityRealTime15MinutesDataByMill",method=RequestMethod.GET,produces="text/html;" +
            "charset=UTF-8")
    @ResponseBody
    public String getVelocityRealTime15MinutesDataByMill(@RequestParam(value="mill",required = false)String
                                                                 millLocation){
        LocalDateTime now = LocalDateTime.now();
        Timestamp begin = Timestamp.valueOf(now.minusMinutes(15));
        Timestamp end = Timestamp.valueOf(now);
        List<CoalPipingHistory> coalPipingHistoryList = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation(millLocation,
                begin,end);
        PipeDataHandleServer.updatePipeDensityAndVelocityWithAvgHandle(coalPipingHistoryList);
        List<CoalPipingHistory> resultList = coalPipingHistoryList.subList(0,coalPipingHistoryList.size()-50);

//        Timestamp latestTimeStamp = getLatestTimeStamp();
//        List<? extends  CoalPipingHistory> list = findCoalPipingHistoryByMillTypeAndLastesTime(latestTimeStamp,
//                millLocation);
        return generateVelocityInitDataJsonArray(resultList).toString();
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

    private List<? extends CoalPipingHistory> findCoalPipingHistoryByMillTypeAndLastesTime(Timestamp laestTimestamp,
                                                                                           String millLocation){
        List<? extends CoalPipingHistory> list = null;
        if(null != millLocation){
            if(millLocation.equals("A")){
                list = coalPipingHistoryRepositoryA.findByHTimeAfterOrderByHTimeAsc(laestTimestamp);
            }else if(millLocation.equals("B")){
                list = coalPipingHistoryRepositoryB.findByHTimeAfterOrderByHTimeAsc(laestTimestamp);
            }else if(millLocation.equals("C")){
                list = coalPipingHistoryRepositoryC.findByHTimeAfterOrderByHTimeAsc(laestTimestamp);
            }else if(millLocation.equals("D")){
                list = coalPipingHistoryRepositoryD.findByHTimeAfterOrderByHTimeAsc(laestTimestamp);
            }
        }
        return list;
    }
    private JSONArray generateDensityInitDataJsonArray(List<? extends  CoalPipingHistory> list){
        return generateDensityOrVelocityInitDataJsonArray(list,"density");
    }
    private JSONArray generateVelocityInitDataJsonArray(List<? extends  CoalPipingHistory> list){
        return generateDensityOrVelocityInitDataJsonArray(list,"velocity");
    }
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
}
