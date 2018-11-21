package com.gaussic.quartz3;

import com.gaussic.model.AlarmHistoryEntity;
import com.gaussic.model.history.*;
import com.gaussic.repository.*;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.dcs.DcsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Component
@Transactional
public class CheckDeviceAlarmQuartz {




    //TODO 进行告警信息生产的开关
    public static final boolean CHECK_ALARM_FUN = false;






    @Autowired
    private DcsHistoryService dcsHistoryService;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;
    @Autowired
    private CoalPipingHistoryRepositoryA coalPipingHistoryRepositoryA;
    @Autowired
    private CoalPipingHistoryRepositoryB coalPipingHistoryRepositoryB;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;
    @Autowired
    private CoalPipingHistoryRepositoryD coalPipingHistoryRepositoryD;
    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;


    //进行计算的最少数据量
    public static final int calculateMinCount = 40;
    //XY两个环之间的偏差，大于100%;传感器或采集器存在问题
    public static final double XYOffsetAlarmLevel0Value = 1;
    //XY两个环之间的偏差，最大为20%;【等级一】
    public static final double XYOffsetAlarmLevel1Value = 0.2;
    //XY两个环之间的偏差，最大为17%;【等级二】
    public static final double XYOffsetAlarmLevel2Value = 0.17;
    //XY两个环之间的偏差，最大为12%;【等级三】
    public static final double XYOffsetAlarmLevel3Value = 0.12;
    //XY两个环，超过此限值则告警，证明某一个环存在问题
    public static final double XYMaxValue = 200000000;
    //TODO 速度判定未做

    //如果磨煤机处于运行状态，两个环的数据平均值小于20万，则环存在问题。
    final double XYMinValue = 200000;

    /**
     * 0、当1分钟内的数据不足40个点时，不进行计算
     * 1、检查每根粉管的X与Y的值进行判断，1分钟内的X与Y的偏差【峰峰偏差20%】,超过20%,设备为报警信号
     * 2、当X与Y的偏差的数量不是一个数量级的时候，报警为某一个环坏掉
     * 3、当磨煤机处于运行状态的时候，X与Y的数据低于20万，则判定环坏掉【谨慎判断】
     * 4、当环的速度出现大于30的时候，判定为速度出现问题【30的时候，速度约为16米每秒】
     * <p>
     * 5、当4个环的数据进行比较的时候，最大环的均值与最小环的均值偏差超过30%为三级告警，超过40%进行二级告警，超过50%，一级告警，
     */
    public void checkDeviceAlarm() {
        //TODO 是否进行告警信息查询与录入数据库，11月13日进行此操作，有问题，暂时搁置
        if(CHECK_ALARM_FUN ) {
//        System.out.println("检查告警信息");

            LocalDateTime end = LocalDateTime.now();
            LocalDateTime begin = end.minusSeconds(60);
            Timestamp queryEndTime = Timestamp.valueOf(end);
            Timestamp queryBeginTime = Timestamp.valueOf(begin);

            List<AcoalPipingHistoryEntity> acoalPipingHistoryEntityList = coalPipingHistoryRepositoryA.findByHTimeBetween(queryBeginTime, queryEndTime);
            List<BcoalPipingHistoryEntity> bcoalPipingHistoryEntityList = coalPipingHistoryRepositoryB.findByHTimeBetween
                    (queryBeginTime, queryEndTime);
            List<CcoalPipingHistoryEntity> ccoalPipingHistoryEntityList = coalPipingHistoryRepositoryC.findByHTimeBetween
                    (queryBeginTime, queryEndTime);
            List<DcoalPipingHistoryEntity> dcoalPipingHistoryEntityList = coalPipingHistoryRepositoryD.findByHTimeBetween
                    (queryBeginTime, queryEndTime);
            long coalMillIdForA = 1;
            generatorMillAllPipeAlarm(acoalPipingHistoryEntityList, queryBeginTime, coalMillIdForA);
            long coalMillIdForB = 2;
            generatorMillAllPipeAlarm(bcoalPipingHistoryEntityList, queryBeginTime, coalMillIdForB);
            long coalMillIdForC = 3;
            generatorMillAllPipeAlarm(ccoalPipingHistoryEntityList, queryBeginTime, coalMillIdForC);
            long coalMillIdForD = 4;
            generatorMillAllPipeAlarm(dcoalPipingHistoryEntityList, queryBeginTime, coalMillIdForD);
        }
    }

