package com.gaussic.dataGet;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.model.history.DcoalPipingHistoryEntity;
import com.gaussic.repository.CoalMillPipingSetRepository;
import com.gaussic.repository.CoalMillRepository;
import com.gaussic.repository.CoalPipingHistoryRepositoryC;
import com.gaussic.repository.CoalPipingRepository;
import com.gaussic.service.CoalMillService;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.CoalPipingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class PipingGetData extends Thread {
    @Resource
    private CoalPipingRepository coalPipingRepository;
    @Resource
    private CoalMillRepository coalMillRepository;
    @Autowired
    private CoalMillPipingSetRepository coalMillPipingSetRepository;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;
    @Autowired
    private CoalPipingService coalPipingService;
    @Autowired
    private CoalMillService coalMillService;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;
    @Autowired
    private PipingGetSingleDataThread pipingGetSingleDataThread;

    @Override
    public void run() { this.getData(); }

    public void getData() {
        List<CoalPipingEntity> coalPipingEntityList = coalPipingService.findAll();
        List<CoalMillEntity> coalMillEntityList = coalMillService.findAll();
//        System.out.println("coalPipingEntityList: " + coalPipingEntityList.size());
        Date now = new Date();
        //TODO 后期进行历史数据优化，例如长时间没有数据，可以对数据进行删除，或记录数据的状态。在查询的时候，不至于某一段时间没有数据
        //生成四条历史记录，每台磨煤机一条历史记录

        for(CoalMillEntity coalMillEntity:coalMillEntityList){
            //生成一条coalPipingHistory数据
            CoalPipingHistory coalPipingHistory = coalPipingHistoryService.generatorHistory(coalMillEntity,now);

            List<CoalPipingEntity> coalPipingEntityList_temp = coalMillEntity.getCoalPipingEntityList();
            for(CoalPipingEntity coalPipingEntity:coalPipingEntityList_temp){
                pipingGetSingleDataThread.setCoalPipingEntity(coalPipingEntity);
                pipingGetSingleDataThread.setNow(now);
                //更新实时数据
                pipingGetSingleDataThread.updateData();
                //更新历史数据，根据piping的location进行判定
                coalPipingHistoryService.updateHistory(coalPipingEntity,coalPipingHistory);
            }
        }

    }
}
