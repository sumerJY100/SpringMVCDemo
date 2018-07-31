package com.gaussic.dataGet;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.CoalPipingSetEntity;
import com.gaussic.model.history.DcoalPipingHistoryEntity;
import com.gaussic.repository.CoalPipingHistoryRepositoryC;
import com.gaussic.repository.CoalPipingRepository;
import com.gaussic.util.HandleDeviceData;
import com.gaussic.util.WindPojoToEntityUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PipingGetSingleDataThread extends Thread {
    @Autowired
    private CoalPipingRepository coalPipingRepository;
    @Autowired
    private CoalPipingHistoryRepositoryC coalPipingHistoryRepositoryC;

    private CoalPipingEntity coalPipingEntity;
    private Date now;

    public PipingGetSingleDataThread() { }
    public PipingGetSingleDataThread(CoalPipingEntity coalPipingEntity, Date now) {
        this.coalPipingEntity = coalPipingEntity;
        this.now = now;
    }

    public void run() { updateData("url"); }

    /**
     * 更新数据,，返回需要更新的对象【已经更新保存到数据库】
     * 更新coalPipingEntity实时数据
     */
    public void updateData(final String url) {
//        CoalPipingSetEntity setEntity = coalPipingEntity.getCoalPipingSetEntity();
//        String url = coalPipingEntity.getCoalPipingSetEntity().getsUrl();
        // 直接读取数据库中的存储的URL地址
//        String url = "http://192.168.1.43:8080/windJsonProject/json.jsp";
        //01、获取设备的jsonp数据，返回一个JsonArray对象
        JSONArray jo = HandleDeviceData.httpRequest(url, "GET");
        //02、jsonArray对象转换为 中间对象“windDataPojo"
        WindDataPojo windDataPojo = new WindDataPojo(jo);
        //03、更新 coalPipingEntity对象的风速与密度
        coalPipingEntity = WindPojoToEntityUtil.updateRealData(now.getTime(),coalPipingEntity,windDataPojo);

        //04、数据库更新coalPipingEntity对象
//        coalPipingRepository.saveAndFlush(coalPipingEntity);
    }


    public CoalPipingEntity getCoalPipingEntity() { return coalPipingEntity; }

    public void setCoalPipingEntity(CoalPipingEntity coalPipingEntity) { this.coalPipingEntity = coalPipingEntity; }

    public Date getNow() { return now; }

    public void setNow(Date now) { this.now = now; }
}
