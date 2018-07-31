package com.gaussic.service;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.history.*;
import com.gaussic.repository.CoalPipingHistoryRepositoryA;
import com.gaussic.repository.CoalPipingHistoryRepositoryB;
import com.gaussic.repository.CoalPipingHistoryRepositoryC;
import com.gaussic.repository.CoalPipingHistoryRepositoryD;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CoalPipingHistoryService<T extends CoalPipingHistory> {
    @Autowired
    private CoalPipingHistoryRepositoryA coalPipingHistoryRepositoryA;
    @Autowired
    private CoalPipingHistoryRepositoryB coalPipingHistoryRepositoryB;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;
    @Autowired
    private CoalPipingHistoryRepositoryD coalPipingHistoryRepositoryD;


//    public String generateJsonStringByHistroyList(List<AcoalPipingHistoryEntity> coalPipeHistoryEntityList, Calendar beginC,
//                                                  Calendar endC){
//        List<CoP>
//    }

    public String generateJsonStringByHistroyList(List<T > coalPipeHistoryEntityList,
                                                  Calendar beginC,
                                                  Calendar endC){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArrayForPipe1Density = new JSONArray();
        JSONArray jsonArrayForPipe2Density = new JSONArray();
        JSONArray jsonArrayForPipe3Density = new JSONArray();
        JSONArray jsonArrayForPipe4Density = new JSONArray();
        JSONArray jsonArrayForPipe1Velocity = new JSONArray();
        JSONArray jsonArrayForPipe2Velocity = new JSONArray();
        JSONArray jsonArrayForPipe3Velocity = new JSONArray();
        JSONArray jsonArrayForPipe4Velocity = new JSONArray();
        if(null != coalPipeHistoryEntityList) {
            for (CoalPipingHistory h : coalPipeHistoryEntityList) {
                jsonArrayForPipe1Density.put(h.gethPipeADencity());
                jsonArrayForPipe2Density.put(h.gethPipeBDencity());
                jsonArrayForPipe3Density.put(h.gethPipeCDencity());
                jsonArrayForPipe4Density.put(h.gethPipeDDencity());

                jsonArrayForPipe1Velocity.put(h.gethPipeAVelocity());
                jsonArrayForPipe2Velocity.put(h.gethPipeBVelocity());
                jsonArrayForPipe3Velocity.put(h.gethPipeCVelocity());
                jsonArrayForPipe4Velocity.put(h.gethPipeDVelocity());
            }
        }

        jsonObject.put("startTime", beginC.getTimeInMillis());
        jsonObject.put("endTime", endC.getTimeInMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        jsonObject.put("startTimeStr", simpleDateFormat.format(beginC.getTime()));
        jsonObject.put("endTimeStr", simpleDateFormat.format(endC.getTime()));

        jsonObject.put("pipe1Density", jsonArrayForPipe1Density);
        jsonObject.put("pipe2Density", jsonArrayForPipe2Density);
        jsonObject.put("pipe3Density", jsonArrayForPipe3Density);
        jsonObject.put("pipe4Density", jsonArrayForPipe4Density);

        jsonObject.put("pipe1Velocity", jsonArrayForPipe1Velocity);
        jsonObject.put("pipe2Velocity", jsonArrayForPipe2Velocity);
        jsonObject.put("pipe3Velocity", jsonArrayForPipe3Velocity);
        jsonObject.put("pipe4Velocity", jsonArrayForPipe4Velocity);



        return jsonObject.toString();
    }

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

    private CoalPipingHistory setCoalPipingHistory(CoalPipingEntity coalPipingEntity,CoalPipingHistory coalPipingHistoryEntity){
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


        return coalPipingHistoryEntity;
    }

    /**
     * 更新历史数据
     * @param coalMillId
     * @param coalPipingHistoryEntity
     */
    public void updateCoalPipingHistory(Long coalMillId,CoalPipingHistory coalPipingHistoryEntity){

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

    /**
     * 更新历史数据
     * @param coalPipingEntityList  所有的对象归属于一个Mill
     * @param coalPipingHistoryEntity
     */
    public void updateHistory(List<CoalPipingEntity> coalPipingEntityList, CoalPipingHistory coalPipingHistoryEntity) {
        for(CoalPipingEntity  coalPipingEntity:coalPipingEntityList){
            setCoalPipingHistory(coalPipingEntity,coalPipingHistoryEntity);
        }
        updateCoalPipingHistory(coalPipingEntityList.get(0).getpCoalMillId(),coalPipingHistoryEntity);
    }
    /**
     * 根据coalPiping的实时数据，更新历史数据
     * @param coalPipingEntity
     * @param coalPipingHistoryEntity
     */
    public void updateHistory(CoalPipingEntity coalPipingEntity, CoalPipingHistory coalPipingHistoryEntity) {
        //设置历史数据ABCD四根管的密度与风速
        setCoalPipingHistory(coalPipingEntity,coalPipingHistoryEntity);
        Long coalMillId= coalPipingEntity.getpCoalMillId();
        //保存粉管历史数据
//        updateCoalPipingHistory(coalMillId,coalPipingHistoryEntity);


    }


}