    /**
     * 生成磨煤机的告警信息
     *
     * @param list
     * @param queryBeginTime
     * @param coalMillId
     */
    private void generatorMillAllPipeAlarm(List<? extends CoalPipingHistory> list, Timestamp queryBeginTime, long coalMillId) {
//        System.out.println("磨煤机：" + coalMillId + ":数量" + list.size());
        try {
            if (null != list && list.size() > calculateMinCount) {
                Stream<? extends CoalPipingHistory> stream = list.stream();

                long pipeIdForA = coalMillId * 10 + 1;
                DoubleStream xStreamForA = list.stream().mapToDouble(CoalPipingHistory::gethPipeAX);
                DoubleStream yStreamForA = list.stream().mapToDouble(CoalPipingHistory::gethPipeAY);
                generatorPipeAlarm(xStreamForA, yStreamForA, queryBeginTime, coalMillId, pipeIdForA);

                long pipeIdForB = coalMillId * 10 + 2;
                DoubleStream xStreamForB = list.stream().mapToDouble(CoalPipingHistory::gethPipeBX);
                DoubleStream yStreamForB = list.stream().mapToDouble(CoalPipingHistory::gethPipeBY);
                generatorPipeAlarm(xStreamForB, yStreamForB, queryBeginTime, coalMillId, pipeIdForB);

                long pipeIdForC = coalMillId * 10 + 3;
                DoubleStream xStreamForC = list.stream().mapToDouble(CoalPipingHistory::gethPipeCX);
                DoubleStream yStreamForC = list.stream().mapToDouble(CoalPipingHistory::gethPipeCY);
                generatorPipeAlarm(xStreamForC, yStreamForC, queryBeginTime, coalMillId, pipeIdForC);

                long pipeIdForD = coalMillId * 10 + 4;
                DoubleStream xStreamForD = list.stream().mapToDouble(CoalPipingHistory::gethPipeDX);
                DoubleStream yStreamForD = list.stream().mapToDouble(CoalPipingHistory::gethPipeDY);
                generatorPipeAlarm(xStreamForD, yStreamForD, queryBeginTime, coalMillId, pipeIdForD);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 生成一个粉管的告警信息
     *
     * @param xStream
     * @param yStream
     * @param queryBeginTime
     * @param coalMillId
     * @param pipeId
     */
    private void generatorPipeAlarm(DoubleStream xStream, DoubleStream yStream, Timestamp queryBeginTime, long
            coalMillId, long pipeId) {
        if(null != xStream && null != yStream) {
//        List<Double> xList = xStream.collect(Collectors.toList());
            double avgForAX = xStream.average().orElse(0d);
            double avgForAY = yStream.average().orElse(0d);
//        double maxForAX = xStream.max().orElse(0);
//        double maxForAY = yStream.max().orElse(0);
//        double minForAX = xStream.min().orElse(0);
//        double minForAy = yStream.min().orElse(0);
            //XY之间的偏差告警
            generatorOffSetAlarmHistoryEntity(avgForAX, avgForAY, queryBeginTime, coalMillId, pipeId);
            if (avgForAX > XYMaxValue) {
                //X环数据超标，环坏掉
            }

            if (avgForAY > XYMaxValue) {
                //y环数据超标，环坏掉

            }
        }
    }

    /**
     * 生成偏差告警
     *
     * @param avgForAX
     * @param avgForAY
     * @param queryBeginTime
     * @return
     */
    private AlarmHistoryEntity generatorOffSetAlarmHistoryEntity(double avgForAX, double avgForAY, Timestamp
            queryBeginTime, long coalMillId, long pipeId) {
        double avgForA = (avgForAX + avgForAY) / 2;
        double offset = Math.abs((avgForAX - avgForAY) / avgForA);
//        System.out.println("粉管：" + pipeId + ",偏差" + offset);
        AlarmHistoryEntity alarmHistoryEntity = null;
        String alarmNote = null;
        if (offset >= XYOffsetAlarmLevel1Value && offset < XYOffsetAlarmLevel0Value) {
            //如果两个XY偏差超过20%，并且小于1，则报警为一级报警
            alarmNote = "一级告警";
        } else if (offset >= XYOffsetAlarmLevel2Value) {
            //如果大于二级告警值，进行二级告警
            alarmNote = "二级告警";
        } else if (offset > XYOffsetAlarmLevel3Value) {
            //如果大于三级告警值，进行三级告警
            alarmNote = "二级告警";
        }
        if (null != alarmNote) {

            alarmHistoryEntity = generatorAlarmHistoyEntity(queryBeginTime, offset, coalMillId, pipeId, alarmNote);
            alarmHistoryRepository.saveAndFlush(alarmHistoryEntity);
        }
        return alarmHistoryEntity;
    }


    /**
     * 生成历史告警对象
     *
     * @param queryEndTime
     * @param offset
     * @param coalMillId
     * @param pipeId
     * @param alarmNote
     * @return
     */
    private AlarmHistoryEntity generatorAlarmHistoyEntity(Timestamp queryEndTime, double offset, long coalMillId, long
            pipeId, String alarmNote) {
        AlarmHistoryEntity alarmHistoryEntity = new AlarmHistoryEntity();
        alarmHistoryEntity.setaAlarmTime(queryEndTime);
        //设置磨煤机的编号

        alarmHistoryEntity.setaAlarmMillId(coalMillId);
        //设置磨煤机的名称
        //设置粉管的编号：11

        alarmHistoryEntity.setaAlarmPipeId(pipeId);
        //设置粉管的名称：
        //设置设备的类型

        //设置告警的类型
        //设置告警的状态为1
        //设置解警的状态为1

        alarmHistoryEntity.setaConfirmFlag(false);
        alarmHistoryEntity.setaAlarmNote("粉管编号:" + pipeId + ", " + alarmNote + "，" + "数据偏差:" +
                ((int)(offset*10000))/100f + "%");
        return alarmHistoryEntity;
    }
}
