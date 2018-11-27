package com.gaussic.service;

import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.util.DataHandleUtil.PipeHandlerUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 此类用于处理，实际的风管浓度与风速
 */
@Component
public class PipeDataHandleServer {
    public static final float operatorValue = 1.8f;
    public static final float distance = 5.0f;


    /**
     * 对浓度与速度进行平滑处理，并将数据对应实际的磨煤量与风速
     *
     * @param coalPipingHistoryList
     */
    public static void updatePipeDensityAndVelocityWithAvgHandle(List<? extends CoalPipingHistory>
                                                                         coalPipingHistoryList) {
        //将4个粉管的浓度与风速形成float[]对象，进行平滑处理
        Map<String, float[]> map = getHandlerDensityAndVelocityData(coalPipingHistoryList);
        if (null != map) {
            float[] d1 = map.get("d1"), d2 = map.get("d2"), d3 = map.get("d3"), d4 = map.get("d4");
            float[] v1 = map.get("v1"), v2 = map.get("v2"), v3 = map.get("v3"), v4 = map.get("v4");
            for (int i = 0; i < d1.length; i++) {

                CoalPipingHistory coalPipingHistory = coalPipingHistoryList.get(i);
                float mill = 0f;
                if (null != coalPipingHistory.getCoalMillValue()) {
                    mill = coalPipingHistory.getCoalMillValue();
                }
//                        mill = mill/100;
                float[] densityArr = new float[]{d1[i], d2[i], d3[i], d4[i]};
                Float desity1Real = PipeDataHandleServer.getDencityRealValue(d1[i], mill, densityArr);
                Float desity2Real = PipeDataHandleServer.getDencityRealValue(d2[i], mill, densityArr);
                Float desity3Real = PipeDataHandleServer.getDencityRealValue(d3[i], mill, densityArr);
                Float desity4Real = PipeDataHandleServer.getDencityRealValue(d4[i], mill, densityArr);

                Float velocity1Real = PipeDataHandleServer.getVelocityRealValue(v1[i]);
                Float velocity2Real = PipeDataHandleServer.getVelocityRealValue(v2[i]);
                Float velocity3Real = PipeDataHandleServer.getVelocityRealValue(v3[i]);
                Float velocity4Real = PipeDataHandleServer.getVelocityRealValue(v4[i]);

                coalPipingHistory.sethPipeADencity(desity1Real);
                coalPipingHistory.sethPipeBDencity(desity2Real);
                coalPipingHistory.sethPipeCDencity(desity3Real);
                coalPipingHistory.sethPipeDDencity(desity4Real);

                coalPipingHistory.sethPipeAVelocity(velocity1Real);
                coalPipingHistory.sethPipeBVelocity(velocity2Real);
                coalPipingHistory.sethPipeCVelocity(velocity3Real);
                coalPipingHistory.sethPipeDVelocity(velocity4Real);

            }
        }
    }

