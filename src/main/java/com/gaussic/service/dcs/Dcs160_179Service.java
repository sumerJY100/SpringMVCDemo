package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs160_179Service {

    @Autowired
    private H160Rep h160Rep;
    @Autowired
    private H161Rep h161Rep;
    @Autowired
    private H162Rep h162Rep;
    @Autowired
    private H163Rep h163Rep;
    @Autowired
    private H164Rep h164Rep;
    @Autowired
    private H165Rep h165Rep;
    @Autowired
    private H166Rep h166Rep;
    @Autowired
    private H167Rep h167Rep;
    @Autowired
    private H168Rep h168Rep;
    @Autowired
    private H169Rep h169Rep;
    @Autowired
    private H170Rep h170Rep;
    @Autowired
    private H171Rep h171Rep;
    @Autowired
    private H172Rep h172Rep;
    @Autowired
    private H173Rep h173Rep;
    @Autowired
    private H174Rep h174Rep;
    @Autowired
    private H175Rep h175Rep;
    @Autowired
    private H176Rep h176Rep;
    @Autowired
    private H177Rep h177Rep;
    @Autowired
    private H178Rep h178Rep;
    @Autowired
    private H179Rep h179Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end) {
        List<Object> list = null;

        try {
            if (offset == 160) {
                list = Arrays.asList(h160Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 161) {
                list = Arrays.asList(h161Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 162) {
                list = Arrays.asList(h162Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 163) {
                list = Arrays.asList(h163Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 164) {
                list = Arrays.asList(h164Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 165) {
                list = Arrays.asList(h165Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 166) {
                list = Arrays.asList(h166Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 167) {
                list = Arrays.asList(h167Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 168) {
                list = Arrays.asList(h168Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 169) {
                list = Arrays.asList(h169Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 170) {
                list = Arrays.asList(h170Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 171) {
                list = Arrays.asList(h171Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 172) {
                list = Arrays.asList(h172Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 173) {
                list = Arrays.asList(h173Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 174) {
                list = Arrays.asList(h174Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 175) {
                list = Arrays.asList(h175Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 176) {
                list = Arrays.asList(h176Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 177) {
                list = Arrays.asList(h177Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 178) {
                list = Arrays.asList(h178Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 179) {
                list = Arrays.asList(h179Rep.findByVTimeBetween(begin, end).toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveValue(int offset, int value, Timestamp timestamp) {
        try {
            Object obj = DcsHistoryService.getHistroyObj(offset, value, timestamp);
            if (offset == 160) {
                h160Rep.saveAndFlush((H160Pojo) obj);
            } else if (offset == 161) {
                h161Rep.saveAndFlush((H161Pojo) obj);
            } else if (offset == 162) {
                h162Rep.saveAndFlush((H162Pojo) obj);
            } else if (offset == 163) {
                h163Rep.saveAndFlush((H163Pojo) obj);
            } else if (offset == 164) {
                h164Rep.saveAndFlush((H164Pojo) obj);
            } else if (offset == 165) {
                h165Rep.saveAndFlush((H165Pojo) obj);
            } else if (offset == 166) {
                h166Rep.saveAndFlush((H166Pojo) obj);
            } else if (offset == 167) {
                h167Rep.saveAndFlush((H167Pojo) obj);
            } else if (offset == 168) {
                h168Rep.saveAndFlush((H168Pojo) obj);
            } else if (offset == 169) {
                h169Rep.saveAndFlush((H169Pojo) obj);
            } else if (offset == 176) {
                h176Rep.saveAndFlush((H176Pojo) obj);
            } else if (offset == 171) {
                h171Rep.saveAndFlush((H171Pojo) obj);
            } else if (offset == 172) {
                h172Rep.saveAndFlush((H172Pojo) obj);
            } else if (offset == 173) {
                h173Rep.saveAndFlush((H173Pojo) obj);
            } else if (offset == 174) {
                h174Rep.saveAndFlush((H174Pojo) obj);
            } else if (offset == 175) {
                h175Rep.saveAndFlush((H175Pojo) obj);
            } else if (offset == 176) {
                h176Rep.saveAndFlush((H176Pojo) obj);
            } else if (offset == 177) {
                h177Rep.saveAndFlush((H177Pojo) obj);
            } else if (offset == 178) {
                h178Rep.saveAndFlush((H178Pojo) obj);
            } else if (offset == 179) {
                h179Rep.saveAndFlush((H179Pojo) obj);
            }
        } catch (Exception e) {
            //TODO 保存数据异常
            System.out.println("offset, value, timestamp:" + offset + "," +value + "," + timestamp);
//            e.printStackTrace();
        }
    }
}
