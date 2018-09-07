package com.gaussic.dataGet.windPojoHandle;

import com.gaussic.model.AlarmHistoryEntity;
import com.gaussic.model.CoalPipingEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WindPojoToEntityUtil {
    /**
     * 工具类, 解析过渡对象 ,转换为 实际的 entity对象
     * 通过windPojo类获取到CoalPipingEntity的实时数据
     *
     * @param pojo
     */
    public static CoalPipingEntity updateRealData(long timeLong, CoalPipingEntity coalPipingEntity, WindDataPojo pojo) {
        //TODO 数据读取错误，无法设置成功
        //TODO 数据分析功能，当通讯中断或者数据处理异常时，需要更新coalpipingEntity的状态为通讯中断。【通讯中断暂时设计为一次中断即判定为中断】
        //TODO 数据恢复以后，通讯中断的状态需要及时更新。

        coalPipingEntity.setpTime(new Timestamp(timeLong));
        if (null != pojo) {
            if (null != pojo.getDensityXValue()) {
                //TODO 浓度处理，暂时使用X与Y两个值去求平均值
                //TODO 增加告警处理，当X与Y的值偏差大于5%的时候，需要增加一个告警信息
                float dencity = (Float.parseFloat(pojo.getDensityXValue()) + Float.parseFloat(pojo.getDensityYValue())) / 2;
                coalPipingEntity.setpDencity(dencity);
//                System.out.println(pojo);
            } else {
                coalPipingEntity.setpDencity(null);
            }
            //TODO 风速数据，获取到的数据，使用指定的距离除以时间量，就是速度
            if (null != pojo.getSpeedValue()) {
                float distance = 0.05F;
                float speed = distance / Float.parseFloat(pojo.getSpeedValue());
                coalPipingEntity.setpVelocity(speed);
            } else {
                coalPipingEntity.setpVelocity(null);
            }
            if(null == coalPipingEntity.getpCommunicationState()){
                coalPipingEntity.setpCommunicationState(0);
            }
            //TODO 通讯中断处理,更新coalpipingEntity设备状态，新增告警记录
            //TODO 通讯中断处理时，需要增加判断机制，不能因为一次通讯异常就新增通讯异常记录。需要进行判断。
            if (null != pojo.getCommunicationValue()) {
                if(null == coalPipingEntity.getpCommunicationState() || coalPipingEntity.getpCommunicationState() ==
                        0  ||(coalPipingEntity.getpCommunicationState() ==
                        1 && coalPipingEntity.getAlarmHistoryEntity() == null)) {
                    //TODO 当通讯中断状态为1，但是数据库中没有对应的告警记录，仍然需要记录数据到数据库中
                    coalPipingEntity.setpCommunicationState(1);
                    //新增告警记录
                    AlarmHistoryEntity alarmHistoryEntity = new AlarmHistoryEntity();
                    alarmHistoryEntity.setaAlarmTime(coalPipingEntity.getpTime());
                    alarmHistoryEntity.setaAlarmType(AlarmHistoryEntity.ALARM_TYPE_COMMUNICATION_PIPE);

                    String note = pojo.getCommunicationValue();
                    alarmHistoryEntity.setaAlarmNote(note);
                    alarmHistoryEntity.setaAlarmPipeId(coalPipingEntity.getId());
                    alarmHistoryEntity.setaAlarmPipeString(coalPipingEntity.getpName());
                    alarmHistoryEntity.setaAlarmMillId(coalPipingEntity.getpCoalMillId());
                    alarmHistoryEntity.setaAlarmMillName(coalPipingEntity.getCoalMillEntity().getcName());
                    //TODO 通讯故障告警，不需要进行告警确认
                    alarmHistoryEntity.setaConfirmFlag(false);
                    alarmHistoryEntity.setaAlarmState(AlarmHistoryEntity.ALARM_STATE_ERROR);

                    coalPipingEntity.setAlarmHistoryEntity(alarmHistoryEntity);
                }

            } else {
                //TODO 对于部分告警，例如：通讯中断告警，可能设备状态已经改变，但是通讯中断的记录仍存在，需要将通讯中断记录进行解警。
                //TODO 这是多线程访问导致出现的问题，当多线程访问时，额外的产生了多余的通讯中断信息或者其它告警信息【需要进行判定】
                if(coalPipingEntity.getpCommunicationState() !=0 || (coalPipingEntity.getpCommunicationState()==0 &&
                        coalPipingEntity.getAlarmHistoryEntity() != null)){
                    //TODO 寻找到当前告警的信息，进行解警
                    AlarmHistoryEntity alarmHistoryEntity =coalPipingEntity.getAlarmHistoryEntity();
                    if(null != alarmHistoryEntity){
                        if(alarmHistoryEntity.getaAlarmType() == AlarmHistoryEntity.ALARM_TYPE_COMMUNICATION_PIPE){
                            alarmHistoryEntity.setaAlarmState(AlarmHistoryEntity.ALARM_STATE_NORMAL);
                            alarmHistoryEntity.setaReAlarmTime(Timestamp.valueOf(LocalDateTime.now()));
                            alarmHistoryEntity.setaAlarmReAlarmNote("系统自动解警");
                        }
                    }
                }
                coalPipingEntity.setpCommunicationState(0);
                //TODO 将告警记录进行解除
            }
        } else {
            coalPipingEntity.setpDencity(null);
            coalPipingEntity.setpVelocity(null);
        }
        return coalPipingEntity;
    }
}
