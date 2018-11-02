package com.gaussic.quartz3;

import com.gaussic.model.dcs_history.DcsHistoryCashePojo;
import com.gaussic.repository.dcs_history.DcsHistoryCasheRep;
import com.gaussic.service.dcs.DcsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class TaskForDcsDataCache {

    @Autowired
    private DcsHistoryCasheRep dcsHistoryCasheRep;
    @Autowired
    private DcsHistoryService dcsHistoryService;

    private static Set<Integer> dcsHistoryCacheIdSets = new HashSet<>();
    private static int count = 0;

    /**
     * 此方法为定时器方法
     * 主要功能：读取dcsHisotryoCache中的数据，将数据分析后保存早H000至H199中，并清除缓存的数据
     */
    public void doJob() {
        count ++;
        try {
//            System.out.println("执行DCS缓存操作" +count);
            LocalDateTime endLocalDateTime = LocalDateTime.now();
            LocalDateTime beginLocalDateTime = endLocalDateTime.minusSeconds(30L);
            Timestamp begin = Timestamp.valueOf(beginLocalDateTime);
            Timestamp end = Timestamp.valueOf(endLocalDateTime);
//            List<DcsHistoryCashePojo> dcsHistoryCashePojoList = dcsHistoryCasheRep.findAll();
            List<DcsHistoryCashePojo> dcsHistoryCashePojoList = null;
            if(count >= 4){
                beginLocalDateTime = endLocalDateTime.minusSeconds(120L);
                begin = Timestamp.valueOf(beginLocalDateTime);
                dcsHistoryCashePojoList = dcsHistoryCasheRep.findByVTimeBetween(begin,end);
            }else{
                dcsHistoryCashePojoList = dcsHistoryCasheRep.findByVTimeBetween(begin,end);
            }
//            System.out.println("缓存数据长度：" + dcsHistoryCashePojoList.size());
//            for(DcsHistoryCashePojo d:dcsHistoryCashePojoList){
//                dcsHistoryCasheRep.delete(d);
//            }
            List<DcsHistoryCashePojo> handleDcsHistoryCachePojoList = new ArrayList<>();
            dcsHistoryCashePojoList.forEach((pojo)->{
                int pojoId = pojo.getId();
                if(dcsHistoryCacheIdSets.contains(pojoId)){

                }else{
                    dcsHistoryCacheIdSets.add(pojoId);
                    handleDcsHistoryCachePojoList.add(pojo);
                }
            });
//            System.out.println("删除结束");
            for(DcsHistoryCashePojo pojo:handleDcsHistoryCachePojoList){
                int offSet = pojo.getvOffset();
                Timestamp timestamp = pojo.getvTime();
                String v = pojo.getValues();
                String[] strArr = v.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    if (strArr[i].trim().length() > 0) {
                        Integer value = Integer.parseInt(strArr[i].trim());
                        if(null != value) {
//                            System.out.println("count:" + count + ",cacheId:"+d.getId()+",value:" + value +",offset:" + (offSet +i));
                                dcsHistoryService.save(offSet + i, value, timestamp);
                        }
                    }
                }
                dcsHistoryCasheRep.delete(pojo);
                dcsHistoryCacheIdSets.remove(pojo.getId());
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