    /**
     * 返回平滑处理后的浓度与风速数据
     *
     * @param coalPipeHistoryEntityList
     * @return
     */
    public static Map<String, float[]> getHandlerDensityAndVelocityData(List<? extends CoalPipingHistory>
                                                                                coalPipeHistoryEntityList) {
//        System.out.println("coalPipeHistoryEntityList:" +coalPipeHistoryEntityList);
        if (null != coalPipeHistoryEntityList) {
            int size = coalPipeHistoryEntityList.size();
            float[] density1Avg = new float[size];
            float[] density2Avg = new float[size];
            float[] density3Avg = new float[size];
            float[] density4Avg = new float[size];
            float[] velocity1Avg = new float[size];
            float[] velocity2Avg = new float[size];
            float[] velocity3Avg = new float[size];
            float[] velocity4Avg = new float[size];
            for (int i = 0; i < size; i++) {
                CoalPipingHistory c = coalPipeHistoryEntityList.get(i);
                density1Avg[i] = c.getPipeADensityNotNull();
                density2Avg[i] = c.getPipeBDensityNotNull();
                density3Avg[i] = c.getPipeCDensityNotNull();
                density4Avg[i] = c.getPipeDDensityNotNull();
                velocity1Avg[i] = c.getPipeAVelocityNotNull();
                velocity2Avg[i] = c.getPipeBVelocityNotNull();
                velocity3Avg[i] = c.getPipeCVelocityNotNull();
                velocity4Avg[i] = c.getPipeDVelocityNotNull();
            }

            //平滑处理
            float[] d1 = new PipeHandlerUtil().getHandleData(density1Avg);
            float[] d2 = new PipeHandlerUtil().getHandleData(density2Avg);
            float[] d3 = new PipeHandlerUtil().getHandleData(density3Avg);
            float[] d4 = new PipeHandlerUtil().getHandleData(density4Avg);

            float[] v1 = new PipeHandlerUtil().getHandleData(velocity1Avg);
            float[] v2 = new PipeHandlerUtil().getHandleData(velocity2Avg);
            float[] v3 = new PipeHandlerUtil().getHandleData(velocity3Avg);
            float[] v4 = new PipeHandlerUtil().getHandleData(velocity4Avg);

            Map<String, float[]> map = new HashMap<>();
            map.put("d1", d1);
            map.put("d2", d2);
            map.put("d3", d3);
            map.put("d4", d4);
            map.put("v1", v1);
            map.put("v2", v2);
            map.put("v3", v3);
            map.put("v4", v4);
            return map;
        } else {
            return null;
        }

    }

    /**
     * 更新磨煤机的粉管的风速与浓度为实际值
     * 浓度数据与磨煤量相关：总磨煤量 /4 * pipe1Data/pipeAvg
     * 风速经过计算得到 ：0.05/(time * operatorValue) *1000 ,operatorValue默认为1.8
     *
     * @param millObj {pipe1:{density:_,Velocity:_},pipe2{},pipe3{},pipe4:{}}
     */
    public static void updatePipeDensityAndVelocity(JSONObject millObj) {
        JSONObject millPipe1JsonObj = millObj.getJSONObject("pipe1");
        JSONObject millPipe2JsonObj = millObj.getJSONObject("pipe2");
        JSONObject millPipe3JsonObj = millObj.getJSONObject("pipe3");
        JSONObject millPipe4JsonObj = millObj.getJSONObject("pipe4");
        float mill = millObj.getFloat("coalCount");
        float pipe1Density = millPipe1JsonObj.getFloat("density");
        float pipe2Density = millPipe2JsonObj.getFloat("density");
        float pipe3Density = millPipe3JsonObj.getFloat("density");
        float pipe4Density = millPipe4JsonObj.getFloat("density");
        float[] densityArr = new float[]{pipe1Density, pipe2Density, pipe3Density, pipe4Density};


        float pipe1Velocity = millPipe1JsonObj.getFloat("Velocity");
        float pipe2Velocity = millPipe2JsonObj.getFloat("Velocity");
        float pipe3Velocity = millPipe3JsonObj.getFloat("Velocity");
        float pipe4Velocity = millPipe4JsonObj.getFloat("Velocity");

        float pipe1DensityReal = getDencityRealValue(pipe1Density, mill, densityArr);
        float pipe2DensityReal = getDencityRealValue(pipe2Density, mill, densityArr);
        float pipe3DensityReal = getDencityRealValue(pipe3Density, mill, densityArr);
        float pipe4DensityReal = getDencityRealValue(pipe4Density, mill, densityArr);


        float pipe1VelocityReal = getVelocityRealValue(pipe1Velocity);
        float pipe2VelocityReal = getVelocityRealValue(pipe2Velocity);
        float pipe3VelocityReal = getVelocityRealValue(pipe3Velocity);
        float pipe4VelocityReal = getVelocityRealValue(pipe4Velocity);


        millPipe1JsonObj.put("Velocity", pipe1VelocityReal);
        millPipe2JsonObj.put("Velocity", pipe2VelocityReal);
        millPipe3JsonObj.put("Velocity", pipe3VelocityReal);
        millPipe4JsonObj.put("Velocity", pipe4VelocityReal);
        millPipe1JsonObj.put("density", pipe1DensityReal);
        millPipe2JsonObj.put("density", pipe2DensityReal);
        millPipe3JsonObj.put("density", pipe3DensityReal);
        millPipe4JsonObj.put("density", pipe4DensityReal);
    }

