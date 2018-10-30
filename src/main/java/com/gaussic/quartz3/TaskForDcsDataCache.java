package com.gaussic.quartz3;

import com.gaussic.model.dcs_history.DcsHistoryCashePojo;
import com.gaussic.repository.dcs_history.DcsHistoryCasheRep;
import com.gaussic.service.dcs.DcsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Component
@Transactional
public class TaskForDcsDataCache {

    @Autowired
    private DcsHistoryCasheRep dcsHistoryCasheRep;
    @Autowired
    private DcsHistoryService dcsHistoryService;

    /**
     * 此方法为定时器方法
     * 主要功能：读取dcsHisotryoCache中的数据，将数据分析后保存早H000至H199中，并清除缓存的数据
     */
    public void doJob() {

        try {
//            System.out.println("执行DCS缓存操作" +count);
            List<DcsHistoryCashePojo> dcsHistoryCashePojoList = dcsHistoryCasheRep.findAll();
//            System.out.println("缓存数据长度：" + dcsHistoryCashePojoList.size());
            for(DcsHistoryCashePojo d:dcsHistoryCashePojoList){
                dcsHistoryCasheRep.delete(d);
            }
//            System.out.println("删除结束");
            for(DcsHistoryCashePojo d:dcsHistoryCashePojoList){
                int offSet = d.getvOffset();
                Timestamp timestamp = d.getvTime();
                String v = d.getValues();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
