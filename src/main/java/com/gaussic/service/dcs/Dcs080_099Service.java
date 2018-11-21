package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs080_099Service {

    @Autowired
    private H080Rep h080Rep;
    @Autowired
    private H081Rep h081Rep;
    @Autowired
    private H082Rep h082Rep;
    @Autowired
    private H083Rep h083Rep;
    @Autowired
    private H084Rep h084Rep;
    @Autowired
    private H085Rep h085Rep;
    @Autowired
    private H086Rep h086Rep;
    @Autowired
    private H087Rep h087Rep;
    @Autowired
    private H088Rep h088Rep;
    @Autowired
    private H089Rep h089Rep;
    @Autowired
    private H090Rep h090Rep;
    @Autowired
    private H091Rep h091Rep;
    @Autowired
    private H092Rep h092Rep;
    @Autowired
    private H093Rep h093Rep;
    @Autowired
    private H094Rep h094Rep;
    @Autowired
    private H095Rep h095Rep;
    @Autowired
    private H096Rep h096Rep;
    @Autowired
    private H097Rep h097Rep;
    @Autowired
    private H098Rep h098Rep;
    @Autowired
    private H099Rep h099Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end) {
        List<Object> list = null;

        try {
            if (offset == 80) {
                list = Arrays.asList(h080Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 81) {
                list = Arrays.asList(h081Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 82) {
                list = Arrays.asList(h082Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 83) {
                list = Arrays.asList(h083Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 84) {
                list = Arrays.asList(h084Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 85) {
                list = Arrays.asList(h085Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 86) {
                list = Arrays.asList(h086Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 87) {
                list = Arrays.asList(h087Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 88) {
                list = Arrays.asList(h088Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 89) {
                list = Arrays.asList(h089Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 90) {
                list = Arrays.asList(h090Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 91) {
                list = Arrays.asList(h091Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 92) {
                list = Arrays.asList(h092Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 93) {
                list = Arrays.asList(h093Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 94) {
                list = Arrays.asList(h094Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 95) {
                list = Arrays.asList(h095Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 96) {
                list = Arrays.asList(h096Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 97) {
                list = Arrays.asList(h097Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 98) {
                list = Arrays.asList(h098Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 99) {
                list = Arrays.asList(h099Rep.findByVTimeBetween(begin, end).toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveValue(int offset, int value, Timestamp timestamp) {
        try {
            Object obj = DcsHistoryService.getHistroyObj(offset, value, timestamp);
            if (offset == 80) {
                h080Rep.saveAndFlush((H080Pojo) obj);
            } else if (offset == 81) {
                h081Rep.saveAndFlush((H081Pojo) obj);
            } else if (offset == 82) {
                h082Rep.saveAndFlush((H082Pojo) obj);
            } else if (offset == 83) {
                h083Rep.saveAndFlush((H083Pojo) obj);
            } else if (offset == 84) {
                h084Rep.saveAndFlush((H084Pojo) obj);
            } else if (offset == 85) {
                h085Rep.saveAndFlush((H085Pojo) obj);
            } else if (offset == 86) {
                h086Rep.saveAndFlush((H086Pojo) obj);
            } else if (offset == 87) {
                h087Rep.saveAndFlush((H087Pojo) obj);
            } else if (offset == 88) {
                h088Rep.saveAndFlush((H088Pojo) obj);
            } else if (offset == 89) {
                h089Rep.saveAndFlush((H089Pojo) obj);
            } else if (offset == 90) {
                h090Rep.saveAndFlush((H090Pojo) obj);
            } else if (offset == 91) {
                h091Rep.saveAndFlush((H091Pojo) obj);
            } else if (offset == 92) {
                h092Rep.saveAndFlush((H092Pojo) obj);
            } else if (offset == 93) {
                h093Rep.saveAndFlush((H093Pojo) obj);
            } else if (offset == 94) {
                h094Rep.saveAndFlush((H094Pojo) obj);
            } else if (offset == 95) {
                h095Rep.saveAndFlush((H095Pojo) obj);
            } else if (offset == 96) {
                h096Rep.saveAndFlush((H096Pojo) obj);
            } else if (offset == 97) {
                h097Rep.saveAndFlush((H097Pojo) obj);
            } else if (offset == 98) {
                h098Rep.saveAndFlush((H098Pojo) obj);
            } else if (offset == 99) {
                h099Rep.saveAndFlush((H099Pojo) obj);
            }
        } catch (Exception e) {
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," + value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
