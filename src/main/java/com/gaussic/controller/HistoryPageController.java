package com.gaussic.controller;

import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.AcoalPipingHistoryEntity;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.CoalPipingHistoryRepositoryA;
import com.gaussic.repository.CoalPipingHistoryRepositoryB;
import com.gaussic.repository.CoalPipingHistoryRepositoryC;
import com.gaussic.repository.CoalPipingHistoryRepositoryD;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.dcs.DcsHistoryService;
import com.gaussic.util.DataFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Controller
public class HistoryPageController {


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


    @RequestMapping(value = "/jumpOriginalXYVPage", method = RequestMethod.GET)
    public String jumpOriginalXYVPage(@RequestParam(value="mill" ,required = false)String millLocation,@RequestParam(value="millId" ,required = false)String millId,@RequestParam(value="pipeId",required = false)Long pipeId, ModelMap modelMap){
        modelMap.put("millLocation",millLocation);
        modelMap.put("millId",millId);
        modelMap.put("pipeId",pipeId);
        return "/historyPages/historyOriginalXY";
    }
    /**
     * 查询某台磨的某根粉管的XYV数据与磨煤机的磨煤量数据
     * @param beginTime
     * @param endTime
     * @param millLocation  字符串ABCD
     * @param pipeId        整形
     * @return
     */
    @RequestMapping(value = "/getMillHistoryXYDataWithMillAndPipeId", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillHistoryXYDataWithMillAndPipeId(@RequestParam(value = "startInputTime", required = false) Date beginTime, @RequestParam(value = "endInputTime", required = false) Date endTime, @RequestParam(value="mill" ,required = false)String
            millLocation,@RequestParam(value="pipeId",required = false)Long pipeId){
        BE be = new BE(beginTime,endTime);
        List<? extends CoalPipingHistory> list = null;
        List<H000Pojo_Base> h000Pojo_baseList = null;
        if(null != millLocation){
            Timestamp begin = be.getBeginTimestamp();
            Timestamp end = be.getEndTimestamp();
            //查询一台磨的4根粉管的历史数据
//            list = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation(millLocation,begin,end);
            list = coalPipingHistoryService.findMillPipeDataHistoryByMillLocationWithThreads(millLocation,begin,end);
            //查询当前磨的磨煤机量
            h000Pojo_baseList = coalPipingHistoryService.findMillDataHistoryByMillLocation(millLocation,begin,end);


        }
        //TODO 处理异常数据
        if(null == list || list.size() == 0){
            list = handleExceptionHistoryList(be);
        }


        return coalPipingHistoryService.generateJsonStringByHistroyListToXYVAndMill(list, be
                .getBeginTimestamp(), be.getEndTimestamp(),h000Pojo_baseList,pipeId);
    }



    /**
     * 查询历史曲线数据【原始数据】
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param millLocation  磨煤机名称 A B C D
     * @return  jsonObj
     */
    @RequestMapping(value = "/getMillHistoryOriginalDataWithMill", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillHistoryOriginalDataWithMill(@RequestParam(value = "startInputTime", required = false) Date beginTime, @RequestParam(value = "endInputTime", required = false) Date endTime, @RequestParam(value="mill" ,required = false)String millLocation){
        //TODO 历史曲线数据，缺少磨煤机磨煤量的数据返回
        BE be = new BE(beginTime,endTime);
        List<? extends CoalPipingHistory> list = null;
        List<H000Pojo_Base> h000Pojo_baseList = null;
        if(null != millLocation){
            Timestamp begin = be.getBeginTimestamp();
            Timestamp end = be.getEndTimestamp();
            //查询一台磨的4根粉管的历史数据
//            list = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation(millLocation,begin,end);
            list = coalPipingHistoryService.findMillPipeDataHistoryByMillLocationWithThreads(millLocation,begin,end);
            //查询当前磨的磨煤机量
            h000Pojo_baseList = coalPipingHistoryService.findMillDataHistoryByMillLocation(millLocation,begin,end);
        }
        //TODO 处理异常数据
        if(null == list || list.size() == 0){
            list = handleExceptionHistoryList(be);
        }

        return coalPipingHistoryService.generateJsonStringByHistroyList(list, be
                .getBeginTimestamp(), be.getEndTimestamp(),h000Pojo_baseList,true);
    }

    /**
     * 查询历史曲线数据【实际磨煤机磨煤量数据】
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param millLocation  磨煤机名称A B C D
     * @return  JSONObject
     */
    @RequestMapping(value = "/getMillHistoryDataWithMill", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillHistoryDataWithMill(@RequestParam(value = "startInputTime", required = false) Date beginTime, @RequestParam(value = "endInputTime", required = false) Date endTime, @RequestParam(value="mill" ,required = false)String millLocation,
                                             @RequestParam(value="millValue",required = false)Float millValue){
        //TODO 历史曲线数据，缺少磨煤机磨煤量的数据返回
        BE be = new BE(beginTime,endTime);
        List<? extends CoalPipingHistory> list = null;
        List<H000Pojo_Base> h000Pojo_baseList = null;
        if(null != millLocation){
            Timestamp begin = be.getBeginTimestamp();
            Timestamp end = be.getEndTimestamp();
            list = coalPipingHistoryService.findMillPipeDataHistoryByMillLocation
                    (millLocation,begin,end);
            if(null == millValue) {
                h000Pojo_baseList = coalPipingHistoryService.findMillDataHistoryByMillLocation(millLocation, begin, end);
            }else{
                h000Pojo_baseList = new ArrayList<>();
                H000Pojo_Base h000Pojo_base = new H000Pojo_Base();
                h000Pojo_base.setV(millValue*100);
                h000Pojo_base.setvTime(begin);
                h000Pojo_baseList.add(h000Pojo_base);
            }
        }
        //TODO 处理异常数据
        if(null == list || list.size() == 0){
            list = handleExceptionHistoryList(be);
        }
        /***************************历史数据查询偏差重新设定****************************/

        //数据偏差记录，读取excel中的偏差
        DataFilter.updateHandleDataWithExcel(millLocation,list);
//        DataFilter.filter(millLocation,list);
        return coalPipingHistoryService.generateJsonStringByHistroyList(list, be
                .getBeginTimestamp(), be.getEndTimestamp(),h000Pojo_baseList,false);
    }
    /**
     * 获取A磨的历史数据
     *
     * @return
     */
    @RequestMapping(value = "/getAMillHistoryData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAMillHistoryData(@RequestParam(value = "startInputTime", required = false) Date beginTime, @RequestParam(value = "endInputTime", required = false) Date endTime) {

        BE be = new BE(beginTime,endTime);


        List<? extends  CoalPipingHistory> coalPipeHistoryEntityList = coalPipingHistoryRepositoryA.findByHTimeBetween(be
                        .getBeginTimestamp(),
                be.getEndTimestamp());
        //TODO 根据数据库查询的数据，进行数据校验
        //TODO 如果一个数据都没有，则根据开始时间进行数据填充，每个5秒填充一个，数据为null
        //TODO 如果数据量大于0，则根据当前时间进行数据填充，最小点时间的数据向前填充，间隔5秒，直至填充的时间小于开始时间，最大点的时间向后填充，直至填充的时间大于结束时间
        //TODO 遍历所有数据，如果两个数据之间的时间间隔大于10秒，则根据间隔开始时间进行添加，每个5秒填充一个，直至填充时间大于间隔的结束时间
        if(coalPipeHistoryEntityList.size() == 0){
            coalPipeHistoryEntityList = handleExceptionHistoryList(be);
        }


        return coalPipingHistoryService.generateJsonStringByHistroyList(coalPipeHistoryEntityList, be
                .getBeginTimestamp(), be.getEndTimestamp(),false);
    }

    /**
     *
     * @param be
     * @return
     */
    private List<? extends CoalPipingHistory> handleExceptionHistoryList(BE be){
        //TODO 根据数据库查询的数据，进行数据校验
        //TODO 如果一个数据都没有，则根据开始时间进行数据填充，每个5秒填充一个，数据为null
        //TODO 如果数据量大于0，则根据当前时间进行数据填充，最小点时间的数据向前填充，间隔5秒，直至填充的时间小于开始时间，最大点的时间向后填充，直至填充的时间大于结束时间
        //TODO 遍历所有数据，如果两个数据之间的时间间隔大于10秒，则根据间隔开始时间进行添加，每个5秒填充一个，直至填充时间大于间隔的结束时间

        List<CoalPipingHistory> list = new ArrayList<>();
        long endTimeForTemp = be.getBeginTimestamp().getTime();
        long endTimeMillis = be.getEndTimestamp().getTime() - 1000 * 10;
        while (endTimeForTemp < endTimeMillis) {
            CoalPipingHistory e1 = new AcoalPipingHistoryEntity();
            e1.sethTime(new Timestamp(endTimeForTemp));
//                e1.sethPipeADencity((float) (Math.random()*10) *0  +  -1);
//                e1.sethPipeBDencity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeCDencity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeDDencity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeAVelocity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeBVelocity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeCVelocity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeDVelocity((float) (Math.random()*10) *0 +  -1);
            list.add(e1);
            endTimeForTemp += 5 * 1000;
        }

        return list;
    }

























    private static class BE {
        private Timestamp beginTimestamp;
        private Timestamp endTimestamp;

        public BE(Date beginTime,Date endTime){
//            System.out.println(beginTime + ", " + endTime);
//        if(null!=beginTime && )
            Calendar beginC = new GregorianCalendar();
            beginC.setTimeZone(TimeZone.getDefault());
            Calendar endC = new GregorianCalendar();
            endC.setTimeZone(TimeZone.getDefault());
            beginC.set(Calendar.MINUTE, beginC.get(Calendar.MINUTE) - (int) (Math.random() * 30));

            LocalDateTime localDateTimeForBegin = LocalDateTime.now();
            LocalDateTime localDateTimeForEnd = LocalDateTime.now();

            Optional<Date> beginDateOpt = Optional.ofNullable(beginTime);
            Optional<Date> endDateOpt = Optional.ofNullable(endTime);
//        beginDateOpt.ifPresent(endDateOpt.ifPresent());
//        beginDateOpt.ifPresent(()->endDateOpt.ifPresent(System.out::println));
            beginDateOpt.ifPresent((b) ->
                    endDateOpt.ifPresent((e) ->
                            {
//                        be.setBE(b, e)
                            }
                    ));
            beginDateOpt.map(new Function<Date, Object>() {
                @Override
                public Object apply(Date date) {
                    return null;
                }
            });
            beginDateOpt.ifPresent(new Consumer<Date>() {
                @Override
                public void accept(Date date) {
//                localDateTimeForBegin.
                }
            });
            Date newDate = beginDateOpt.orElseGet(new Supplier<Date>() {
                @Override
                public Date get() {
                    return null;
                }
            });

            Optional<Object> oDate = beginDateOpt.map(new Function<Date, Object>() {
                @Override
                public Object apply(Date date) {
                    return null;
                }
            });

            Optional<LocalDateTime> t = beginDateOpt.flatMap(new Function<Date, Optional<LocalDateTime>>() {
                @Override
                public Optional<LocalDateTime> apply(Date date) {
                    return Optional.empty();
                }
            });


            if (null != beginTime && null != endTime) {
                beginC.setTime(beginTime);
                endC.setTime(endTime);
            } else if (null != beginTime && endTime == null) {
                beginC.setTime(beginTime);
                endC.setTime(beginTime);
                endC.set(Calendar.HOUR, beginC.get(Calendar.HOUR) + 1);
            } else if (null == beginTime && null != endTime) {
                endC.setTime(endTime);
                beginC.setTime(endTime);
                beginC.set(Calendar.HOUR, endC.get(Calendar.HOUR) - 1);
            } else if (null == beginTime && null == endTime) {
                endC.setTime(new Date());
                beginC.setTime(new Date());
                beginC.set(Calendar.HOUR, beginC.get(Calendar.HOUR) - 1);
            }

            beginTimestamp = new Timestamp(beginC.getTimeInMillis());
            endTimestamp = new Timestamp(endC.getTimeInMillis());
        }

        public Timestamp getBeginTimestamp() {
            return beginTimestamp;
        }

        public void setBeginTimestamp(Timestamp beginTimestamp) {
            this.beginTimestamp = beginTimestamp;
        }

        public Timestamp getEndTimestamp() {
            return endTimestamp;
        }

        public void setEndTimestamp(Timestamp endTimestamp) {
            this.endTimestamp = endTimestamp;
        }



    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sf, true));
    }
}
