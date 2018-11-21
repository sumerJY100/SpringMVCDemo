package com.gaussic.service.dcs;

import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcsTemp.*;
import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs000_019Service {

    @Autowired
    private H000Rep h000Rep;
    @Autowired
    private H001Rep h001Rep;
    @Autowired
    private H002Rep h002Rep;
    @Autowired
    private H003Rep h003Rep;
    @Autowired
    private H004Rep h004Rep;
    @Autowired
    private H005Rep h005Rep;
    @Autowired
    private H006Rep h006Rep;
    @Autowired
    private H007Rep h007Rep;
    @Autowired
    private H008Rep h008Rep;
    @Autowired
    private H009Rep h009Rep;
    @Autowired
    private H010Rep h010Rep;
    @Autowired
    private H011Rep h011Rep;
    @Autowired
    private H012Rep h012Rep;
    @Autowired
    private H013Rep h013Rep;
    @Autowired
    private H014Rep h014Rep;
    @Autowired
    private H015Rep h015Rep;
    @Autowired
    private H016Rep h016Rep;
    @Autowired
    private H017Rep h017Rep;
    @Autowired
    private H018Rep h018Rep;
    @Autowired
    private H019Rep h019Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end){
        List<Object> list = null;

        try {
            if (offset == 0) {
                list = Arrays.asList(h000Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 1) {
                list = Arrays.asList(h001Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 2) {
                list = Arrays.asList(h002Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 3) {
                list = Arrays.asList(h003Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 4) {
                list = Arrays.asList(h004Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 5) {
                list = Arrays.asList(h005Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 6) {
                list = Arrays.asList(h006Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 7) {
                list = Arrays.asList(h007Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 8) {
                list = Arrays.asList(h008Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 9) {
                list = Arrays.asList(h009Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 10) {
                list = Arrays.asList(h010Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 11) {
                list = Arrays.asList(h011Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 12) {
                list = Arrays.asList(h012Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 13) {
                list = Arrays.asList(h013Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 14) {
                list = Arrays.asList(h014Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 15) {
                list = Arrays.asList(h015Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 16) {
                list = Arrays.asList(h016Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 17) {
                list = Arrays.asList(h017Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 18) {
                list = Arrays.asList(h018Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 19) {
                list = Arrays.asList(h019Rep.findByVTimeBetween(begin, end).toArray());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    public void saveValue(int offset, int value, Timestamp timestamp)  {

        try {
            Object obj = DcsHistoryService.getHistroyObj(offset,value,timestamp);
            if (offset == 0) {
                h000Rep.saveAndFlush((H000Pojo) obj);
            } else if (offset == 1) {
                h001Rep.saveAndFlush((H001Pojo) obj);
            } else if (offset == 2) {
                h002Rep.saveAndFlush((H002Pojo) obj);
            } else if (offset == 3) {
                h003Rep.saveAndFlush((H003Pojo) obj);
            } else if (offset == 4) {
                h004Rep.saveAndFlush((H004Pojo) obj);
            } else if (offset == 5) {
                h005Rep.saveAndFlush((H005Pojo) obj);
            } else if (offset == 6) {
                h006Rep.saveAndFlush((H006Pojo) obj);
            } else if (offset == 7) {
                h007Rep.saveAndFlush((H007Pojo) obj);
            } else if (offset == 8) {
                h008Rep.saveAndFlush((H008Pojo) obj);
            } else if (offset == 9) {
                h009Rep.saveAndFlush((H009Pojo) obj);
            } else if (offset == 10) {
                h010Rep.saveAndFlush((H010Pojo) obj);
            } else if (offset == 11) {
                h011Rep.saveAndFlush((H011Pojo) obj);
            } else if (offset == 12) {
                h012Rep.saveAndFlush((H012Pojo) obj);
            } else if (offset == 13) {
                h013Rep.saveAndFlush((H013Pojo) obj);
            } else if (offset == 14) {
                h014Rep.saveAndFlush((H014Pojo) obj);
            } else if (offset == 15) {
                h015Rep.saveAndFlush((H015Pojo) obj);
            } else if (offset == 16) {
                h016Rep.saveAndFlush((H016Pojo) obj);
            } else if (offset == 17) {
                h017Rep.saveAndFlush((H017Pojo) obj);
            } else if (offset == 18) {
                h018Rep.saveAndFlush((H018Pojo) obj);
            } else if (offset == 19) {
                h019Rep.saveAndFlush((H019Pojo) obj);
            }
        }catch (Exception e){
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," +value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
