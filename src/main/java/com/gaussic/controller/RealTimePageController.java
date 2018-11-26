package com.gaussic.controller;

import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.CoalPipingHistoryRepositoryA;
import com.gaussic.repository.CoalPipingHistoryRepositoryB;
import com.gaussic.repository.CoalPipingHistoryRepositoryC;
import com.gaussic.repository.CoalPipingHistoryRepositoryD;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.MainPageService;
import com.gaussic.service.PipeDataHandleServer;
import com.gaussic.service.dcs.DcsHistoryService;
import com.gaussic.util.HandlDcsHistoryListUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RealTimePageController {


    @Autowired
    private CoalPipingHistoryRepositoryA coalPipingHistoryRepositoryA;
    @Autowired
    private CoalPipingHistoryRepositoryB coalPipingHistoryRepositoryB;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;
    @Autowired
    private CoalPipingHistoryRepositoryD coalPipingHistoryRepositoryD;
    @Autowired
    private DcsHistoryService dcsHistoryService;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;
    @Autowired
    private MainPageService mainPageService;
    /**
     * 查询15分钟内4根粉管的浓度与风速数据，磨煤机的数据
     * 实时画面使用到此方法
     * @param mill mill:A B C D
     * @return
     */
    @RequestMapping(value = "/getInitTimeDataForDensity", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getInitTimeDataForDensity(@RequestParam(value = "mill", required = false) String mill) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimeBefore = localDateTime.minusMinutes(15);
        Timestamp begin = Timestamp.valueOf(localDateTimeBefore);
        Timestamp end = Timestamp.valueOf(localDateTime);
        JSONArray jsonArray = new JSONArray();
        if (null != mill) {
            List<? extends CoalPipingHistory> list = null;
            List<Float> millHistoryList = new ArrayList<>();
            List<H000Pojo_Base> hList = null;
            switch (mill) {
                case "A":
                    list = coalPipingHistoryRepositoryA.findByHTimeAfterOrderByHTimeAsc(begin);
                    hList = dcsHistoryService.findByTime(75, begin, end);
                    break;
                case "B":
                    list = coalPipingHistoryRepositoryB.findByHTimeAfterOrderByHTimeAsc(begin);
                    hList = dcsHistoryService.findByTime(76, begin, end);
                    break;
                case "C":
                    list = coalPipingHistoryRepositoryC.findByHTimeAfterOrderByHTimeAsc(begin);
                    hList = dcsHistoryService.findByTime(77, begin, end);
                    break;
                case "D":
                    list = coalPipingHistoryRepositoryD.findByHTimeAfterOrderByHTimeAsc(begin);
                    hList = dcsHistoryService.findByTime(78, begin, end);
                    break;
                default:
                    list = null;
                    hList = null;
                    break;
            }
            //TODO 查询15分钟内的磨煤机数据
            //将磨煤机的磨煤量数据设置到 list的对象中。
            millHistoryList = HandlDcsHistoryListUtil.getMilHistoryListNoChange(list,hList);
            //对数据进行平滑处理
            jsonArray = generateInitDataJsonArray(list);
        }
        return jsonArray.toString();
    }
    /**
     * 根据磨煤机的编号，返回一台磨煤机的实时数据，包括4根粉管的风速、密度，磨煤机的磨煤量与电流
     * @param mill
     * @return
     */
    @RequestMapping(value = "/getMillRealTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillRealTimeData(@RequestParam(value = "mill", required = false) String mill) {
        return mainPageService.getMillRealTimeData(mill);
    }
    /**
     * 返回一个jsonArray对象
     * 每个数组对象为：{time:12,AD:1,BD:1,CD:1,AV:1,BV:1,CV:1,DV:1,m:1}
     * @param list
     * @return
     */
    private JSONArray generateInitDataJsonArray(List<? extends CoalPipingHistory> list) {
//        JSONArray jsonArray = generateInitDataJsonArray(list);
        JSONArray jsonArray = new JSONArray();

        PipeDataHandleServer.updatePipeDensityAndVelocityWithAvgHandle(list);

        if (null != list) {
            for (int i=0;i<list.size()-36   ;i++) {
                CoalPipingHistory coalPipingHistory = list.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("time", coalPipingHistory.gethTime().getTime());
                jsonObject.put("AD", coalPipingHistory.getPipeADensityNotNull());
                jsonObject.put("BD", coalPipingHistory.getPipeBDensityNotNull());
                jsonObject.put("CD", coalPipingHistory.getPipeCDensityNotNull());
                jsonObject.put("DD", coalPipingHistory.getPipeDDensityNotNull());

                jsonObject.put("AV", coalPipingHistory.getPipeAVelocityNotNull());
                jsonObject.put("BV", coalPipingHistory.getPipeBVelocityNotNull());
                jsonObject.put("CV", coalPipingHistory.getPipeCVelocityNotNull());
                jsonObject.put("DV", coalPipingHistory.getPipeDVelocityNotNull());
                //TODO 磨煤机 模拟数据
//                jsonObject.put("m", (Math.random() * 100) + 20);

                if(null != coalPipingHistory.getCoalMillValue()){
                    jsonObject.put("m", coalPipingHistory.getCoalMillValue()/100);
//                    jsonObject.put("m",-1);
                }else{
                    jsonObject.put("m",-0.01);
                }
                jsonArray.put(jsonObject);
            }
            //TODO 添加锅炉负荷的15分钟数据
        }
        return jsonArray;
    }

}
