package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs120_139Service {

    @Autowired
    private H120Rep h120Rep;
    @Autowired
    private H121Rep h121Rep;
    @Autowired
    private H122Rep h122Rep;
    @Autowired
    private H123Rep h123Rep;
    @Autowired
    private H124Rep h124Rep;
    @Autowired
    private H125Rep h125Rep;
    @Autowired
    private H126Rep h126Rep;
    @Autowired
    private H127Rep h127Rep;
    @Autowired
    private H128Rep h128Rep;
    @Autowired
    private H129Rep h129Rep;
    @Autowired
    private H130Rep h130Rep;
    @Autowired
    private H131Rep h131Rep;
    @Autowired
    private H132Rep h132Rep;
    @Autowired
    private H133Rep h133Rep;
    @Autowired
    private H134Rep h134Rep;
    @Autowired
    private H135Rep h135Rep;
    @Autowired
    private H136Rep h136Rep;
    @Autowired
    private H137Rep h137Rep;
    @Autowired
    private H138Rep h138Rep;
    @Autowired
    private H139Rep h139Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end) {
        List<Object> list = null;

        try {
            if (offset == 120) {
                list = Arrays.asList(h120Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 121) {
                list = Arrays.asList(h121Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 122) {
                list = Arrays.asList(h122Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 123) {
                list = Arrays.asList(h123Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 124) {
                list = Arrays.asList(h124Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 125) {
                list = Arrays.asList(h125Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 126) {
                list = Arrays.asList(h126Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 127) {
                list = Arrays.asList(h127Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 128) {
                list = Arrays.asList(h128Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 129) {
                list = Arrays.asList(h129Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 130) {
                list = Arrays.asList(h130Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 131) {
                list = Arrays.asList(h131Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 132) {
                list = Arrays.asList(h132Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 133) {
                list = Arrays.asList(h133Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 134) {
                list = Arrays.asList(h134Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 135) {
                list = Arrays.asList(h135Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 136) {
                list = Arrays.asList(h136Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 137) {
                list = Arrays.asList(h137Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 138) {
                list = Arrays.asList(h138Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 139) {
                list = Arrays.asList(h139Rep.findByVTimeBetween(begin, end).toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveValue(int offset, int value, Timestamp timestamp) {
        try {
            Object obj = DcsHistoryService.getHistroyObj(offset, value, timestamp);
            if (offset == 120) {
                h120Rep.saveAndFlush((H120Pojo) obj);
            } else if (offset == 121) {
                h121Rep.saveAndFlush((H121Pojo) obj);
            } else if (offset == 122) {
                h122Rep.saveAndFlush((H122Pojo) obj);
            } else if (offset == 123) {
                h123Rep.saveAndFlush((H123Pojo) obj);
            } else if (offset == 124) {
                h124Rep.saveAndFlush((H124Pojo) obj);
            } else if (offset == 125) {
                h125Rep.saveAndFlush((H125Pojo) obj);
            } else if (offset == 126) {
                h126Rep.saveAndFlush((H126Pojo) obj);
            } else if (offset == 127) {
                h127Rep.saveAndFlush((H127Pojo) obj);
            } else if (offset == 128) {
                h128Rep.saveAndFlush((H128Pojo) obj);
            } else if (offset == 129) {
                h129Rep.saveAndFlush((H129Pojo) obj);
            } else if (offset == 130) {
                h130Rep.saveAndFlush((H130Pojo) obj);
            } else if (offset == 131) {
                h131Rep.saveAndFlush((H131Pojo) obj);
            } else if (offset == 132) {
                h132Rep.saveAndFlush((H132Pojo) obj);
            } else if (offset == 133) {
                h133Rep.saveAndFlush((H133Pojo) obj);
            } else if (offset == 134) {
                h134Rep.saveAndFlush((H134Pojo) obj);
            } else if (offset == 135) {
                h135Rep.saveAndFlush((H135Pojo) obj);
            } else if (offset == 136) {
                h136Rep.saveAndFlush((H136Pojo) obj);
            } else if (offset == 137) {
                h137Rep.saveAndFlush((H137Pojo) obj);
            } else if (offset == 138) {
                h138Rep.saveAndFlush((H138Pojo) obj);
            } else if (offset == 139) {
                h139Rep.saveAndFlush((H139Pojo) obj);
            }
        } catch (Exception e) {
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," +value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
