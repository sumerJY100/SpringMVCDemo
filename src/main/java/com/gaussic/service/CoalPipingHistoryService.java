package com.gaussic.service;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.CoalPipingSetEntity;
import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.*;
import com.gaussic.repository.*;
import com.gaussic.service.dcs.DcsHistoryService;
import com.gaussic.util.DataFilter;
import com.gaussic.util.DataHandleUtil.PipeHandlerUtil;
import com.gaussic.util.DateUtil;
import com.gaussic.util.HandlDcsHistoryListUtil;
import com.serotonin.modbus4j.sero.timer.TimeSource;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private DcsHistoryService dcsHistoryService;
    @Autowired
    private CoalMillPipingSetRepository coalMillPipingSetRepository;
    @Autowired
    private CoalPipingRepository coalPipingRepository;


    //    public String generateJsonStringByHistroyList(List<AcoalPipingHistoryEntity> coalPipeHistoryEntityList, Calendar beginC,
//                                                  Calendar endC){
//        List<CoP>
//    }

    /**
     * @param coalPipeHistoryEntityList
     * @param beginT
     * @param endT
     * @param original                  true为原始值，false为平滑处理值
     * @return
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList,
                                                  Timestamp beginT,
                                                  Timestamp endT, boolean original) {
        Calendar beginC = new GregorianCalendar();
        beginC.setTimeInMillis(beginT.getTime());
        Calendar endC = new GregorianCalendar();
        endC.setTimeInMillis(endT.getTime());
        return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC, original);

    }

    /**
     * @param coalPipeHistoryEntityList
     * @param beginT
     * @param endT
     * @param pipeId
     * @return
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList,
                                                  Timestamp beginT,
                                                  Timestamp endT, Long pipeId) {
        Calendar beginC = new GregorianCalendar();
        beginC.setTimeInMillis(beginT.getTime());
        Calendar endC = new GregorianCalendar();
        endC.setTimeInMillis(endT.getTime());
        return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC, pipeId);

    }

    /**
     * @param coalPipeHistoryEntityList List<T> coalPipeHistoryEntityList
     * @param beginT                    开始时间
     * @param endT                      结束时间
     * @param hList                     磨煤机磨煤量集合    List<H000Pojo_Base>
     * @param original                  true为原始值，false为平滑处理值
     * @return jsonString
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList, Timestamp beginT, Timestamp
            endT, List<H000Pojo_Base> hList, boolean original) {

        if (null == hList) {
            return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginT, endT, original);
        } else {
            Calendar beginC = new GregorianCalendar();
            beginC.setTimeInMillis(beginT.getTime());
            Calendar endC = new GregorianCalendar();
            endC.setTimeInMillis(endT.getTime());
            return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC, hList, original);
        }


    }

    /**
     * @param coalPipeHistoryEntityList
     * @param beginT
     * @param endT
     * @param hList
     * @param pipeId
     * @return
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList, Timestamp beginT, Timestamp
            endT, List<H000Pojo_Base> hList, Long pipeId) {
        if (null == hList) {
            return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginT, endT, pipeId);
        } else {
            Calendar beginC = new GregorianCalendar();
            beginC.setTimeInMillis(beginT.getTime());
            Calendar endC = new GregorianCalendar();
            endC.setTimeInMillis(endT.getTime());
            return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC, hList, pipeId);
        }
    }

    /**
     * @param coalPipeHistoryEntityList
     * @param beginT
     * @param endT
     * @param hList
     * @param pipeId
     * @return
     */
    public String generateJsonStringByHistroyListToXYVAndMill(List<T> coalPipeHistoryEntityList, Timestamp beginT, Timestamp
            endT, List<H000Pojo_Base> hList, Long pipeId) {
        if (null == hList) {
            return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginT, endT, pipeId);
        } else {
            Calendar beginC = new GregorianCalendar();
            beginC.setTimeInMillis(beginT.getTime());
            Calendar endC = new GregorianCalendar();
            endC.setTimeInMillis(endT.getTime());
            return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC, hList, pipeId);
        }


    }

    /**
     * @param coalPipeHistoryEntityList
     * @param beginC
     * @param endC
     * @param hList
     * @param original                  true为原始值，false为平滑处理值
     * @return
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList,
                                                  Calendar beginC,
                                                  Calendar endC, List<H000Pojo_Base> hList, boolean original) {
        //TODO 将磨煤机的磨煤量设置到coalpipingHistory中
        HandlDcsHistoryListUtil.getMilHistoryListNoChange(coalPipeHistoryEntityList, hList);

        return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC, original);

    }

    /**
     * @param coalPipeHistoryEntityList
     * @param beginC
     * @param endC
     * @param hList
     * @param pipeId                    true为原始值，false为平滑处理值
     * @return
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList,
                                                  Calendar beginC,
                                                  Calendar endC, List<H000Pojo_Base> hList, Long pipeId) {
        //TODO 将磨煤机的磨煤量设置到coalpipingHistory中
        HandlDcsHistoryListUtil.getMilHistoryListNoChange(coalPipeHistoryEntityList, hList);

        return generateJsonStringByHistroyList(coalPipeHistoryEntityList, beginC, endC, pipeId);

    }

    /**
     * 处理一台磨的历史数据，返回jsonString
     *
     * @param coalPipeHistoryEntityList
     * @param beginC
     * @param endC
     * @param original                  true为原始数据，false为平滑数据
     * @return
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList,
                                                  Calendar beginC,
                                                  Calendar endC, boolean original) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArrayForPipe1Density = new JSONArray();
            JSONArray jsonArrayForPipe2Density = new JSONArray();
            JSONArray jsonArrayForPipe3Density = new JSONArray();
            JSONArray jsonArrayForPipe4Density = new JSONArray();
            JSONArray jsonArrayForPipe1Velocity = new JSONArray();
            JSONArray jsonArrayForPipe2Velocity = new JSONArray();
            JSONArray jsonArrayForPipe3Velocity = new JSONArray();
            JSONArray jsonArrayForPipe4Velocity = new JSONArray();
            JSONArray jsonArrayForCoalMill = new JSONArray();
            if (null != coalPipeHistoryEntityList) {

                //将每个粉管的浓度与风速，封装成float对象
                Map<String, float[]> map = PipeDataHandleServer.getHandlerDensityAndVelocityData
                        (coalPipeHistoryEntityList);
                if (null != map) {
                    float[] d1 = map.get("d1"), d2 = map.get("d2"), d3 = map.get("d3"), d4 = map.get("d4");
                    float[] v1 = map.get("v1"), v2 = map.get("v2"), v3 = map.get("v3"), v4 = map.get("v4");
                    for (int i = 0; i < d1.length; i++) {


                        Float mill = coalPipeHistoryEntityList.get(i).getCoalMillValue();
                        if (null == mill)
                            mill = 100f;
//                        mill = mill/100;
                        Float[] densityArr = new Float[]{d1[i], d2[i], d3[i], d4[i]};
                        Float desity1Real = 0f;
                        Float desity2Real = 0f;
                        Float desity3Real = 0f;
                        Float desity4Real = 0f;

                        Float velocity1Real = 0f;
                        Float velocity2Real = 0f;
                        Float velocity3Real = 0f;
                        Float velocity4Real = 0f;
                        if (original) {
                            desity1Real = d1[i];
                            desity2Real = d2[i];
                            desity3Real = d3[i];
                            desity4Real = d4[i];

                            velocity1Real = v1[i];
                            velocity2Real = v2[i];
                            velocity3Real = v3[i];
                            velocity4Real = v4[i];
                        } else {
                            desity1Real = PipeDataHandleServer.getDencityRealValue(d1[i], mill, densityArr);
                            desity2Real = PipeDataHandleServer.getDencityRealValue(d2[i], mill, densityArr);
                            desity3Real = PipeDataHandleServer.getDencityRealValue(d3[i], mill, densityArr);
                            desity4Real = PipeDataHandleServer.getDencityRealValue(d4[i], mill, densityArr);


                            velocity1Real = PipeDataHandleServer.getVelocityRealValue(v1[i]);
                            velocity2Real = PipeDataHandleServer.getVelocityRealValue(v2[i]);
                            velocity3Real = PipeDataHandleServer.getVelocityRealValue(v3[i]);
                            velocity4Real = PipeDataHandleServer.getVelocityRealValue(v4[i]);
                        }
                        jsonArrayForPipe1Velocity.put(velocity1Real);
                        jsonArrayForPipe2Velocity.put(velocity2Real);
                        jsonArrayForPipe3Velocity.put(velocity3Real);
                        jsonArrayForPipe4Velocity.put(velocity4Real);

                        jsonArrayForPipe1Density.put(desity1Real);
                        jsonArrayForPipe2Density.put(desity2Real);
                        jsonArrayForPipe3Density.put(desity3Real);
                        jsonArrayForPipe4Density.put(desity4Real);
                    }
                }

                int dif = coalPipeHistoryEntityList.size() - jsonArrayForPipe1Density.length();
                for (int i = dif; i < coalPipeHistoryEntityList.size(); i++) {
                    Float mill = coalPipeHistoryEntityList.get(i).getCoalMillValue();
                    if (null == mill)
                        mill = 100f;
                    mill = mill / 100;
                    jsonArrayForCoalMill.put(mill);
                }
//                for (CoalPipingHistory h : coalPipeHistoryEntityList) {
//                    jsonArrayForCoalMill.put(h.getCoalMillValue());
//                }
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

            jsonObject.put("coalMillDatas", jsonArrayForCoalMill);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 处理一台磨的历史数据，返回jsonString,返回一根粉管的XY数据，V数据，还有磨煤机数据【原始数据】
     *
     * @param coalPipeHistoryEntityList
     * @param beginC
     * @param endC
     * @param pipeId
     * @return
     */
    public String generateJsonStringByHistroyList(List<T> coalPipeHistoryEntityList,
                                                  Calendar beginC,
                                                  Calendar endC, Long pipeId) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArrayForX = new JSONArray();
            JSONArray jsonArrayForY = new JSONArray();
            JSONArray jsonArrayForV = new JSONArray();
            JSONArray jsonArrayForCoalMill = new JSONArray();
            if (null != coalPipeHistoryEntityList) {
                for (CoalPipingHistory c : coalPipeHistoryEntityList) {
                    Float x = 0f, y = 0f, v = 0f;
                    if (pipeId % 10 == 1) {
                        x = c.gethPipeAX();
                        y = c.gethPipeAY();
                        v = c.gethPipeAV();
                    } else if (pipeId % 10 == 2) {
                        x = c.gethPipeBX();
                        y = c.gethPipeBY();
                        v = c.gethPipeBV();
                    } else if (pipeId % 10 == 3) {
                        x = c.gethPipeCX();
                        y = c.gethPipeCY();
                        v = c.gethPipeCV();
                    } else if (pipeId % 10 == 4) {
                        x = c.gethPipeDX();
                        y = c.gethPipeDY();
                        v = c.gethPipeDV();
                    }
                    jsonArrayForX.put(Optional.ofNullable(x).orElse(0f));
                    jsonArrayForY.put(Optional.ofNullable(y).orElse(0f));
                    jsonArrayForV.put(Optional.ofNullable(v).orElse(0f));

                    jsonArrayForCoalMill.put(c.getCoalMillValue());
                }
            }

            //将每个粉管的浓度与风速，封装成float对象
            /****
             Map<String, float[]> map = PipeDataHandleServer.getHandlerDensityAndVelocityData
             (coalPipeHistoryEntityList);
             if (null != map) {
             float[] d1 = map.get("d1"), d2 = map.get("d2"), d3 = map.get("d3"), d4 = map.get("d4");
             float[] v1 = map.get("v1"), v2 = map.get("v2"), v3 = map.get("v3"), v4 = map.get("v4");
             for (int i = 0; i < d1.length; i++) {


             Float mill = coalPipeHistoryEntityList.get(i).getCoalMillValue();
             if(null == mill)
             mill = 100f;
             //                        mill = mill/100;
             float[] densityArr = new float[]{d1[i], d2[i], d3[i], d4[i]};
             Float desity1Real = 0f;
             Float desity2Real = 0f;
             Float desity3Real = 0f;
             Float desity4Real = 0f;

             Float velocity1Real = 0f;
             Float velocity2Real = 0f;
             Float velocity3Real = 0f;
             Float velocity4Real = 0f;
             if (original) {
             desity1Real = d1[i];
             desity2Real = d2[i];
             desity3Real = d3[i];
             desity4Real = d4[i];

             velocity1Real = v1[i];
             velocity2Real = v2[i];
             velocity3Real = v3[i];
             velocity4Real = v4[i];
             } else {
             desity1Real = PipeDataHandleServer.getDencityRealValue(d1[i], mill, densityArr);
             desity2Real = PipeDataHandleServer.getDencityRealValue(d2[i], mill, densityArr);
             desity3Real = PipeDataHandleServer.getDencityRealValue(d3[i], mill, densityArr);
             desity4Real = PipeDataHandleServer.getDencityRealValue(d4[i], mill, densityArr);

             velocity1Real = PipeDataHandleServer.getVelocityRealValue(v1[i]);
             velocity2Real = PipeDataHandleServer.getVelocityRealValue(v2[i]);
             velocity3Real = PipeDataHandleServer.getVelocityRealValue(v3[i]);
             velocity4Real = PipeDataHandleServer.getVelocityRealValue(v4[i]);
             }
             jsonArrayForPipe1Velocity.put(velocity1Real);
             jsonArrayForPipe2Velocity.put(velocity2Real);
             jsonArrayForPipe3Velocity.put(velocity3Real);
             jsonArrayForPipe4Velocity.put(velocity4Real);

             jsonArrayForPipe1Density.put(desity1Real);
             jsonArrayForPipe2Density.put(desity2Real);
             jsonArrayForPipe3Density.put(desity3Real);
             jsonArrayForPipe4Density.put(desity4Real);
             }
             }

             int dif = coalPipeHistoryEntityList.size() - jsonArrayForPipe1Density.length();
             for (int i = dif; i < coalPipeHistoryEntityList.size(); i++) {
             Float mill = coalPipeHistoryEntityList.get(i).getCoalMillValue();
             if(null == mill)
             mill = 100f;
             mill = mill / 100;
             jsonArrayForCoalMill.put(mill);
             }
             //                for (CoalPipingHistory h : coalPipeHistoryEntityList) {
             //                    jsonArrayForCoalMill.put(h.getCoalMillValue());
             //                }
             }
             //            System.out.println("jsonArrayForPipe1Density:"+jsonArrayForPipe1Density.toString());


             */


            jsonObject.put("startTime", beginC.getTimeInMillis());
            jsonObject.put("endTime", endC.getTimeInMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            jsonObject.put("startTimeStr", simpleDateFormat.format(beginC.getTime()));
            jsonObject.put("endTimeStr", simpleDateFormat.format(endC.getTime()));

            jsonObject.put("xDatas", jsonArrayForX);
            jsonObject.put("yDatas", jsonArrayForY);
            jsonObject.put("vDatas", jsonArrayForV);

            jsonObject.put("coalMillDatas", jsonArrayForCoalMill);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 生成一个 history对象
     *
     * @param coalMillEntity
     * @param now
     * @return
     */
    public CoalPipingHistory generatorHistory(CoalMillEntity coalMillEntity, Date now) {
        Long coalMillId = coalMillEntity.getId();
        CoalPipingHistory coalPipingHistory = null;
        switch (coalMillId.intValue()) {
            case 1:
                coalPipingHistory = new AcoalPipingHistoryEntity(coalMillEntity, now);
//                coalPipingHistoryRepositoryA.save((AcoalPipingHistoryEntity) coalPipingHistory);
                break;
            case 2:
                coalPipingHistory = new BcoalPipingHistoryEntity(coalMillEntity, now);
//                coalPipingHistoryRepositoryB.save((BcoalPipingHistoryEntity) coalPipingHistory);
                break;
            case 3:
                coalPipingHistory = new CcoalPipingHistoryEntity(coalMillEntity, now);
//                coalPipingHistoryRepositoryC.save((CcoalPipingHistoryEntity) coalPipingHistory);
                break;
            case 4:
                coalPipingHistory = new DcoalPipingHistoryEntity(coalMillEntity, now);
//                coalPipingHistoryRepositoryD.save((DcoalPipingHistoryEntity) coalPipingHistory);
                break;
            default:
                break;
        }
        return coalPipingHistory;
    }

    private CoalPipingHistory setCoalPipingHistory(CoalPipingEntity coalPipingEntity, CoalPipingHistory coalPipingHistoryEntity) {
        String pipingLocation = coalPipingEntity.getpLocation();
        Float velocity = coalPipingEntity.getpVelocity();
        Float density = coalPipingEntity.getpDencity();
        Float x = coalPipingEntity.getX();
        Float y = coalPipingEntity.getY();
        Float v = coalPipingEntity.getV();
        switch (pipingLocation) {
            case "A":
                coalPipingHistoryEntity.sethPipeADencity(density);
                coalPipingHistoryEntity.sethPipeAVelocity(velocity);
                coalPipingHistoryEntity.sethPipeAX(x);
                coalPipingHistoryEntity.sethPipeAY(y);
                coalPipingHistoryEntity.sethPipeAV(v);
                break;
            case "B":
                coalPipingHistoryEntity.sethPipeBDencity(density);
                coalPipingHistoryEntity.sethPipeBVelocity(velocity);
                coalPipingHistoryEntity.sethPipeBX(x);
                coalPipingHistoryEntity.sethPipeBY(y);
                coalPipingHistoryEntity.sethPipeBV(v);
                break;
            case "C":
                coalPipingHistoryEntity.sethPipeCDencity(density);
                coalPipingHistoryEntity.sethPipeCVelocity(velocity);
                coalPipingHistoryEntity.sethPipeCX(x);
                coalPipingHistoryEntity.sethPipeCY(y);
                coalPipingHistoryEntity.sethPipeCV(v);
                break;
            case "D":
                coalPipingHistoryEntity.sethPipeDDencity(density);
                coalPipingHistoryEntity.sethPipeDVelocity(velocity);
                coalPipingHistoryEntity.sethPipeDX(x);
                coalPipingHistoryEntity.sethPipeDY(y);
                coalPipingHistoryEntity.sethPipeDV(v);
                break;
            default:
                break;
        }


        return coalPipingHistoryEntity;
    }

    /**
     * 更新历史数据
     *
     * @param coalMillId
     * @param coalPipingHistoryEntity
     */
    public void updateCoalPipingHistory(Long coalMillId, CoalPipingHistory coalPipingHistoryEntity) {
//        System.out.println("更新历史数据：" + coalMillId.intValue() + "," + coalPipingHistoryEntity.gethPipeADencity() + ","
//                + coalPipingHistoryEntity.getPipeADensityNotNull());
        switch (coalMillId.intValue()) {

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
            default:
                break;
        }
    }

    /**
     * 更新历史数据
     *
     * @param coalPipingEntityList    所有的对象归属于一个Mill
     * @param coalPipingHistoryEntity
     */
    public void updateHistory(List<CoalPipingEntity> coalPipingEntityList, CoalPipingHistory coalPipingHistoryEntity) {
        for (CoalPipingEntity coalPipingEntity : coalPipingEntityList) {
            setCoalPipingHistory(coalPipingEntity, coalPipingHistoryEntity);
        }
        updateCoalPipingHistory(coalPipingEntityList.get(0).getpCoalMillId(), coalPipingHistoryEntity);
    }

    public void updateCoalPipintHistoryData(List<CoalPipingHistory> coalPipingHistorieList, List<CoalMillEntity>
            coalMillEntityList) {
        for (CoalMillEntity coalMillEntity : coalMillEntityList) {
            List<CoalPipingEntity> coalPipingEntityList = coalMillEntity.getCoalPipingEntityList();
            CoalPipingHistory coalPipingHistoryByCoalMill = getCoalPipingHistoryListByCoalMill
                    (coalMillEntity, coalPipingHistorieList);
            //更新一台磨煤机的风管历史数据
            for (CoalPipingEntity coalPipingEntity : coalPipingEntityList) {
                setCoalPipingHistory(coalPipingEntity, coalPipingHistoryByCoalMill);
            }
        }
    }

    public CoalPipingHistory getCoalPipingHistoryListByCoalMill(CoalMillEntity coalMillEntity, List<CoalPipingHistory>
            coalPipingHistories) {
        CoalPipingHistory coalPipingHistory = null;
        Long coalMillId = coalMillEntity.getId();
        for (CoalPipingHistory coalPipingHistoryTemp : coalPipingHistories) {
            Long millIdForTemp = coalPipingHistoryTemp.gethCoalMillId();
            if (coalMillId.equals(millIdForTemp)) {
                coalPipingHistory = coalPipingHistoryTemp;
                break;
            }
        }
        return coalPipingHistory;
    }

    /**
     * 根据coalPiping的实时数据，更新历史数据
     *
     * @param coalPipingEntity
     * @param coalPipingHistoryEntity
     */
    public void updateHistory(CoalPipingEntity coalPipingEntity, CoalPipingHistory coalPipingHistoryEntity) {
        //设置历史数据ABCD四根管的密度与风速
        setCoalPipingHistory(coalPipingEntity, coalPipingHistoryEntity);
        Long coalMillId = coalPipingEntity.getpCoalMillId();
        //保存粉管历史数据
//        updateCoalPipingHistory(coalMillId,coalPipingHistoryEntity);


    }


    public List<T> handleDataToReport(List<T> list) {
        //TODO 当第一个返回为null，进行处理
        //todo 当第一个的时间不是查询的开始时间，进行处理
//        Instant.
        Instant instantNow = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.now();
//        instantNow = localDateTime.toInstant(ZoneOffset.of());

        LocalDateTime localDateTime1 = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Instant instantNow2 = localDateTime1.toInstant(ZoneOffset.of("+00:00"));

//        System.out.println("数据size：" + list.size());
        //01-数据分组函数，分组条件为每300秒分一组
        Function<T, String> groupByingFunction = (a) -> {
            long durationsSeconds = getDurationBetweenNowAndCurrent(instantNow2, a);
            int result = (int) Math.floor(durationsSeconds / 300);
//            System.out.println("分组result：" + result);
            return String.valueOf(result);
        };
        //数据分组，返回map格式的数据
        Map<String, List<T>> map = list.stream().collect(Collectors.groupingBy(groupByingFunction));

        //02-将map对象转换为list<map.Entry>
        List<Map.Entry<String, List<T>>> listA = new ArrayList<>(map.entrySet());
        //03-对分组后的数据进行排序，默认为降序，使用comparator.reversed方法进行反转，实现升序排列。
        Comparator<Map.Entry<String, List<T>>> comparator = Comparator.comparing((b) -> Integer.parseInt(b.getKey()));
//        Collections.sort(listA, comparator.reversed());
        listA.sort(comparator.reversed());
        listA.forEach((l) -> {
//            System.out.println(l.getKey() + "," + l.getValue().size() + ",  min:  " + l.getValue().stream().min(Comparator.comparing(T::gethTime)).get().gethTime());
        });


        //04-将List<Map.entry>转换为List<List<T>>对象，使用map方法
        List<List<T>> list3 = listA.stream().map((l) -> l.getValue()).collect(Collectors.toList());
       /* for(int i=0;i<list3.size();i++){
            List<T> listT = list3.get(i);
            T t = list.stream().findFirst().get();
            LocalDateTime localDateTime = t.gethTime().toLocalDateTime();
            LocalDate localDate = localDateTime.toLocalDate();
            LocalDateTime localDateTime1 = LocalDateTime.of(localDate,null);
            localDateTime1.plusMinutes(i * 5);
            t.sethTime(Timestamp.valueOf(localDateTime1));
        }*/
        //05-对List<List<T>>数据进行统计分析,生成List<T>集合
        List<T> list5 = list3.stream().map((l2) -> {
            //TODO 第一个数据可以能为null
            //TODO new一个 T 对象，时间设置为00:00 开始，下一个对象为5分钟以后的对象
//            T t = CoalPipingHistory.GeneratorCoalPipingHistory();
//            T t = l2.stream().findFirst().get();
            T t = l2.stream().min(Comparator.comparing(T::gethTime)).get();
            //TODO 对数据进行平均取值，此方法需要设置接口，
            //TODO 当数据为null时，返回了0，平均的时候，将0计入，并进行了平均【这样的方式存在问题】
            //TODO              如果数据为null，返回0，数据平均的时候，不应该计入平均数中。
            /*double densityAAvg = l2.stream().mapToDouble((ache) ->
                    new Double(Optional.ofNullable(ache.gethPipeAVelocity()).orElse(0f)))
                    .average().orElse(0d);*/
            double densityAAvg = l2.stream().mapToDouble(T::getPipeADensityNotNull).average().orElse(0d);
            double densityBAvg = l2.stream().mapToDouble(T::getPipeBDensityNotNull).average().orElse(0d);
            double densityCAvg = l2.stream().mapToDouble(T::getPipeCDensityNotNull).average().orElse(0d);
            double densityDAvg = l2.stream().mapToDouble(T::getPipeDDensityNotNull).average().orElse(0d);
            double velocityAAvg = l2.stream().mapToDouble(T::getPipeAVelocityNotNull).average().orElse(0d);
            double velocityBAvg = l2.stream().mapToDouble(T::getPipeBVelocityNotNull).average().orElse(0d);
            double velocityCAvg = l2.stream().mapToDouble(T::getPipeCVelocityNotNull).average().orElse(0d);
            double velocityDAvg = l2.stream().mapToDouble(T::getPipeDVelocityNotNull).average().orElse(0d);

            t.sethPipeADencity((float) densityAAvg);
            t.sethPipeBDencity((float) densityBAvg);
            t.sethPipeCDencity((float) densityCAvg);
            t.sethPipeDDencity((float) densityDAvg);

            t.sethPipeAVelocity((float) velocityAAvg);
            t.sethPipeBVelocity((float) velocityBAvg);
            t.sethPipeCVelocity((float) velocityCAvg);
            t.sethPipeDVelocity((float) velocityDAvg);
//            double densityBAvg = l2.stream().map()
//            l2.stream().map((acphe)->)
            return t;
        }).collect(Collectors.toList());

        /* System.out.println("list5:" + list5.size() + "," + list5.stream().findFirst().get().gethPipeADencity());*/
        list5.forEach((l) -> {
//            System.out.println(l.gethTime() + "," + l.gethPipeADencity());
        });

        return list5;
    }

    private long getDurationBetweenNowAndCurrent(Instant instantNow, T a) {
//        Instant instantCurrent = Instant.ofEpochMilli(a.gethTime().getTime());
//        instantCurrent.atOffset(ZoneOffset.of("+08:00"));
        Instant instant = a.gethTime().toLocalDateTime().toInstant(ZoneOffset.of("+00:00"));
        Duration duration = Duration.between(instantNow, instant);
        return duration.getSeconds();
    }


    public List<? extends CoalPipingHistory> findMillPipeDataHistoryByMillLocation(Long millId, Timestamp begin,
                                                                                   Timestamp end) {
        String millLocation = "";
        if (null != millId) {
            if (millId.longValue() == 1L) {
                millLocation = "A";
            } else if (millId.longValue() == 2L) {
                millLocation = "B";
            } else if (millId.longValue() == 3L) {
                millLocation = "C";
            } else if (millId.longValue() == 4L) {
                millLocation = "D";
            }
            return findMillPipeDataHistoryByMillLocation(millLocation, begin, end);
        } else {
            return null;
        }
    }

    /**
     * 查询一段时间范围内的一台磨煤机的四根粉管的原始数据
     *
     * @param millLocation
     * @param begin
     * @param end
     */
    public List<CoalPipingHistory> findMillPipeDataHistoryByMillLocation(String millLocation, Timestamp begin, Timestamp end) {
        List<CoalPipingHistory> list = new ArrayList<>();
//        System.out.println("开始：" + begin + ", 结束：" + end);
        if (null != millLocation) {

            if (millLocation.equals("A")) {
                List<AcoalPipingHistoryEntity> listForA = coalPipingHistoryRepositoryA.findByHTimeBetween(begin, end);
                list.addAll(listForA);
            } else if (millLocation.equals("B")) {
                List<BcoalPipingHistoryEntity> listForB = coalPipingHistoryRepositoryB.findByHTimeBetween(begin, end);
                list.addAll(listForB);
            } else if (millLocation.equals("C")) {
                List<CcoalPipingHistoryEntity> listForC = coalPipingHistoryRepositoryC.findByHTimeBetween(begin, end);
                list.addAll(listForC);
            } else if (millLocation.equals("D")) {
                List<DcoalPipingHistoryEntity> listForD = coalPipingHistoryRepositoryD.findByHTimeBetween(begin, end);
                list.addAll(listForD);
            }
        }
        return list;
    }


    /**
     * 查询一段时间范围内的一台磨煤机的四根粉管的原始数据，多线程查询
     *
     * @param millLocation A   B   C   D
     * @param begin        开始时间
     * @param end          结束时间
     */
    public List<CoalPipingHistory> findMillPipeDataHistoryByMillLocationWithThreads(String millLocation,
                                                                                    Timestamp begin, Timestamp end) {
        List<CoalPipingHistory> resultList = null;
        final List<CoalPipingHistory> list = new ArrayList<>();
        //如果查询的时间段大于30分钟，进行分段查询
        try {
            LocalDateTime beginLocalDateTime = begin.toLocalDateTime();
            LocalDateTime endLocalDateTime = end.toLocalDateTime();

            ZoneOffset zoneOffset = ZoneOffset.of("+08:00");
            Instant instantForBegin = beginLocalDateTime.toInstant(zoneOffset);
            Instant instantForEnd = endLocalDateTime.toInstant(zoneOffset);
            Duration duration = Duration.between(instantForBegin, instantForEnd);
            long durationSeconds = duration.getSeconds();
            long interval = 30 * 60;
            if (durationSeconds > interval) {
                long counts = (durationSeconds / (interval));
                int threadCounts = (int) counts;
                if (durationSeconds % (interval) != 0) {
                    threadCounts = threadCounts + 1;
                }
                CountDownLatch countDownLatch = new CountDownLatch(threadCounts);
                for (int i = 0; i < counts; i++) {
                    final LocalDateTime localDateTimeForBeginWithStep = beginLocalDateTime.plusSeconds(i * (interval));
                    final LocalDateTime localDateTimeForEndWithStep = beginLocalDateTime.plusSeconds((i + 1) * (interval) - 1);
                    final Timestamp beginForStep = Timestamp.valueOf(localDateTimeForBeginWithStep);
                    final Timestamp endForStep = Timestamp.valueOf(localDateTimeForEndWithStep);

                    new Thread(() -> {
                        List<? extends CoalPipingHistory> listForStep = findMillPipeDataHistoryByMillLocation(millLocation, beginForStep, endForStep);
                        synchronized (list) {
                            list.addAll(listForStep);
                        }
                        countDownLatch.countDown();
                    }).start();
                }
                if (durationSeconds % (interval) != 0) {
                    LocalDateTime localDateTimeForBeginWithStep = endLocalDateTime.minusSeconds(durationSeconds % (interval));
                    LocalDateTime localDateTimeForEndWithStep = endLocalDateTime;
                    Timestamp beginForStep = Timestamp.valueOf(localDateTimeForBeginWithStep);
                    Timestamp endForStep = Timestamp.valueOf(localDateTimeForEndWithStep);
                    new Thread(() -> {
                        List<? extends CoalPipingHistory> listForStep = findMillPipeDataHistoryByMillLocation(millLocation, beginForStep,
                                endForStep);
                        synchronized (list) {
                            list.addAll(listForStep);
                        }
                        countDownLatch.countDown();
                    }).start();
                }
                countDownLatch.await();
                Comparator<CoalPipingHistory> coalPipingHistoryComparator = Comparator.comparing
                        (CoalPipingHistory::gethTime);
                resultList = list.stream().sorted(coalPipingHistoryComparator).collect(Collectors.toList());
            } else {
                resultList = findMillPipeDataHistoryByMillLocation(millLocation, begin, end);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        /***************************粉管参数设定的偏差计算*****************************/
        //四根粉管的XY数据标定数据，进行计算，生成一个数组
        List<CoalPipingSetEntity> coalPipingSetEntityList = coalMillPipingSetRepository.findAll();
        Map<Long, CoalPipingSetEntity> map = new HashMap<>();
        for (CoalPipingSetEntity coalPipingSetEntity : coalPipingSetEntityList) {
            map.put(coalPipingSetEntity.getCoalPipeId(), coalPipingSetEntity);
        }

        Float p1X = 0f, p1Y = 0f, p2X = 0f, p2Y = 0f, p3X = 0f, p3Y = 0f, p4X = 0f, p4Y = 0f;
        Long millBaseLocation = 1L;
        if (millLocation.equals("A")) {
            millBaseLocation = 1L;
        } else if (millLocation.equals("B")) {
            millBaseLocation = 2L;
        } else if (millBaseLocation.equals("C")) {
            millBaseLocation = 3L;
        } else if (millBaseLocation.equals("D")) {
            millBaseLocation = 4L;
        }
        millBaseLocation = millBaseLocation * 10L;
        p1X = map.get(millBaseLocation + 1L).getsParam3();
        p1Y = map.get(millBaseLocation + 1L).getsParam4();
        p2X = map.get(millBaseLocation + 2L).getsParam4();
        p2Y = map.get(millBaseLocation + 2L).getsParam4();
        p3X = map.get(millBaseLocation + 3L).getsParam3();
        p3Y = map.get(millBaseLocation + 3L).getsParam4();
        p4X = map.get(millBaseLocation + 4L).getsParam4();
        p4Y = map.get(millBaseLocation + 4L).getsParam4();

        for (int i = 0; i < resultList.size(); i++) {
            CoalPipingHistory coalPipingHistory = resultList.get(i);
            coalPipingHistory.sethPipeAX(Optional.ofNullable(coalPipingHistory.gethPipeAX()).orElse(0F) * p1X);
            coalPipingHistory.sethPipeAY(Optional.ofNullable(coalPipingHistory.gethPipeAY()).orElse(0F) * p1Y);

            coalPipingHistory.sethPipeBX(Optional.ofNullable(coalPipingHistory.gethPipeBX()).orElse(0F) * p2X);
            coalPipingHistory.sethPipeBY(Optional.ofNullable(coalPipingHistory.gethPipeBY()).orElse(0F) * p2Y);

            coalPipingHistory.sethPipeCX(Optional.ofNullable(coalPipingHistory.gethPipeCX()).orElse(0F) * p3X);
            coalPipingHistory.sethPipeCY(Optional.ofNullable(coalPipingHistory.gethPipeCY()).orElse(0F) * p3Y);

            coalPipingHistory.sethPipeDX(Optional.ofNullable(coalPipingHistory.gethPipeDX()).orElse(0F) * p4X);
            coalPipingHistory.sethPipeDY(Optional.ofNullable(coalPipingHistory.gethPipeDY()).orElse(0F) * p4Y);
//            System.out.println(i+"," + resultList.get(i).gethTime() + "," + resultList.get(i).getPipeADensityNotNull());
        }

        /***************************历史数据查询偏差重新设定****************************/

        //数据偏差记录，读取excel中的偏差
//        DataFilter.filter(millLocation,resultList);






        //处理数据，去除0的数据
        for(int i=0;i<resultList.size();i++){
            if(null == resultList.get(i).gethPipeADencity() || resultList.get(i).gethPipeADencity() < 10000){
                if(i-1>0){
                    CoalPipingHistory coalPipingHistoryBefor = resultList.get(i-1);
                    resultList.get(i).sethPipeADencity(coalPipingHistoryBefor.gethPipeADencity());
                }
            }
            if(null == resultList.get(i).gethPipeBDencity() || resultList.get(i).gethPipeBDencity() <
                    10000){
                if(i-1>0){
                    CoalPipingHistory coalPipingHistoryBefor = resultList.get(i-1);
                    resultList.get(i).sethPipeBDencity(coalPipingHistoryBefor.gethPipeBDencity());
                }
            }
            if(null == resultList.get(i).gethPipeCDencity() || resultList.get(i).gethPipeCDencity() <
                    10000){
                if(i-1>0){
                    CoalPipingHistory coalPipingHistoryBefor = resultList.get(i-1);
                    resultList.get(i).sethPipeCDencity(coalPipingHistoryBefor.gethPipeCDencity());
                }
            }
            if(null == resultList.get(i).gethPipeDDencity() || resultList.get(i).gethPipeDDencity() <
                    10000){
                if(i-1>0){
                    CoalPipingHistory coalPipingHistoryBefor = resultList.get(i-1);
                    resultList.get(i).sethPipeDDencity(coalPipingHistoryBefor.gethPipeDDencity());
                }
            }
        }




        /*******************************历史数据 磨煤量与电荷量对比关系*******************************************/
//
        //读取幂指数开关与幂指数
        DataFilter.updateOrigianlDataWithExcel(resultList);



//        resultList.forEach(coalPipingHistory -> {
//
//            coalPipingHistory.sethPipeADencity((float) Math.log(coalPipingHistory.gethPipeADencity()));
//            coalPipingHistory.sethPipeBDencity((float) Math.log(coalPipingHistory.gethPipeBDencity()));
//            coalPipingHistory.sethPipeCDencity((float) Math.log(coalPipingHistory.gethPipeCDencity()));
//            coalPipingHistory.sethPipeDDencity((float) Math.log(coalPipingHistory.gethPipeDDencity()));
//        });
        return resultList;
    }


    /**
     * 查询一段时间范围内的一台磨煤机的磨煤量历史数据
     *
     * @param millLocation
     * @param begin
     * @param end
     */
    public List<H000Pojo_Base> findMillDataHistoryByMillLocation(String millLocation, Timestamp begin, Timestamp end) {
        List<H000Pojo_Base> h000Pojo_baseList = null;
        if (null != millLocation) {

            if (millLocation.equals("A")) {
                h000Pojo_baseList = dcsHistoryService.findByTime(75, begin, end);
            } else if (millLocation.equals("B")) {
                h000Pojo_baseList = dcsHistoryService.findByTime(76, begin, end);
            } else if (millLocation.equals("C")) {
                h000Pojo_baseList = dcsHistoryService.findByTime(77, begin, end);
            } else if (millLocation.equals("D")) {
                h000Pojo_baseList = dcsHistoryService.findByTime(78, begin, end);
            }
        }
        if (null == h000Pojo_baseList || h000Pojo_baseList.size() == 0) {
            h000Pojo_baseList = new ArrayList<>();
            H000Pojo_Base h000Pojo_base = new H000Pojo_Base();
            h000Pojo_base.setV(-1f);
            h000Pojo_base.setvTime(begin);
            h000Pojo_baseList.add(h000Pojo_base);
        }
        return h000Pojo_baseList;
    }
}
