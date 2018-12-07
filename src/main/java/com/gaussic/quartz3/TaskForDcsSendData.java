package com.gaussic.quartz3;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcsRemote.DcsRemotePointPojo;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.CoalMillRepository;
import com.gaussic.repository.DcsRemotePointRepository;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.MainPageService;
import com.gaussic.service.PipeDataHandleServer;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Transactional
public class TaskForDcsSendData {


    @Autowired
    private DcsRemotePointRepository dcsRemotePointRepository;
    @Autowired
    private CoalMillRepository coalMillRepository;
    @Autowired
    private CoalPipingHistoryService<? extends CoalPipingHistory> coalPipingHistoryService;

    public void updateSendDcsData(){
//        System.out.println("更新DCS数据");
        List<CoalMillEntity> coalMillEntityList = coalMillRepository.findAll();
        Map<Long,List<DcsRemotePointPojo>> dcsMap = new HashMap<>();
        List<DcsRemotePointPojo> dcsRemotePointPojoList = dcsRemotePointRepository.findAll();
        for(DcsRemotePointPojo dcsRemotePointPojo:dcsRemotePointPojoList){
            Long pipeId = dcsRemotePointPojo.getCoalPipingEntity().getId();
            if(null != dcsMap.get(pipeId)){

            }else{
                List<DcsRemotePointPojo> list = new ArrayList<>();
                dcsMap.put(pipeId,list);
            }
            dcsMap.get(pipeId).add(dcsRemotePointPojo);
        }
        LocalDateTime now = LocalDateTime.now();

        Timestamp begin = Timestamp.valueOf(now.minusSeconds(230));
        Timestamp end = Timestamp.valueOf(now.minusSeconds(30));
        Timestamp currentTime = end;
//        System.out.println("coalMillEntityList.size:" + coalMillEntityList.size());
        try {
            for (CoalMillEntity coalMillEntity : coalMillEntityList) {


                List<? extends  CoalPipingHistory> coalPipingHistoryList = coalPipingHistoryService
                        .findMillPipeDataHistoryByMillLocation(coalMillEntity.getId(), begin, end);
//                System.out.println("磨煤机名称："+ coalMillEntity.getcName());
                //将风速与密度封装成float[]数组对象，对应v1，d1等数据，并进行了平滑均值处理
                Map<String, float[]> map = PipeDataHandleServer.getHandlerDensityAndVelocityData(coalPipingHistoryList);
//            Map<String, float[]> map = null;
                float mill = MainPageService.getMillCount(coalMillEntity);
//                System.out.println("mill:" + mill);

                if (null != map) {

//                    System.out.println("map:" + map.keySet().size());

                    float pipe1Density = MainPageService.getLatestValue(map.get("d1"));
                    float pipe2Density = MainPageService.getLatestValue(map.get("d2"));
                    float pipe3Density = MainPageService.getLatestValue(map.get("d3"));
                    float pipe4Density = MainPageService.getLatestValue(map.get("d4"));
                    Float[] densityArr = new Float[]{pipe1Density, pipe2Density, pipe3Density, pipe4Density};
                    float pipe1DensityReal = PipeDataHandleServer.getDencityRealValue(pipe1Density, mill, densityArr);
                    float pipe2DensityReal = PipeDataHandleServer.getDencityRealValue(pipe2Density, mill, densityArr);
                    float pipe3DensityReal = PipeDataHandleServer.getDencityRealValue(pipe3Density, mill, densityArr);
                    float pipe4DensityReal = PipeDataHandleServer.getDencityRealValue(pipe4Density, mill, densityArr);
                    float pipe1Velocity = getVelocity(map.get("v1"));
                    float pipe2Velocity = getVelocity(map.get("v2"));
                    float pipe3Velocity = getVelocity(map.get("v3"));
                    float pipe4Velocity = getVelocity(map.get("v4"));

                    List<CoalPipingEntity> coalPipingEntityList = coalMillEntity.getCoalPipingEntityList();
//                    System.out.println("millid:" + coalMillEntity.getId());
                    if (coalMillEntity.getId() == 1) {
//                        System.out.println("A磨：" );
                        setDcsRemotePojoCurrentValue(dcsMap, 11L, currentTime, pipe1Velocity, pipe1DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 12L, currentTime, pipe2Velocity, pipe2DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 13L, currentTime, pipe3Velocity, pipe3DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 14L, currentTime, pipe4Velocity, pipe4DensityReal);
                    } else if (coalMillEntity.getId() == 2) {
                        setDcsRemotePojoCurrentValue(dcsMap, 21L, currentTime, pipe1Velocity, pipe1DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 22L, currentTime, pipe2Velocity, pipe2DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 23L, currentTime, pipe3Velocity, pipe3DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 24L, currentTime, pipe4Velocity, pipe4DensityReal);
                    } else if (coalMillEntity.getId() == 3) {
                        setDcsRemotePojoCurrentValue(dcsMap, 31L, currentTime, pipe1Velocity, pipe1DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 32L, currentTime, pipe2Velocity, pipe2DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 33L, currentTime, pipe3Velocity, pipe3DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 34L, currentTime, pipe4Velocity, pipe4DensityReal);
                    } else if (coalMillEntity.getId() == 4) {
                        setDcsRemotePojoCurrentValue(dcsMap, 41L, currentTime, pipe1Velocity, pipe1DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 42L, currentTime, pipe2Velocity, pipe2DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 43L, currentTime, pipe3Velocity, pipe3DensityReal);
                        setDcsRemotePojoCurrentValue(dcsMap, 44L, currentTime, pipe4Velocity, pipe4DensityReal);
                    }
                }
//                System.out.println("更一次结束");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setDcsRemotePojoCurrentValue(Map<Long,List<DcsRemotePointPojo>> dcsMap,long pipeId,Timestamp
            currentTime,
                                              float pipeVelocity,float pipeDensity){
        try {
            List<DcsRemotePointPojo> dcsRemotePointPojos = dcsMap.get(pipeId);
            setDcsRemotePojoCurrentValue(dcsRemotePointPojos, currentTime, pipeVelocity, pipeDensity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setDcsRemotePojoCurrentValue(List<DcsRemotePointPojo> dcsRemotePointPojos,Timestamp currentTime,
                                              float pipeVelocity,float pipeDensity){
        try {
            for (DcsRemotePointPojo dcsRemotePointPojo : dcsRemotePointPojos) {
                dcsRemotePointPojo.setCurrTime(currentTime);
                if (dcsRemotePointPojo.getDensityOrVelocity() == 0) {
                    //风速
                    dcsRemotePointPojo.setCurrentValue(pipeVelocity);
                } else {
                    //浓度
                    dcsRemotePointPojo.setCurrentValue(pipeDensity);
                }
//                System.out.println("保存");
                dcsRemotePointRepository.saveAndFlush(dcsRemotePointPojo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private float getVelocity(float[] velocityArr){
        float latestVelocity = MainPageService.getLatestValue(velocityArr);
        float velocityReal = PipeDataHandleServer.getVelocityRealValue(latestVelocity);
        return velocityReal;
    }
}
