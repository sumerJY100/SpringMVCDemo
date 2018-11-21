package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs180_199Service {

    @Autowired
    private H180Rep h180Rep;
    @Autowired
    private H181Rep h181Rep;
    @Autowired
    private H182Rep h182Rep;
    @Autowired
    private H183Rep h183Rep;
    @Autowired
    private H184Rep h184Rep;
    @Autowired
    private H185Rep h185Rep;
    @Autowired
    private H186Rep h186Rep;
    @Autowired
    private H187Rep h187Rep;
    @Autowired
    private H188Rep h188Rep;
    @Autowired
    private H189Rep h189Rep;
    @Autowired
    private H190Rep h190Rep;
    @Autowired
    private H191Rep h191Rep;
    @Autowired
    private H192Rep h192Rep;
    @Autowired
    private H193Rep h193Rep;
    @Autowired
    private H194Rep h194Rep;
    @Autowired
    private H195Rep h195Rep;
    @Autowired
    private H196Rep h196Rep;
    @Autowired
    private H197Rep h197Rep;
    @Autowired
    private H198Rep h198Rep;
    @Autowired
    private H199Rep h199Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end) {
        List<Object> list = null;

        try {
            if (offset == 180) {
                list = Arrays.asList(h180Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 181) {
                list = Arrays.asList(h181Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 182) {
                list = Arrays.asList(h182Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 183) {
                list = Arrays.asList(h183Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 184) {
                list = Arrays.asList(h184Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 185) {
                list = Arrays.asList(h185Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 186) {
                list = Arrays.asList(h186Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 187) {
                list = Arrays.asList(h187Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 188) {
                list = Arrays.asList(h188Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 189) {
                list = Arrays.asList(h189Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 190) {
                list = Arrays.asList(h190Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 191) {
                list = Arrays.asList(h191Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 192) {
                list = Arrays.asList(h192Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 193) {
                list = Arrays.asList(h193Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 194) {
                list = Arrays.asList(h194Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 195) {
                list = Arrays.asList(h195Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 196) {
                list = Arrays.asList(h196Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 197) {
                list = Arrays.asList(h197Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 198) {
                list = Arrays.asList(h198Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 199) {
                list = Arrays.asList(h199Rep.findByVTimeBetween(begin, end).toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveValue(int offset, int value, Timestamp timestamp) {
        try {
            Object obj = DcsHistoryService.getHistroyObj(offset, value, timestamp);
            if (offset == 180) {
                h180Rep.saveAndFlush((H180Pojo) obj);
            } else if (offset == 181) {
                h181Rep.saveAndFlush((H181Pojo) obj);
            } else if (offset == 182) {
                h182Rep.saveAndFlush((H182Pojo) obj);
            } else if (offset == 183) {
                h183Rep.saveAndFlush((H183Pojo) obj);
            } else if (offset == 184) {
                h184Rep.saveAndFlush((H184Pojo) obj);
            } else if (offset == 185) {
                h185Rep.saveAndFlush((H185Pojo) obj);
            } else if (offset == 186) {
                h186Rep.saveAndFlush((H186Pojo) obj);
            } else if (offset == 187) {
                h187Rep.saveAndFlush((H187Pojo) obj);
            } else if (offset == 188) {
                h188Rep.saveAndFlush((H188Pojo) obj);
            } else if (offset == 189) {
                h189Rep.saveAndFlush((H189Pojo) obj);
            } else if (offset == 190) {
                h190Rep.saveAndFlush((H190Pojo) obj);
            } else if (offset == 191) {
                h191Rep.saveAndFlush((H191Pojo) obj);
            } else if (offset == 192) {
                h192Rep.saveAndFlush((H192Pojo) obj);
            } else if (offset == 193) {
                h193Rep.saveAndFlush((H193Pojo) obj);
            } else if (offset == 194) {
                h194Rep.saveAndFlush((H194Pojo) obj);
            } else if (offset == 195) {
                h195Rep.saveAndFlush((H195Pojo) obj);
            } else if (offset == 196) {
                h196Rep.saveAndFlush((H196Pojo) obj);
            } else if (offset == 197) {
                h197Rep.saveAndFlush((H197Pojo) obj);
            } else if (offset == 198) {
                h198Rep.saveAndFlush((H198Pojo) obj);
            } else if (offset == 199) {
                h199Rep.saveAndFlush((H199Pojo) obj);
            }
        } catch (Exception e) {
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," +value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
