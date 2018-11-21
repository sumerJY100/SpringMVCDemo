package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs100_119Service {

    @Autowired
    private H100Rep h100Rep;
    @Autowired
    private H101Rep h101Rep;
    @Autowired
    private H102Rep h102Rep;
    @Autowired
    private H103Rep h103Rep;
    @Autowired
    private H104Rep h104Rep;
    @Autowired
    private H105Rep h105Rep;
    @Autowired
    private H106Rep h106Rep;
    @Autowired
    private H107Rep h107Rep;
    @Autowired
    private H108Rep h108Rep;
    @Autowired
    private H109Rep h109Rep;
    @Autowired
    private H110Rep h110Rep;
    @Autowired
    private H111Rep h111Rep;
    @Autowired
    private H112Rep h112Rep;
    @Autowired
    private H113Rep h113Rep;
    @Autowired
    private H114Rep h114Rep;
    @Autowired
    private H115Rep h115Rep;
    @Autowired
    private H116Rep h116Rep;
    @Autowired
    private H117Rep h117Rep;
    @Autowired
    private H118Rep h118Rep;
    @Autowired
    private H119Rep h119Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end) {
        List<Object> list = null;

        try {
            if (offset == 100) {
                list = Arrays.asList(h100Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 101) {
                list = Arrays.asList(h101Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 102) {
                list = Arrays.asList(h102Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 103) {
                list = Arrays.asList(h103Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 104) {
                list = Arrays.asList(h104Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 105) {
                list = Arrays.asList(h105Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 106) {
                list = Arrays.asList(h106Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 107) {
                list = Arrays.asList(h107Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 108) {
                list = Arrays.asList(h108Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 109) {
                list = Arrays.asList(h109Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 110) {
                list = Arrays.asList(h110Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 111) {
                list = Arrays.asList(h111Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 112) {
                list = Arrays.asList(h112Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 113) {
                list = Arrays.asList(h113Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 114) {
                list = Arrays.asList(h114Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 115) {
                list = Arrays.asList(h115Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 116) {
                list = Arrays.asList(h116Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 117) {
                list = Arrays.asList(h117Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 118) {
                list = Arrays.asList(h118Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 119) {
                list = Arrays.asList(h119Rep.findByVTimeBetween(begin, end).toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveValue(int offset, int value, Timestamp timestamp) {
        try {
            Object obj = DcsHistoryService.getHistroyObj(offset, value, timestamp);
            if (offset == 100) {
                h100Rep.saveAndFlush((H100Pojo) obj);
            } else if (offset == 101) {
                h101Rep.saveAndFlush((H101Pojo) obj);
            } else if (offset == 102) {
                h102Rep.saveAndFlush((H102Pojo) obj);
            } else if (offset == 103) {
                h103Rep.saveAndFlush((H103Pojo) obj);
            } else if (offset == 104) {
                h104Rep.saveAndFlush((H104Pojo) obj);
            } else if (offset == 105) {
                h105Rep.saveAndFlush((H105Pojo) obj);
            } else if (offset == 106) {
                h106Rep.saveAndFlush((H106Pojo) obj);
            } else if (offset == 107) {
                h107Rep.saveAndFlush((H107Pojo) obj);
            } else if (offset == 108) {
                h108Rep.saveAndFlush((H108Pojo) obj);
            } else if (offset == 109) {
                h109Rep.saveAndFlush((H109Pojo) obj);
            } else if (offset == 110) {
                h110Rep.saveAndFlush((H110Pojo) obj);
            } else if (offset == 111) {
                h111Rep.saveAndFlush((H111Pojo) obj);
            } else if (offset == 112) {
                h112Rep.saveAndFlush((H112Pojo) obj);
            } else if (offset == 113) {
                h113Rep.saveAndFlush((H113Pojo) obj);
            } else if (offset == 114) {
                h114Rep.saveAndFlush((H114Pojo) obj);
            } else if (offset == 115) {
                h115Rep.saveAndFlush((H115Pojo) obj);
            } else if (offset == 116) {
                h116Rep.saveAndFlush((H116Pojo) obj);
            } else if (offset == 117) {
                h117Rep.saveAndFlush((H117Pojo) obj);
            } else if (offset == 118) {
                h118Rep.saveAndFlush((H118Pojo) obj);
            } else if (offset == 119) {
                h119Rep.saveAndFlush((H119Pojo) obj);
            }
        } catch (Exception e) {
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," +value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
