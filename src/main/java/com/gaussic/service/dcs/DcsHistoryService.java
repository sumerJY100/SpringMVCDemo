package com.gaussic.service.dcs;

import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcs_history.DcsHistoryCashePojo;
import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.repository.dcs_history.DcsHistoryCasheRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class DcsHistoryService {
    @Autowired
    private Dcs000_019Service dcs000_019Service;
    @Autowired
    private Dcs020_039Service dcs020_039Service;
    @Autowired
    private Dcs040_059Service dcs040_059Service;
    @Autowired
    private Dcs060_079Service dcs060_079Service;
    @Autowired
    private Dcs080_099Service dcs080_099Service;
    @Autowired
    private Dcs100_119Service dcs100_119Service;
    @Autowired
    private Dcs120_139Service dcs120_139Service;
    @Autowired
    private Dcs140_159Service dcs140_159Service;
    @Autowired
    private Dcs160_179Service dcs160_179Service;
    @Autowired
    private Dcs180_199Service dcs180_199Service;
    @Autowired
    private DcsHistoryCasheRep dcsHistoryCasheRep;



    public List<H000Pojo_Base> findByTime(Integer offset,Timestamp begin,Timestamp end){
        List<H000Pojo_Base> list = new ArrayList<>();
        List<Object> historyList = null;
        offset = offset - 1;
        if(offset<20){
            historyList = dcs000_019Service.findByTime(offset,begin,end);
        }else if(offset < 40){
            historyList = dcs020_039Service.findByTime(offset,begin,end);
        }else if(offset < 60){
            historyList = dcs040_059Service.findByTime(offset,begin,end);
        }else if(offset < 80){
            System.out.println("查询历史：" + offset);
            historyList = dcs060_079Service.findByTime(offset,begin,end);
        }else if(offset < 100){
            historyList = dcs080_099Service.findByTime(offset,begin,end);
        }else if(offset < 120){
            historyList = dcs100_119Service.findByTime(offset,begin,end);
        }else if(offset < 140){
            historyList = dcs120_139Service.findByTime(offset,begin,end);
        }else if(offset < 160){
            historyList = dcs140_159Service.findByTime(offset,begin,end);
        }else if(offset < 180){
            historyList = dcs160_179Service.findByTime(offset,begin,end);
        }else if(offset < 200){
            historyList = dcs180_199Service.findByTime(offset,begin,end);
        }
        if(null != historyList) {
            list = changeObjectListToH000BaseList(offset,historyList);
//            if(null != list){
//                list = fillData(list);
//            }
        }
        return list;
    }

    public List<H000Pojo_Base> findByTime(DevicePointPojo devicePointPojo,Timestamp begin,Timestamp end){
        Integer offset = Integer.valueOf(devicePointPojo.getPointAddress());
        return findByTime(offset,begin,end);
    }

    /**
     * 填充历史数据，时间间隔固定为 5 秒
     * @param list
     * @return
     */
    private List<H000Pojo_Base> fillData(List<H000Pojo_Base> list) {
        return fillData(list,5);
    }

    /**
     * 填充历史数据，根据固定时间间隔
     * @param list
     * @param interval
     * @return
     */
    private List<H000Pojo_Base> fillData(List<H000Pojo_Base> list,int interval) {
        //TODO 未开始
        return null;
    }

    private List<H000Pojo_Base> changeObjectListToH000BaseList(int offset,List<Object> historyList) {
        List<H000Pojo_Base> list = new ArrayList<>();
        String targetClassName = "com.gaussic.model.dcs_history.H" + String.format("%03d", offset) + "Pojo";
        try {
            Class c = Class.forName(targetClassName);
            historyList.forEach((h) -> {
                String methodGetV = "getV";
                String methodGetVT = "getvTime";
                try {
                    Method getVMethod = c.getMethod(methodGetV);
                    Method getVTMethod = c.getMethod(methodGetVT);

                    try {
                        Float v = (Float) getVMethod.invoke(h);
                        Timestamp time = (Timestamp) getVTMethod.invoke(h);
                        H000Pojo_Base h000Pojo_base = new H000Pojo_Base();
                        h000Pojo_base.setV(v);
                        h000Pojo_base.setvTime(time);
                        list.add(h000Pojo_base);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 保存DCS发送的数据，全部保存到dcsHistoryCache中
     * @param offset
     * @param value
     * @param timestamp
     */
    public void save(int offset, int[] value, Timestamp timestamp){
        try {
            String str = Arrays.toString(value);
            str = str.substring(1, str.length() - 1);
            DcsHistoryCashePojo dcsHistoryCashePojo = new DcsHistoryCashePojo();
            dcsHistoryCashePojo.setValues(str);
            dcsHistoryCashePojo.setvOffset(offset);
            dcsHistoryCashePojo.setvTime(timestamp);

            dcsHistoryCasheRep.saveAndFlush(dcsHistoryCashePojo);

//            List<DcsHistoryCashePojo> dcsHistoryCashePojoList = dcsHistoryCasheRep.findAll();
//            System.out.println("数据量：" + dcsHistoryCashePojoList.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存Dcs发送的数据到数据库中的历史表中H000至H199
     * @param offset
     * @param value
     * @param timestamp
     */
    public void save(int offset, int value, Timestamp timestamp){
        if(offset<20){
            dcs000_019Service.saveValue(offset,value,timestamp);
        }else if(offset < 40){
            dcs020_039Service.saveValue(offset,value,timestamp);
        }else if(offset < 60){
            dcs040_059Service.saveValue(offset,value,timestamp);
        }else if(offset < 80){
            dcs060_079Service.saveValue(offset,value,timestamp);
        }else if(offset < 100){
            dcs080_099Service.saveValue(offset,value,timestamp);
        }else if(offset < 120){
            dcs100_119Service.saveValue(offset,value,timestamp);
        }else if(offset < 140){
            dcs120_139Service.saveValue(offset,value,timestamp);
        }else if(offset < 160){
            dcs140_159Service.saveValue(offset,value,timestamp);
        }else if(offset < 180){
            dcs160_179Service.saveValue(offset,value,timestamp);
        }else if(offset < 200){
            dcs180_199Service.saveValue(offset,value,timestamp);
        }
    }


    public static void main(String[] args) {
        getHistroyObj(0,1,Timestamp.valueOf(LocalDateTime.now()));
    }


    public static Object getHistroyObj(int offset,int value,Timestamp timestamp){
        Object object = null;
        String className = "com.gaussic.model.dcs_history.H" + String.format("%03d", offset) + "Pojo";
        String methodSetV = "setV";
        String methodSetTime = "setvTime";
        String methodId = "setId";
        try {
            Class c = Class.forName(className);
            object = c.newInstance();

            Method methodForV = c.getMethod(methodSetV, Float.class);
            Method methodForTime = c.getMethod(methodSetTime,Timestamp.class);
            Method methodForId = c.getMethod(methodId,Long.class);

            methodForV.invoke(object,Float.valueOf(value));
            methodForTime.invoke(object,timestamp);
            String str = String.valueOf(new Date().getTime());
            int len = str.length();
            String resultStr = str.substring(len-10);
            Long id = Long.valueOf(resultStr);
            methodForId.invoke(object, id.longValue());
        } catch (ClassNotFoundException | InstantiationException|NoSuchMethodException|InvocationTargetException
                |IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}
