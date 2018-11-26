package com.java8;

import com.gaussic.model.AlarmHistoryEntity;
import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @ClassName Test1
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/11/26 12:38
 * @Version 1.0
 */
public class Test1 {
    public static void main(String[] args) {
        List<CoalMillEntity> coalMillEntityList = new ArrayList<CoalMillEntity>();
        for(int i=0;i<10;i++) {
            CoalMillEntity coalMillEntity = new CoalMillEntity();
            coalMillEntity.setId(i+1);
            List<CoalPipingEntity> coalPipingEntityList = new ArrayList<>();
            for(int m=0;m<4;m++){
                CoalPipingEntity coalPipingEntity = new CoalPipingEntity();
                coalPipingEntity.setId((i+1)*10+m+1);
                coalPipingEntityList.add(coalPipingEntity);
            }
            coalMillEntity.setCoalPipingEntityList(coalPipingEntityList);
            coalMillEntityList.add(coalMillEntity);
        }






        List<AlarmHistoryEntity> alarmHistoryEntityList = new ArrayList<>();
        for(int i=0;i<110;i++){
            AlarmHistoryEntity alarmHistoryEntity = new AlarmHistoryEntity();
            alarmHistoryEntity.setaAlarmPipeId((long) i);
            alarmHistoryEntity.setId(i);
            alarmHistoryEntityList.add(alarmHistoryEntity);
        }


//        AlarmHistoryEntity alarmHistoryEntity =
//        Predicate<AlarmHistoryEntity> predicate = (alarmHistoryEntity)->{};
        coalMillEntityList.stream().
                flatMap(coalMillEntity -> coalMillEntity.getCoalPipingEntityList().stream()).forEach(coalPipingEntity ->
                    coalPipingEntity.setAlarmHistoryEntity(
                            alarmHistoryEntityList.stream().
                                    filter(alarmHistoryEntity -> alarmHistoryEntity.getaAlarmPipeId().equals(coalPipingEntity.getId())).
                                        findFirst().orElse(null)));

        coalMillEntityList.stream().flatMap(coalMillEntity -> coalMillEntity.getCoalPipingEntityList().stream())
                .forEach(coalPipingEntity -> System.out.println(coalPipingEntity.getAlarmHistoryEntity()));
    }
}
