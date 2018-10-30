package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs040_059Service {

    @Autowired
    private H040Rep h040Rep;
    @Autowired
    private H041Rep h041Rep;
    @Autowired
    private H042Rep h042Rep;
    @Autowired
    private H043Rep h043Rep;
    @Autowired
    private H044Rep h044Rep;
    @Autowired
    private H045Rep h045Rep;
    @Autowired
    private H046Rep h046Rep;
    @Autowired
    private H047Rep h047Rep;
    @Autowired
    private H048Rep h048Rep;
    @Autowired
    private H049Rep h049Rep;
    @Autowired
    private H050Rep h050Rep;
    @Autowired
    private H051Rep h051Rep;
    @Autowired
    private H052Rep h052Rep;
    @Autowired
    private H053Rep h053Rep;
    @Autowired
    private H054Rep h054Rep;
    @Autowired
    private H055Rep h055Rep;
    @Autowired
    private H056Rep h056Rep;
    @Autowired
    private H057Rep h057Rep;
    @Autowired
    private H058Rep h058Rep;
    @Autowired
    private H059Rep h059Rep;

    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end){
        List<Object> list = null;

        try {
            if (offset == 40) {
                list = Arrays.asList(h040Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 41) {
                list = Arrays.asList(h041Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 42) {
                list = Arrays.asList(h042Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 43) {
                list = Arrays.asList(h043Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 44) {
                list = Arrays.asList(h044Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 45) {
                list = Arrays.asList(h045Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 46) {
                list = Arrays.asList(h046Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 47) {
                list = Arrays.asList(h047Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 48) {
                list = Arrays.asList(h048Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 49) {
                list = Arrays.asList(h049Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 50) {
                list = Arrays.asList(h050Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 51) {
                list = Arrays.asList(h051Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 52) {
                list = Arrays.asList(h052Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 53) {
                list = Arrays.asList(h053Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 54) {
                list = Arrays.asList(h054Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 55) {
                list = Arrays.asList(h055Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 56) {
                list = Arrays.asList(h056Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 57) {
                list = Arrays.asList(h057Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 58) {
                list = Arrays.asList(h058Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 59) {
                list = Arrays.asList(h059Rep.findByVTimeBetween(begin, end).toArray());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public void saveValue(int offset,int value, Timestamp timestamp){
        Object obj = DcsHistoryService.getHistroyObj(offset,value,timestamp);
        if (offset ==40) {
            h040Rep.saveAndFlush((H040Pojo) obj);
        }else if(offset ==41){
            h041Rep.saveAndFlush((H041Pojo) obj);
        }else if(offset ==42){
            h042Rep.saveAndFlush((H042Pojo) obj);
        }else if(offset ==43){
            h043Rep.saveAndFlush((H043Pojo) obj);
        }else if(offset ==44){
            h044Rep.saveAndFlush((H044Pojo) obj);
        }else if(offset ==45){
            h045Rep.saveAndFlush((H045Pojo) obj);
        }else if(offset ==46){
            h046Rep.saveAndFlush((H046Pojo) obj);
        }else if(offset ==47){
            h047Rep.saveAndFlush((H047Pojo) obj);
        }else if(offset ==48){
            h048Rep.saveAndFlush((H048Pojo) obj);
        }else if(offset ==49){
            h049Rep.saveAndFlush((H049Pojo) obj);
        }else if(offset == 50){
            h050Rep.saveAndFlush((H050Pojo) obj);
        }else if(offset == 51){
            h051Rep.saveAndFlush((H051Pojo) obj);
        }else if(offset == 52){
            h052Rep.saveAndFlush((H052Pojo) obj);
        }else if(offset == 53){
            h053Rep.saveAndFlush((H053Pojo) obj);
        }else if(offset == 54){
            h054Rep.saveAndFlush((H054Pojo) obj);
        }else if(offset == 55){
            h055Rep.saveAndFlush((H055Pojo) obj);
        }else if(offset == 56){
            h056Rep.saveAndFlush((H056Pojo) obj);
        }else if(offset == 57){
            h057Rep.saveAndFlush((H057Pojo) obj);
        }else if(offset == 58){
            h058Rep.saveAndFlush((H058Pojo) obj);
        }else if(offset == 59){
            h059Rep.saveAndFlush((H059Pojo) obj);
        }
    }
}
