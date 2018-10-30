package com.gaussic.service.dcs;

import com.gaussic.model.dcs_history.*;
import com.gaussic.repository.dcs_history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class Dcs140_159Service {

    @Autowired
    private H140Rep h140Rep;
    @Autowired
    private H141Rep h141Rep;
    @Autowired
    private H142Rep h142Rep;
    @Autowired
    private H143Rep h143Rep;
    @Autowired
    private H144Rep h144Rep;
    @Autowired
    private H145Rep h145Rep;
    @Autowired
    private H146Rep h146Rep;
    @Autowired
    private H147Rep h147Rep;
    @Autowired
    private H148Rep h148Rep;
    @Autowired
    private H149Rep h149Rep;
    @Autowired
    private H150Rep h150Rep;
    @Autowired
    private H151Rep h151Rep;
    @Autowired
    private H152Rep h152Rep;
    @Autowired
    private H153Rep h153Rep;
    @Autowired
    private H154Rep h154Rep;
    @Autowired
    private H155Rep h155Rep;
    @Autowired
    private H156Rep h156Rep;
    @Autowired
    private H157Rep h157Rep;
    @Autowired
    private H158Rep h158Rep;
    @Autowired
    private H159Rep h159Rep;
    public List<Object> findByTime(Integer offset, Timestamp begin, Timestamp end){
        List<Object> list = null;

        try {
            if (offset == 140) {
                list = Arrays.asList(h140Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 141) {
                list = Arrays.asList(h141Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 142) {
                list = Arrays.asList(h142Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 143) {
                list = Arrays.asList(h143Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 144) {
                list = Arrays.asList(h144Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 145) {
                list = Arrays.asList(h145Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 146) {
                list = Arrays.asList(h146Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 147) {
                list = Arrays.asList(h147Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 148) {
                list = Arrays.asList(h148Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 149) {
                list = Arrays.asList(h149Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 150) {
                list = Arrays.asList(h150Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 151) {
                list = Arrays.asList(h151Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 152) {
                list = Arrays.asList(h152Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 153) {
                list = Arrays.asList(h153Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 154) {
                list = Arrays.asList(h154Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 155) {
                list = Arrays.asList(h155Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 156) {
                list = Arrays.asList(h156Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 157) {
                list = Arrays.asList(h157Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 158) {
                list = Arrays.asList(h158Rep.findByVTimeBetween(begin, end).toArray());
            } else if (offset == 159) {
                list = Arrays.asList(h159Rep.findByVTimeBetween(begin, end).toArray());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void saveValue(int offset,int value, Timestamp timestamp){
        Object obj = DcsHistoryService.getHistroyObj(offset,value,timestamp);
        if (offset == 140) {
            h140Rep.saveAndFlush((H140Pojo) obj);
        }else if(offset == 141){
            h141Rep.saveAndFlush((H141Pojo) obj);
        }else if(offset == 142){
            h142Rep.saveAndFlush((H142Pojo) obj);
        }else if(offset == 143){
            h143Rep.saveAndFlush((H143Pojo) obj);
        }else if(offset == 144){
            h144Rep.saveAndFlush((H144Pojo) obj);
        }else if(offset == 145){
            h145Rep.saveAndFlush((H145Pojo) obj);
        }else if(offset == 146){
            h146Rep.saveAndFlush((H146Pojo) obj);
        }else if(offset == 147){
            h147Rep.saveAndFlush((H147Pojo) obj);
        }else if(offset == 148){
            h148Rep.saveAndFlush((H148Pojo) obj);
        }else if(offset == 149){
            h149Rep.saveAndFlush((H149Pojo) obj);
        }else if(offset == 150){
            h150Rep.saveAndFlush((H150Pojo) obj);
        }else if(offset == 151){
            h151Rep.saveAndFlush((H151Pojo) obj);
        }else if(offset == 152){
            h152Rep.saveAndFlush((H152Pojo) obj);
        }else if(offset == 153){
            h153Rep.saveAndFlush((H153Pojo) obj);
        }else if(offset == 154){
            h154Rep.saveAndFlush((H154Pojo) obj);
        }else if(offset == 155){
            h155Rep.saveAndFlush((H155Pojo) obj);
        }else if(offset == 156){
            h156Rep.saveAndFlush((H156Pojo) obj);
        }else if(offset == 157){
            h157Rep.saveAndFlush((H157Pojo) obj);
        }else if(offset == 158){
            h158Rep.saveAndFlush((H158Pojo) obj);
        }else if(offset == 159){
            h159Rep.saveAndFlush((H159Pojo) obj);
        }
    }
}
