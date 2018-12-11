package com.gaussic.dataGet.pipeHandle;

import com.gaussic.dataGet.windPojoHandle.PipingGetSingleDataThread;
import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.repository.AlarmHistoryRepository;
import com.gaussic.repository.CoalPipingRepository;
import com.gaussic.repository.DeviceDcsRepository;
import com.gaussic.service.CoalMillService;
import com.gaussic.service.CoalPipingHistoryService;
import com.gaussic.service.CoalPipingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName PipeHandler
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/18 10:34
 * @Version 1.0
 */
@Component
public class PipeHandler {
    @Resource
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private CoalPipingService coalPipingService;
    @Autowired
    private CoalMillService coalMillService;
    @Autowired
    private CoalPipingHistoryService coalPipingHistoryService;

    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;


    private static  List<CoalMillEntity> coalMillEntityList ;
    /**
     * 粉管设备数据读取处理
     * @param localDateTime
     */
    public void handlePipe(LocalDateTime localDateTime) {
        try {
            //TODO 后期进行历史数据优化，例如长时间没有数据，可以对数据进行删除，或记录数据的状态。在查询的时候，不至于某一段时间没有数据
            //生成四条历史记录，每台磨煤机一条历史记录
//            if(null == coalMillEntityList) {

                coalMillEntityList = coalMillService.findAll();
                for(CoalMillEntity coalMillEntity:coalMillEntityList){
                    List<CoalPipingEntity> list = coalMillEntity.getCoalPipingEntityList();
//                    list.forEach(coalPipingEntity -> coalPipingEntity.);
                    if(null != list) {
                        list.forEach(coalPipingEntity -> {
                            if(null != coalPipingEntity){
                                if(null != coalPipingEntity.getCoalPipingSetEntity()){

                                }
                            }
                        });
                    }
                }
//            }
            //遍历告警信息，将  ”粉管通讯中断”  的告警信息查询出来,并设置到coalPipingEntity中
            //查询当前粉管是否存在通讯中断告警，并且是正在告警中的状态。
            //设置完成后，coalPipingEntity将存在alarmHistoryEntity对象。
            coalPipingService.handleCoalPipingWithAlarmForCommunication(coalMillEntityList);
            Date now = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            List<CoalPipingHistory> coalPipingHistorieList = new ArrayList<>();
            new Thread(() -> {
                //访问远程网络，处理数据，录入数据库，更新数据库
                handlPipeUrlData(coalMillEntityList, coalPipingHistorieList, now);
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * DCS数据，读取处理
     * @param localDateTime
     */
//    /**
//     * @Description  DCS数据，读取数据
//     * @Author jiangyong xia
//     * @Date 10:57 18/9/3
//     * @Param
//     * @return
//     **/
//    private void handleDCS(LocalDateTime localDateTime) {
//        //一、读取DCS的数据
//        //01-查询DCS表
//        //02-根据DCS表，查询此设备的点设备地址
//        //03-根据设备信息，点号，读取数据
//        //04-分析数据，录入实时数据表
//        //05-分析数据，录入历史数据库
//        //二、写数据到DCS
//        //1、读取数据库中的pipe实时风速与密度
//        //2、将数据发送给DCS
//        try {
//            DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1l);
//            List<DevicePointPojo> devicePointPojoList = deviceDcsPojo.getDevicePointPojoList();
//            devicePointPojoList.stream().forEach((point) -> {
//                point.getDevicePointRealtimePojo();
//            });
//            int slaveId = Integer.parseInt(deviceDcsPojo.getDeviceAddress());
//            DevicePointHistory14Pojo devicePointHistory1Pojo = new DevicePointHistory14Pojo(localDateTime);
//            DevicePointHistory11Pojo devicePointHistory2Pojo = new DevicePointHistory11Pojo(localDateTime);
//            DevicePointHistory12Pojo devicePointHistory3Pojo = new DevicePointHistory12Pojo(localDateTime);
//            DevicePointHistory13Pojo devicePointHistory4Pojo = new DevicePointHistory13Pojo(localDateTime);
//            new Thread(() -> {
//                handelDcsData(devicePointPojoList, slaveId, localDateTime, devicePointHistory1Pojo, devicePointHistory2Pojo, devicePointHistory3Pojo, devicePointHistory4Pojo);
//            }).start();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 处理数据采集设备的数据，线程处理
     * @param coalMillEntityList
     * @param coalPipingHistorieList
     * @param now
     */
    private void handlPipeUrlData(List<CoalMillEntity> coalMillEntityList ,List<CoalPipingHistory>
            coalPipingHistorieList,Date now) {
        //TODO 使用16个线程，16根粉管的数据读取结束以后，进行数据的更新，历史数据的存储
//        Instant instant00 = Instant.now();
        final CountDownLatch countDownLatch = new CountDownLatch(16);
        try {
            //01、使用16个线程读取与处理数据，并将处理后的数据更新到实体类中【未保存到数据库】。
            for (CoalMillEntity coalMillEntity : coalMillEntityList) {
                //生成一条coalPipingHistory数据
                CoalPipingHistory coalPipingHistory = coalPipingHistoryService.generatorHistory(coalMillEntity, now);
                coalPipingHistorieList.add(coalPipingHistory);
                List<CoalPipingEntity> coalPipingEntityList_temp = coalMillEntity.getCoalPipingEntityList();
                if(null != coalPipingEntityList_temp) {
                    for (int m = 0; m < coalPipingEntityList_temp.size(); m++) {
                        CoalPipingEntity coalPipingEntity = coalPipingEntityList_temp.get(m);
                        String url = coalPipingEntity.getCoalPipingSetEntity().getsUrl();
                        final String urlFinal = new String(url);
                        new Thread(() -> {
                            try {

                                /*Instant instant10 = Instant.now();*/

                                PipingGetSingleDataThread pipingGetSingleDataThread = new PipingGetSingleDataThread();
                                pipingGetSingleDataThread.setCoalPipingEntity(coalPipingEntity);
                                pipingGetSingleDataThread.setNow(now);
                                //采集数据，并更新实时数据【不进行更新保存到数据库】
                                //TODO 历史数据录入，进行判定告警信息，此线程中，无法调用spring管理的bean
                                //TODO 粉管数据关键处理类
                                /************关键处理类****************/
                                pipingGetSingleDataThread.updateData(urlFinal);
//                            System.out.println("---------------coalPipingEntity:" + coalPipingEntity.getpName() + "," + coalPipingEntity + "," + coalPipingEntity.getpDencity() );


                           /* Instant instant11 = Instant.now();
                            Duration duration1011 = Duration.between(instant10,instant11);
                            System.out.println("采集单根根管，时间：" + duration1011.getSeconds() + "秒,"+duration1011.getNano()
                                    /1000/1000 +"毫秒");*/
                                countDownLatch.countDown();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();

                    }
                }

            }

            //16个线程结束以后执行的代码
            countDownLatch.await();
            //02、将数据更新后的实体类，更新到数据库中。
            //02-01、更新coalPipingHistory数据
//            System.out.println("更新历史数据");


  /*              Instant instant01 = Instant.now();
                Duration duration0001 = Duration.between(instant00,instant01);
                System.out.println("采集16根管，时间：" + duration0001.getSeconds() + "秒,"+duration0001.getNano()/1000/1000 +"毫秒");
*/

            // 更新历史数据的ABCD的浓度与风速
            coalPipingHistoryService.updateCoalPipintHistoryData(coalPipingHistorieList,coalMillEntityList);

/*                Instant instant02 = Instant.now();
                Duration duration0002 = Duration.between(instant00,instant02);
                System.out.println("保存粉管数据，时间：" + duration0002.getSeconds() + "秒,"+duration0002.getNano()/1000/1000 +"毫秒");*/

            //历史数据更新到数据库
            for (CoalPipingHistory coalPipingHistory : coalPipingHistorieList) {
                coalPipingHistoryService.updateCoalPipingHistory(coalPipingHistory.gethCoalMillId(), coalPipingHistory);
            }
            //02-02、更新coalpipingEntity，粉管的浓度、密度、运行状态、告警状态
            //02-03、更新alarmHistoryEntity，告警信息
            for (CoalMillEntity coalMillEntity : coalMillEntityList) {
                List<CoalPipingEntity> coalPipingEntityList = coalMillEntity.getCoalPipingEntityList();
                for (CoalPipingEntity coalPipingEntity : coalPipingEntityList) {
//                    System.out.println("保存数据" + coalPipingEntity.getpName() + "," + coalPipingEntity.getpDencity() +
//                            "," + coalPipingEntity.getpVelocity());
                    coalPipingRepository.saveAndFlush(coalPipingEntity);
                    //告警信息的录入
                    if(null != coalPipingEntity){
                        if(null != coalPipingEntity.getAlarmHistoryEntity()){
                            alarmHistoryRepository.saveAndFlush(coalPipingEntity.getAlarmHistoryEntity());
                        }
                    }
                }
            }

/*            Instant instant03 = Instant.now();
            Duration duration0003 = Duration.between(instant00,instant03);
            System.out.println("更新粉管实时数据，时间：" + duration0002.getSeconds() + "秒,"+duration0002.getNano()/1000/1000
                    +"毫秒");*/


//            coalPipingRepository.batchUpate(coalPipingEntityListAll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
