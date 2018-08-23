package com.gaussic.controller;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.history.AcoalPipingHistoryEntity;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.CoalPipingHistoryRepositoryA;
import com.gaussic.repository.CoalPipingRepository;
import com.gaussic.service.CoalPipingHistoryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private CoalPipingHistoryRepositoryA coalPipingHistoryRepositoryA;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;

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
        if (null != latestTime) {
            System.out.println("latestTime:" + latestTime);
        }

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

    private static class BE {
        private LocalDateTime begin;
        private LocalDateTime end;

        public LocalDateTime getBegin() {
            return begin;
        }

        public void setBegin(LocalDateTime begin) {
            this.begin = begin;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public void setBE(Date b, Date e) {
            this.setBegin(getLocalDateTimeFromDate(b));
            this.setEnd(getLocalDateTimeFromDate(e));
        }
    }

    /**
     * 获取A磨的历史数据
     *
     * @return
     */
    @RequestMapping(value = "/getAMillHistoryData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAMillHistoryData(@RequestParam(value = "startInputTime", required = false) Date beginTime, @RequestParam(value = "endInputTime", required = false) Date endTime) {


        System.out.println(beginTime + ", " + endTime);
//        if(null!=beginTime && )
        Calendar beginC = new GregorianCalendar();
        beginC.setTimeZone(TimeZone.getDefault());
        Calendar endC = new GregorianCalendar();
        endC.setTimeZone(TimeZone.getDefault());


        BE be = new BE();

        beginC.set(Calendar.MINUTE, beginC.get(Calendar.MINUTE) - (int) (Math.random() * 30));

        LocalDateTime localDateTimeForBegin = LocalDateTime.now();
        LocalDateTime localDateTimeForEnd = LocalDateTime.now();

        Optional<Date> beginDateOpt = Optional.ofNullable(beginTime);
        Optional<Date> endDateOpt = Optional.ofNullable(endTime);
//        beginDateOpt.ifPresent(endDateOpt.ifPresent());
//        beginDateOpt.ifPresent(()->endDateOpt.ifPresent(System.out::println));
        beginDateOpt.ifPresent((b) ->
                endDateOpt.ifPresent((e) ->
                        be.setBE(b, e)
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

//        beginC.set(Calendar.DAY_OF_MONTH,beginC.get(Calendar.DAY_OF_MONTH) -1);
//        endC.set(Calendar.DAY_OF_MONTH,endC.get(Calendar.DAY_OF_MONTH) -1);

        List<AcoalPipingHistoryEntity> coalPipeHistoryEntityList = coalPipingHistoryRepositoryA.findByHTimeBetween(new Timestamp(beginC.getTimeInMillis()), new Timestamp(endC.getTimeInMillis()));
        //根据数据库查询的数据，进行数据校验
        //如果一个数据都没有，则根据开始时间进行数据填充，每个5秒填充一个，数据为null
        //如果数据量大于0，则根据当前时间进行数据填充，最小点时间的数据向前填充，间隔5秒，直至填充的时间小于开始时间，最大点的时间向后填充，直至填充的时间大于结束时间
        //遍历所有数据，如果两个数据之间的时间间隔大于10秒，则根据间隔开始时间进行添加，每个5秒填充一个，直至填充时间大于间隔的结束时间

        if (coalPipeHistoryEntityList.size() == 0) {

            AcoalPipingHistoryEntity e = new AcoalPipingHistoryEntity();
//            e.sethTime(new Timestamp(endTimeForTemp));
            e.sethPipeADencity((float) (Math.random() * 10));
            e.sethPipeBDencity((float) (Math.random() * 10));
            e.sethPipeCDencity((float) (Math.random() * 10));
            e.sethPipeDDencity((float) (Math.random() * 10));
            e.sethPipeAVelocity((float) (Math.random() * 10));
            e.sethPipeBVelocity((float) (Math.random() * 10));
            e.sethPipeCVelocity((float) (Math.random() * 10));
            e.sethPipeDVelocity((float) (Math.random() * 10));
//            coalPipeHistoryEntityList.add(e);
            long endTimeForTemp = beginC.getTimeInMillis();
            long endTimeMillis = endC.getTimeInMillis() - 1000 * 10;
            while (endTimeForTemp < endTimeMillis) {
                AcoalPipingHistoryEntity e1 = new AcoalPipingHistoryEntity();
                e1.sethTime(new Timestamp(endTimeForTemp));
//                e1.sethPipeADencity((float) (Math.random()*10) *0  +  -1);
//                e1.sethPipeBDencity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeCDencity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeDDencity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeAVelocity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeBVelocity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeCVelocity((float) (Math.random()*10) *0 +  -1);
//                e1.sethPipeDVelocity((float) (Math.random()*10) *0 +  -1);
                coalPipeHistoryEntityList.add(e1);
                endTimeForTemp += 6 * 1000;
            }
        }

        return coalPipingHistoryService.generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC);
    }

    /**
     * 获取四台磨的密度与风速的实时数据
     *
     * @return
     */
    @RequestMapping(value = "/getAllMillRealTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllMillRealTimeData() {
        //TODO 使用视图进行查询，避免一次性查询16个表的实时数据
        //todo 根据前台反馈的最新时间，返回最新的数据，如果没有，需要重新制定返回规则！！！！！！
        CoalPipingEntity coalMillAPiping1Entity = coalPipingRepository.findOne(11L);
        CoalPipingEntity coalMillAPiping2Entity = coalPipingRepository.findOne(12L);
        CoalPipingEntity coalMillAPiping3Entity = coalPipingRepository.findOne(13L);
        CoalPipingEntity coalMillAPiping4Entity = coalPipingRepository.findOne(14L);

        CoalPipingEntity coalMillBPiping1Entity = coalPipingRepository.findOne(21L);
        CoalPipingEntity coalMillBPiping2Entity = coalPipingRepository.findOne(22L);
        CoalPipingEntity coalMillBPiping3Entity = coalPipingRepository.findOne(23L);
        CoalPipingEntity coalMillBPiping4Entity = coalPipingRepository.findOne(24L);

        CoalPipingEntity coalMillCPiping1Entity = coalPipingRepository.findOne(31L);
        CoalPipingEntity coalMillCPiping2Entity = coalPipingRepository.findOne(32L);
        CoalPipingEntity coalMillCPiping3Entity = coalPipingRepository.findOne(33L);
        CoalPipingEntity coalMillCPiping4Entity = coalPipingRepository.findOne(34L);

        CoalPipingEntity coalMillDPiping1Entity = coalPipingRepository.findOne(41L);
        CoalPipingEntity coalMillDPiping2Entity = coalPipingRepository.findOne(42L);
        CoalPipingEntity coalMillDPiping3Entity = coalPipingRepository.findOne(43L);
        CoalPipingEntity coalMillDPiping4Entity = coalPipingRepository.findOne(44L);


        JSONObject jsonObject = new JSONObject();
        JSONObject millAPipe1JsonObj = getMillPipingJsonObj(coalMillAPiping1Entity);
        JSONObject millAPipe2JsonObj = getMillPipingJsonObj(coalMillAPiping2Entity);
        JSONObject millAPipe3JsonObj = getMillPipingJsonObj(coalMillAPiping3Entity);
        JSONObject millAPipe4JsonObj = getMillPipingJsonObj(coalMillAPiping4Entity);

        JSONObject millBPipe1JsonObj = getMillPipingJsonObj(coalMillBPiping1Entity);
        JSONObject millBPipe2JsonObj = getMillPipingJsonObj(coalMillBPiping2Entity);
        JSONObject millBPipe3JsonObj = getMillPipingJsonObj(coalMillBPiping3Entity);
        JSONObject millBPipe4JsonObj = getMillPipingJsonObj(coalMillBPiping4Entity);

        JSONObject millCPipe1JsonObj = getMillPipingJsonObj(coalMillCPiping1Entity);
        JSONObject millCPipe2JsonObj = getMillPipingJsonObj(coalMillCPiping2Entity);
        JSONObject millCPipe3JsonObj = getMillPipingJsonObj(coalMillCPiping3Entity);
        JSONObject millCPipe4JsonObj = getMillPipingJsonObj(coalMillCPiping4Entity);

        JSONObject millDPipe1JsonObj = getMillPipingJsonObj(coalMillDPiping1Entity);
        JSONObject millDPipe2JsonObj = getMillPipingJsonObj(coalMillDPiping2Entity);
        JSONObject millDPipe3JsonObj = getMillPipingJsonObj(coalMillDPiping3Entity);
        JSONObject millDPipe4JsonObj = getMillPipingJsonObj(coalMillDPiping4Entity);


        JSONObject millAJsonObj = new JSONObject();
        JSONObject millBJsonObj = new JSONObject();
        JSONObject millCJsonObj = new JSONObject();
        JSONObject millDJsonObj = new JSONObject();
        millAJsonObj.put("pipe1", millAPipe1JsonObj);
        millAJsonObj.put("pipe2", millAPipe2JsonObj);
        millAJsonObj.put("pipe3", millAPipe3JsonObj);
        millAJsonObj.put("pipe4", millAPipe4JsonObj);

        millBJsonObj.put("pipe1", millBPipe1JsonObj);
        millBJsonObj.put("pipe2", millBPipe2JsonObj);
        millBJsonObj.put("pipe3", millBPipe3JsonObj);
        millBJsonObj.put("pipe4", millBPipe4JsonObj);

        millCJsonObj.put("pipe1", millCPipe1JsonObj);
        millCJsonObj.put("pipe2", millCPipe2JsonObj);
        millCJsonObj.put("pipe3", millCPipe3JsonObj);
        millCJsonObj.put("pipe4", millCPipe4JsonObj);

        millDJsonObj.put("pipe1", millDPipe1JsonObj);
        millDJsonObj.put("pipe2", millDPipe2JsonObj);
        millDJsonObj.put("pipe3", millDPipe3JsonObj);
        millDJsonObj.put("pipe4", millDPipe4JsonObj);

        jsonObject.put("time", coalMillAPiping1Entity.getpTime().getTime());
        jsonObject.put("millA", millAJsonObj);
        jsonObject.put("millB", millBJsonObj);
        jsonObject.put("millC", millCJsonObj);
        jsonObject.put("millD", millDJsonObj);
        return jsonObject.toString();
    }
    @RequestMapping(value = "/getMillARealTimeData", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMillARealTimeData(){
        CoalPipingEntity coalMillAPiping1Entity = coalPipingRepository.findOne(11L);
        CoalPipingEntity coalMillAPiping2Entity = coalPipingRepository.findOne(12L);
        CoalPipingEntity coalMillAPiping3Entity = coalPipingRepository.findOne(13L);
        CoalPipingEntity coalMillAPiping4Entity = coalPipingRepository.findOne(14L);

        JSONObject jsonObject = new JSONObject();
        JSONObject millAPipe1JsonObj = getMillPipingJsonObj(coalMillAPiping1Entity);
        JSONObject millAPipe2JsonObj = getMillPipingJsonObj(coalMillAPiping2Entity);
        JSONObject millAPipe3JsonObj = getMillPipingJsonObj(coalMillAPiping3Entity);
        JSONObject millAPipe4JsonObj = getMillPipingJsonObj(coalMillAPiping4Entity);

        JSONObject millAJsonObj = new JSONObject();
        millAJsonObj.put("pipe1", millAPipe1JsonObj);
        millAJsonObj.put("pipe2", millAPipe2JsonObj);
        millAJsonObj.put("pipe3", millAPipe3JsonObj);
        millAJsonObj.put("pipe4", millAPipe4JsonObj);
        jsonObject.put("time", coalMillAPiping1Entity.getpTime().getTime());
        jsonObject.put("millA", millAJsonObj);
        return jsonObject.toString();
    }


    private JSONObject getMillPipingJsonObj(CoalPipingEntity coalMillAPiping1Entity) {
        JSONObject millAPipe1JsonObj = new JSONObject();
        millAPipe1JsonObj.put("pipeId", coalMillAPiping1Entity.getId());
        millAPipe1JsonObj.put("pipeName", coalMillAPiping1Entity.getpName());
        millAPipe1JsonObj.put("density", coalMillAPiping1Entity.getpDencity());
        millAPipe1JsonObj.put("Velocity", coalMillAPiping1Entity.getpVelocity());
        return millAPipe1JsonObj;
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
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - 15);
        List<AcoalPipingHistoryEntity> list = coalPipingHistoryRepositoryA.findByHTimeAfter(new Timestamp(c.getTimeInMillis()));
        JSONArray jsonArray = generateInitDataJsonArray(list);
        return jsonArray.toString();
    }

    private JSONArray generateInitDataJsonArray(List<AcoalPipingHistoryEntity> list) {
        JSONArray jsonArray = new JSONArray();
        for (AcoalPipingHistoryEntity acoalPipingHistoryEntity : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("time", acoalPipingHistoryEntity.gethTime().getTime());
            JSONArray jsonArrayForData = new JSONArray();
            jsonArrayForData.put(acoalPipingHistoryEntity.gethPipeADencity());
            jsonArrayForData.put(acoalPipingHistoryEntity.gethPipeBDencity());
            jsonArrayForData.put(acoalPipingHistoryEntity.gethPipeCDencity());
            jsonArrayForData.put(acoalPipingHistoryEntity.gethPipeDDencity());
            jsonObject.put("data", jsonArrayForData);

            jsonArray.put(jsonObject);
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

