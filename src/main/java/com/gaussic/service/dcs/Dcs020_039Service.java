package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs020_039Service {

    @Autowired
    private H020Rep h020Rep;
    @Autowired
    private H021Rep h021Rep;
    @Autowired
    private H022Rep h022Rep;
    @Autowired
    private H023Rep h023Rep;
    @Autowired
    private H024Rep h024Rep;
    @Autowired
    private H025Rep h025Rep;
    @Autowired
    private H026Rep h026Rep;
    @Autowired
    private H027Rep h027Rep;
    @Autowired
    private H028Rep h028Rep;
    @Autowired
    private H029Rep h029Rep;
    @Autowired
    private H030Rep h030Rep;
    @Autowired
    private H031Rep h031Rep;
    @Autowired
    private H032Rep h032Rep;
    @Autowired
    private H033Rep h033Rep;
    @Autowired
    private H034Rep h034Rep;
    @Autowired
    private H035Rep h035Rep;
    @Autowired
    private H036Rep h036Rep;
    @Autowired
    private H037Rep h037Rep;
    @Autowired
    private H038Rep h038Rep;
    @Autowired
    private H039Rep h039Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end){
        List<Object> list = null;

        try {
            if (offset == 20) {
                list = Arrays.asList(h020Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 21) {
                list = Arrays.asList(h021Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 22) {
                list = Arrays.asList(h022Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 23) {
                list = Arrays.asList(h023Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 24) {
                list = Arrays.asList(h024Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 25) {
                list = Arrays.asList(h025Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 26) {
                list = Arrays.asList(h026Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 27) {
                list = Arrays.asList(h027Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 28) {
                list = Arrays.asList(h028Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 29) {
                list = Arrays.asList(h029Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 30) {
                list = Arrays.asList(h030Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 31) {
                list = Arrays.asList(h031Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 32) {
                list = Arrays.asList(h032Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 33) {
                list = Arrays.asList(h033Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 34) {
                list = Arrays.asList(h034Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 35) {
                list = Arrays.asList(h035Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 36) {
                list = Arrays.asList(h036Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 37) {
                list = Arrays.asList(h037Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 38) {
                list = Arrays.asList(h038Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 39) {
                list = Arrays.asList(h039Rep.findByVTimeBetween(begin, end).toArray());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public void saveValue(int offset,int value, Timestamp timestamp){
        try {
            Object obj = DcsHistoryService.getHistroyObj(offset, value, timestamp);
            if (offset == 20) {
                h020Rep.saveAndFlush((H020Pojo) obj);
            } else if (offset == 21) {
                h021Rep.saveAndFlush((H021Pojo) obj);
            } else if (offset == 22) {
                h022Rep.saveAndFlush((H022Pojo) obj);
            } else if (offset == 23) {
                h023Rep.saveAndFlush((H023Pojo) obj);
            } else if (offset == 24) {
                h024Rep.saveAndFlush((H024Pojo) obj);
            } else if (offset == 25) {
                h025Rep.saveAndFlush((H025Pojo) obj);
            } else if (offset == 26) {
                h026Rep.saveAndFlush((H026Pojo) obj);
            } else if (offset == 27) {
                h027Rep.saveAndFlush((H027Pojo) obj);
            } else if (offset == 28) {
                h028Rep.saveAndFlush((H028Pojo) obj);
            } else if (offset == 29) {
                h029Rep.saveAndFlush((H029Pojo) obj);
            } else if (offset == 30) {
                h030Rep.saveAndFlush((H030Pojo) obj);
            } else if (offset == 31) {
                h031Rep.saveAndFlush((H031Pojo) obj);
            } else if (offset == 32) {
                h032Rep.saveAndFlush((H032Pojo) obj);
            } else if (offset == 33) {
                h033Rep.saveAndFlush((H033Pojo) obj);
            } else if (offset == 34) {
                h034Rep.saveAndFlush((H034Pojo) obj);
            } else if (offset == 35) {
                h035Rep.saveAndFlush((H035Pojo) obj);
            } else if (offset == 36) {
                h036Rep.saveAndFlush((H036Pojo) obj);
            } else if (offset == 37) {
                h037Rep.saveAndFlush((H037Pojo) obj);
            } else if (offset == 38) {
                h038Rep.saveAndFlush((H038Pojo) obj);
            } else if (offset == 39) {
                h039Rep.saveAndFlush((H039Pojo) obj);
            }
        }catch (Exception e){
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," +value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
