package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs060_079Service {

    @Autowired
    private H060Rep h060Rep;
    @Autowired
    private H061Rep h061Rep;
    @Autowired
    private H062Rep h062Rep;
    @Autowired
    private H063Rep h063Rep;
    @Autowired
    private H064Rep h064Rep;
    @Autowired
    private H065Rep h065Rep;
    @Autowired
    private H066Rep h066Rep;
    @Autowired
    private H067Rep h067Rep;
    @Autowired
    private H068Rep h068Rep;
    @Autowired
    private H069Rep h069Rep;
    @Autowired
    private H070Rep h070Rep;
    @Autowired
    private H071Rep h071Rep;
    @Autowired
    private H072Rep h072Rep;
    @Autowired
    private H073Rep h073Rep;
    @Autowired
    private H074Rep h074Rep;
    @Autowired
    private H075Rep h075Rep;
    @Autowired
    private H076Rep h076Rep;
    @Autowired
    private H077Rep h077Rep;
    @Autowired
    private H078Rep h078Rep;
    @Autowired
    private H079Rep h079Rep;


    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end) {
        List<Object> list = null;

        try {
            if (offset == 60) {
                list = Arrays.asList(h060Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 61) {
                list = Arrays.asList(h061Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 62) {
                list = Arrays.asList(h062Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 63) {
                list = Arrays.asList(h063Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 64) {
                list = Arrays.asList(h064Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 65) {
                list = Arrays.asList(h065Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 66) {
                list = Arrays.asList(h066Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 67) {
                list = Arrays.asList(h067Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 68) {
                list = Arrays.asList(h068Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 69) {
                list = Arrays.asList(h069Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 70) {
                list = Arrays.asList(h070Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 71) {
                list = Arrays.asList(h071Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 72) {
                list = Arrays.asList(h072Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 73) {
                list = Arrays.asList(h073Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 74) {
                list = Arrays.asList(h074Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 75) {
                System.out.println("service查询：" + 75);
                list = Arrays.asList(h075Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 76) {
                System.out.println("service查询：" + 76);
                list = Arrays.asList(h076Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 77) {
                list = Arrays.asList(h077Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 78) {
                list = Arrays.asList(h078Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 79) {
                list = Arrays.asList(h079Rep.findByVTimeBetween(begin, end).toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveValue(int offset, int value, Timestamp timestamp) {
        try {
            Object obj = DcsHistoryService.getHistroyObj(offset, value, timestamp);
            if (offset == 60) {
                h060Rep.saveAndFlush((H060Pojo) obj);
            } else if (offset == 61) {
                h061Rep.saveAndFlush((H061Pojo) obj);
            } else if (offset == 62) {
                h062Rep.saveAndFlush((H062Pojo) obj);
            } else if (offset == 63) {
                h063Rep.saveAndFlush((H063Pojo) obj);
            } else if (offset == 64) {
                h064Rep.saveAndFlush((H064Pojo) obj);
            } else if (offset == 65) {
                h065Rep.saveAndFlush((H065Pojo) obj);
            } else if (offset == 66) {
                h066Rep.saveAndFlush((H066Pojo) obj);
            } else if (offset == 67) {
                h067Rep.saveAndFlush((H067Pojo) obj);
            } else if (offset == 68) {
                h068Rep.saveAndFlush((H068Pojo) obj);
            } else if (offset == 69) {
                h069Rep.saveAndFlush((H069Pojo) obj);
            } else if (offset == 70) {
                h070Rep.saveAndFlush((H070Pojo) obj);
            } else if (offset == 71) {
                h071Rep.saveAndFlush((H071Pojo) obj);
            } else if (offset == 72) {
                h072Rep.saveAndFlush((H072Pojo) obj);
            } else if (offset == 73) {
                h073Rep.saveAndFlush((H073Pojo) obj);
            } else if (offset == 74) {
                h074Rep.saveAndFlush((H074Pojo) obj);
            } else if (offset == 75) {
                h075Rep.saveAndFlush((H075Pojo) obj);
            } else if (offset == 76) {
                h076Rep.saveAndFlush((H076Pojo) obj);
            } else if (offset == 77) {
                h077Rep.saveAndFlush((H077Pojo) obj);
            } else if (offset == 78) {
                h078Rep.saveAndFlush((H078Pojo) obj);
            } else if (offset == 79) {
                h079Rep.saveAndFlush((H079Pojo) obj);
            }
        } catch (Exception e) {
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," +value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
