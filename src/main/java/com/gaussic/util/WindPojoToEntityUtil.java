package com.gaussic.util;

import com.gaussic.dataGet.WindDataPojo;
import com.gaussic.model.CoalPipingEntity;

import java.sql.Timestamp;

public class WindPojoToEntityUtil {
    /**
     * 工具类, 解析过渡对象 ,转换为 实际的 entity对象
     * 通过windPojo类获取到CoalPipingEntity的实时数据
     * @param pojo
     */
    public static CoalPipingEntity updateRealData(long timeLong,CoalPipingEntity coalPipingEntity,WindDataPojo pojo) {
        //TODO 数据读取错误，无法设置成功

        coalPipingEntity.setpTime(new Timestamp(timeLong));
        if(null != pojo) {
            if (null != pojo.getDensityXValue()) {
                coalPipingEntity.setpDencity(Float.parseFloat(pojo.getDensityXValue()));
//                System.out.println(pojo);
            }else{
                coalPipingEntity.setpDencity(null);
            }
            if(null != pojo.getDensityYValue()) {
                coalPipingEntity.setpVelocity(Float.parseFloat(pojo.getSpeedValue()));
            }else{
                coalPipingEntity.setpVelocity(null);
            }
        }else {
            coalPipingEntity.setpDencity(null);
            coalPipingEntity.setpVelocity(null);
        }
        return coalPipingEntity;
    }
}