    /**
     * 获取风速的实际值
     * 时间量大于20,都不进行计算，算为故障,返回-1
     * 或者小于5，不进行计算，返回0
     *
     * @param velocity float  风速的实际时间量
     * @return float 风速的实际值
     */
    public static float getVelocityRealValue(float velocity) {
        float pipe1VelocityReal = 0f;
        if (velocity >= 5 && velocity <= 20) {
            pipe1VelocityReal = ((int) ((distance / (velocity * operatorValue)) * 1000)) / 10f;
        } else if (velocity < 5 && velocity > 0) {
            pipe1VelocityReal = 0f;
        } else if (velocity > 20) {
            pipe1VelocityReal = -1f;
        } else if (velocity == 0) {
            pipe1VelocityReal = 0f;
        }
        return pipe1VelocityReal;
    }

    /**
     * 获取煤粉浓度的实际值
     * 1、实际数据小于50万，返回0
     * 2、高于2000万，返回0
     * 3、当4根粉管正常时，使用100%的磨煤量，
     * 当3个正常时，使用75%的磨煤量，
     * 当2个正常时，使用50%的磨煤量，
     * 当1根正常时，使用25%的磨煤量
     *
     * @param dencity    浓度的电压值
     * @param mill       磨煤机的磨煤量，DCS传递过来的数据，计算时需要除以100
     * @param densityArr 4个粉管的浓度电压值信号的数组
     * @return float 当前浓度电压值对应的 实际浓度数据
     */
    public static float getDencityRealValue(float dencity, float mill, float[] densityArr) {
        float densityReal = 0f;

//        float totalDensity = pipe1Density + pipe2Density + pipe3Density + pipe4Density;

        if (dencity < 500000 && dencity > 20000000) {
            densityReal = 0f;
        } else {
//            int count = 0;
//            for (float densityTemp : densityArr) {
//                totalDensity += densityTemp;
//                count++;
//            }


//            List<Object> floatList = Arrays.asList(densityArr);
//            Arrays.asList(densityArr).stream().map(Double.class::cast).mapToDouble(Double::doubleValue).sum();
//            Stream.of(densityArr).map(Double.class::cast).mapToDouble(Double::doubleValue).sum();
//            List<Float> resultFloatsList = floatList.stream().filter(f -> f > 500000).filter(f -> f < 20000000).collect(Collectors.toList());
//            int count = resultFloatsList.size();
//            totalDensity = (float) resultFloatsList.stream().mapToDouble(Float::doubleValue).sum();
//            floatList.stream().map(f -> (f.doubleValue())).mapToDouble(Double::doubleValue).sum();


            /*
            float totalDensity = 0f;
            int count = 0;
            for (float densityTemp : densityArr) {
                if(densityTemp>500000 && densityTemp < 20000000) {
                    totalDensity += densityTemp;
                    count++;
                }
            }*/



            List<Double> doubleList = Stream.of(densityArr).map(Double.class::cast).filter(d->d>500000).filter(d->d<20000000).collect(Collectors.toList());
            int count = doubleList.size();
            float totalDensity = (float) doubleList.stream().mapToDouble(Double::doubleValue).sum();

            int baseMill = (int) ((mill / 100 * (dencity / totalDensity)) * 100);

            if (count == densityArr.length) {
                //4根粉管都正常
                densityReal = baseMill / 100f;
            } else if (count == 3) {
                //3根正常
                densityReal = ((int) (baseMill * 0.75)) / 100f;
            } else if (count == 2) {
                //2根正常
                densityReal = ((int) (baseMill * 0.5)) / 100f;
            } else if (count == 1) {
                //1根正常
                densityReal = ((int) (baseMill * 0.25)) / 100f;
            }
        }
        return densityReal;


    }

}
