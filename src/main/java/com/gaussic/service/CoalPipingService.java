package com.gaussic.service;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.CoalPipingSetEntity;
import com.gaussic.repository.CoalMillPipingSetRepository;
import com.gaussic.repository.CoalMillRepository;
import com.gaussic.repository.CoalPipingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class CoalPipingService {

    @Autowired
    private CoalMillPipingSetRepository coalMillPipingSetRepository;
    @Autowired
    private CoalMillRepository coalMillRepository;
    @Autowired
    private CoalPipingRepository coalPipingRepository;

    /**
     * 查询所有的管道，并且关联pipingSet对象
     * @return
     */
    public List<CoalPipingEntity> findAll(){
        List<CoalPipingEntity> coalPipingEntityList = coalPipingRepository.findAll();
        List<CoalPipingSetEntity> coalPipingSetEntityList = coalMillPipingSetRepository.findAll();
        for(CoalPipingEntity coalPipingEntity:coalPipingEntityList){
            Long coalPipingId = coalPipingEntity.getId();
            for(CoalPipingSetEntity coalPipingSetEntity:coalPipingSetEntityList){
                Long coalPipingSetPipingId = coalPipingSetEntity.getCoalPipingId();
                if(coalPipingId.equals(coalPipingSetPipingId)){
                    coalPipingEntity.setCoalPipingSetEntity(coalPipingSetEntity);
                    break;
                }
            }
        }
        return coalPipingEntityList;
    }
}
