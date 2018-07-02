package com.gaussic.service;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.history.*;
import com.gaussic.repository.CoalPipingHistoryRepositoryA;
import com.gaussic.repository.CoalPipingHistoryRepositoryB;
import com.gaussic.repository.CoalPipingHistoryRepositoryC;
import com.gaussic.repository.CoalPipingHistoryRepositoryD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CoalPipingHistoryService {
    @Autowired
    private CoalPipingHistoryRepositoryA coalPipingHistoryRepositoryA;
    @Autowired
    private CoalPipingHistoryRepositoryB coalPipingHistoryRepositoryB;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;
    @Autowired
    private CoalPipingHistoryRepositoryD coalPipingHistoryRepositoryD;


    /**
     * 生成一个 history对象
     * @param coalMillEntity
     * @param now
     * @return
     */
    public CoalPipingHistory generatorHistory(CoalMillEntity coalMillEntity, Date now) {
        Long coalMillId = coalMillEntity.getId();
        CoalPipingHistory coalPipingHistory = null;
        switch(coalMillId.intValue()){
            case 1:
                coalPipingHistory = new AcoalPipingHistoryEntity(coalMillEntity,now);
                coalPipingHistoryRepositoryA.save((AcoalPipingHistoryEntity) coalPipingHistory);
                break;
            case 2:
                coalPipingHistory = new BcoalPipingHistoryEntity(coalMillEntity,now);
                coalPipingHistoryRepositoryB.save((BcoalPipingHistoryEntity) coalPipingHistory);
                    break;
            case 3:
                coalPipingHistory = new CcoalPipingHistoryEntity(coalMillEntity,now);
                coalPipingHistoryRepositoryC.save((CcoalPipingHistoryEntity) coalPipingHistory);
                break;
            case 4:
                coalPipingHistory = new DcoalPipingHistoryEntity(coalMillEntity,now);
                coalPipingHistoryRepositoryD.save((DcoalPipingHistoryEntity) coalPipingHistory);
                break;
            default:break;
        }
        return coalPipingHistory;
    }

    /**
     * 根据coalPiping的实时数据，更新历史数据
     * @param coalPipingEntity
     * @param coalPipingHistoryEntity
     */
    public void updateHistory(CoalPipingEntity coalPipingEntity, CoalPipingHistory coalPipingHistoryEntity) {
        String pipingLocation = coalPipingEntity.getpLocation();
        Float velocity = coalPipingEntity.getpVelocity();
        Float density = coalPipingEntity.getpDencity();
        switch (pipingLocation ){
            case "A":
                coalPipingHistoryEntity.sethPipeADencity(density);
                coalPipingHistoryEntity.sethPipeAVelocity(velocity);
                break;
            case "B":
                coalPipingHistoryEntity.sethPipeBDencity(density);
                coalPipingHistoryEntity.sethPipeBVelocity(velocity);
                break;
            case "C":
                coalPipingHistoryEntity.sethPipeCDencity(density);
                coalPipingHistoryEntity.sethPipeCVelocity(velocity);
                break;
            case "D":
                coalPipingHistoryEntity.sethPipeDDencity(density);
                coalPipingHistoryEntity.sethPipeDVelocity(velocity);
                break;
            default:
                    break;
        }
        Long coalMillId = coalPipingEntity.getpCoalMillId();
        switch(coalMillId.intValue()){
            case 1:
                coalPipingHistoryRepositoryA.saveAndFlush((AcoalPipingHistoryEntity) coalPipingHistoryEntity);
                break;
            case 2:
                coalPipingHistoryRepositoryB.saveAndFlush((BcoalPipingHistoryEntity) coalPipingHistoryEntity);
                break;
            case 3:
                coalPipingHistoryRepositoryC.saveAndFlush((CcoalPipingHistoryEntity) coalPipingHistoryEntity);
                break;
            case 4:
                coalPipingHistoryRepositoryD.saveAndFlush((DcoalPipingHistoryEntity) coalPipingHistoryEntity);
                break;
            default:break;
        }

    }


}
