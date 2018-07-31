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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
//    @Autowired
//    private PipingGetSingleDataThread pipingGetSingleDataThread;

    @Override
    public void run() { this.getData(); }

    public void getData() {
        List<CoalMillEntity> coalMillEntityList = null;
        try {
            coalMillEntityList = coalMillService.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        Date now = new Date();
        //TODO 后期进行历史数据优化，例如长时间没有数据，可以对数据进行删除，或记录数据的状态。在查询的时候，不至于某一段时间没有数据
        //生成四条历史记录，每台磨煤机一条历史记录
        try {
            List<CoalPipingEntity> coalPipingEntityListAll = new ArrayList<CoalPipingEntity>();
            final CountDownLatch countDownLatch = new CountDownLatch(16);
            List<CoalPipingHistory> coalPipingHistorieList = new ArrayList<CoalPipingHistory>();
            for (CoalMillEntity coalMillEntity : coalMillEntityList) {
                //生成一条coalPipingHistory数据
                CoalPipingHistory coalPipingHistory = coalPipingHistoryService.generatorHistory(coalMillEntity, now);
                coalPipingHistorieList.add(coalPipingHistory);
                List<CoalPipingEntity> coalPipingEntityList_temp = coalMillEntity.getCoalPipingEntityList();

                for(int m=0;m<coalPipingEntityList_temp.size();m++){
                    CoalPipingEntity coalPipingEntity = coalPipingEntityList_temp.get(m);
                    String url = coalPipingEntity.getCoalPipingSetEntity().getsUrl();
                    final String urlFinal = new String(url);
                    new Thread(() -> {
                        PipingGetSingleDataThread pipingGetSingleDataThread = new PipingGetSingleDataThread();
                        pipingGetSingleDataThread.setCoalPipingEntity(coalPipingEntity);
                        pipingGetSingleDataThread.setNow(now);
                        //采集数据，并更新实时数据【已经更新保存到数据库】
                        pipingGetSingleDataThread.updateData(urlFinal);
                        //更新历史数据，根据piping的location进行判定
                        coalPipingHistoryService.updateHistory(coalPipingEntity,coalPipingHistory);

                        countDownLatch.countDown();
                    }).start();

                }

//                coalPipingHistoryService.updateHistory(coalPipingEntityList_temp, coalPipingHistory);
            }

            //16个线程结束以后执行的代码
            countDownLatch.await();

            //更新 coalPipingEntity,更新coalPipingHistory数据
            for(CoalPipingHistory coalPipingHistory:coalPipingHistorieList){
                coalPipingHistoryService.updateCoalPipingHistory(coalPipingHistory.gethCoalMillId(),coalPipingHistory);
            }
            for(CoalMillEntity coalMillEntity:coalMillEntityList){
                List<CoalPipingEntity> coalPipingEntityList = coalMillEntity.getCoalPipingEntityList();
                for(CoalPipingEntity coalPipingEntity:coalPipingEntityList){
                    if(coalPipingEntity.getId() == 11){
                        System.out.println("2222222coalPipingEntity:" + coalPipingEntity + "             ," +
                                coalPipingEntity.getpVelocity());
                    }
                    coalPipingRepository.saveAndFlush(coalPipingEntity);
                }
            }

//            coalPipingRepository.batchUpate(coalPipingEntityListAll);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
