package com.gaussic.util;

import com.gaussic.dataGet.WindDataPojo;
import com.gaussic.model.CoalPipingEntity;

public class WindPojoToEntityUtil {
    /**
     * 通过windPojo类获取到CoalPipingEntity的实时数据
     * @param pojo
     */
    public static void updateRealData(CoalPipingEntity coalPipingEntity,WindDataPojo pojo) {
        //TODO 数据读取错误，无法设置成功

        coalPipingEntity.setpDencity(Float.parseFloat(pojo.getDensityXValue()));
        coalPipingEntity.setpVelocity(Float.parseFloat(pojo.getSpeedValue()));
    }
}
