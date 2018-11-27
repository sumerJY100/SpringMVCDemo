package com.gaussic.controller;


import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.*;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.MainPageService;

import com.gaussic.service.PipeDataHandleServer;
import com.gaussic.util.HandlDcsHistoryListUtil;
import com.serotonin.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     *
     * @param millLocation
     * @return
     */
    @RequestMapping(value = "/getDensityRealTime15MinutesDataByMill", method = RequestMethod.GET, produces = "text/html;" +
            "charset=UTF-8")
    @ResponseBody
    public String getDensityRealTime15MinutesDataByMill(@RequestParam(value = "mill", required = false) String
                                                                millLocation) {
//        Timestamp latestTimeStamp = getLatestTimeStamp();
//        List<? extends  CoalPipingHistory> list = findCoalPipingHistoryByMillTypeAndLastesTime(latestTimeStamp,
//                millLocation);
        LocalDateTime now = LocalDateTime.now();
        Timestamp begin = Timestamp.valueOf(now.minusMinutes(15));
        Timestamp end = Timestamp.valueOf(now);
        List<CoalPipingHistory> coalPipingHistoryList = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation(millLocation,
                begin, end);
        List<H000Pojo_Base> h000Pojo_baseList = coalPipingHistoryService.findMillDataHistoryByMillLocation
                (millLocation, begin, end);
        HandlDcsHistoryListUtil.getMilHistoryListNoChange(coalPipingHistoryList, h000Pojo_baseList);
        PipeDataHandleServer.updatePipeDensityAndVelocityWithAvgHandle(coalPipingHistoryList);
        List<CoalPipingHistory> resultList = coalPipingHistoryList.subList(0, coalPipingHistoryList.size() - 50);
        return generateDensityInitDataJsonArray(resultList).toString();
    }

    /**
     * 根据millLocation，查询15分钟内的4根粉管的风速数据
     *
     * @param millLocation
     * @return
     */
    @RequestMapping(value = "/getVelocityRealTime15MinutesDataByMill", method = RequestMethod.GET, produces = "text/html;" +
            "charset=UTF-8")
    @ResponseBody
    public String getVelocityRealTime15MinutesDataByMill(@RequestParam(value = "mill", required = false) String
                                                                 millLocation) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp begin = Timestamp.valueOf(now.minusMinutes(15));
        Timestamp end = Timestamp.valueOf(now);
        List<CoalPipingHistory> coalPipingHistoryList = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation(millLocation, begin, end);
        //数据平滑处理
        PipeDataHandleServer.updatePipeDensityAndVelocityWithAvgHandle(coalPipingHistoryList);
        //提取数量减去50
        List<CoalPipingHistory> resultList = coalPipingHistoryList.subList(0, coalPipingHistoryList.size() - 50);

//        Timestamp latestTimeStamp = getLatestTimeStamp();
//        List<? extends  CoalPipingHistory> list = findCoalPipingHistoryByMillTypeAndLastesTime(latestTimeStamp,
//                millLocation);
        return generateVelocityInitDataJsonArray(resultList).toString();
    }



    /**
     * 获取一台磨的历史浓度数据，封装成JSONArray
     * @param list  List<? extends CoalPipingHistory> list
     * @return  [{time：long类型，data:[A浓度/速度，B浓度/速度，C浓度/速度，D浓度/速度]},{},{}]
     */
    private JSONArray generateDensityInitDataJsonArray(List<? extends CoalPipingHistory> list) {
        return generateInitDensityOrVelocityDataJsonArray(list, "density");
    }

    /**
     * 获取一台磨的历史风速数据，封装成JSONArray
     * @param list List<? extends CoalPipingHistory> list
     * @return  [{time：long类型，data:[A浓度/速度，B浓度/速度，C浓度/速度，D浓度/速度]},{},{}]
     */
    private JSONArray generateVelocityInitDataJsonArray(List<? extends CoalPipingHistory> list) {
        return generateInitDensityOrVelocityDataJsonArray(list, "velocity");
    }


    /**
     * 生成一台磨的历史数据对象，浓度或者风速，历史数据
     * @param list  coalPipingList
     * @param densityOrVelocity density or velocity
     * @return  JSONArray [{time：long类型，data:[A浓度/速度，B浓度/速度，C浓度/速度，D浓度/速度]},{},{}]
     */
    private JSONArray generateInitDensityOrVelocityDataJsonArray(List<? extends CoalPipingHistory> list,String
            densityOrVelocity) {
        JSONArray jsonArray = new JSONArray();
        if (null != list) {
            for (CoalPipingHistory coalPipingHistory : list) {
                JSONObject jsonObject = generateJsonObject(coalPipingHistory, densityOrVelocity);
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     *  将一台磨的4个粉管的浓度或者风速与时间封装成一个对象，{time：long类型，data:[A浓度/速度，B浓度/速度，C浓度/速度，D浓度/速度]}
     * @param coalPipingHistory     CoalPipingHistory对象
     * @param densityOrVelocity     density or velocity
     * @return  JSONObject {time：long类型，data:[A浓度/速度，B浓度/速度，C浓度/速度，D浓度/速度]}
     */
    private JSONObject generateJsonObject(CoalPipingHistory coalPipingHistory, String densityOrVelocity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time", coalPipingHistory.gethTime().getTime());
        JSONArray jsonArrayForData = new JSONArray();
        if (densityOrVelocity.equals("density")) {
            jsonArrayForData.put(coalPipingHistory.gethPipeADencity());
            jsonArrayForData.put(coalPipingHistory.gethPipeBDencity());
            jsonArrayForData.put(coalPipingHistory.gethPipeCDencity());
            jsonArrayForData.put(coalPipingHistory.gethPipeDDencity());
        } else if (densityOrVelocity.equals("velocity")) {
            jsonArrayForData.put(coalPipingHistory.gethPipeAVelocity());
            jsonArrayForData.put(coalPipingHistory.gethPipeBVelocity());
            jsonArrayForData.put(coalPipingHistory.gethPipeCVelocity());
            jsonArrayForData.put(coalPipingHistory.gethPipeDVelocity());
        }
        jsonObject.put("data", jsonArrayForData);
        return jsonObject;
    }



}
