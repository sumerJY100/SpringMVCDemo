package com.gaussic.dataGet;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.CoalPipingSetEntity;
import com.gaussic.model.history.DcoalPipingHistoryEntity;
import com.gaussic.repository.CoalPipingHistoryRepositoryC;
import com.gaussic.repository.CoalPipingRepository;
import com.gaussic.util.HandleDeviceData;
import com.gaussic.util.WindPojoToEntityUtil;
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

    public void run() { updateData(); }

    /**
     * 更新数据
     * 更新coalPipingEntity实时数据
     * 新增coalPipingHistoryEntity数据
     */
    public void updateData() {
        CoalPipingSetEntity setEntity = coalPipingEntity.getCoalPipingSetEntity();
//        String url = setEntity.getsUrl() ;
        //TODO 每个对象的地址需要设置
        String url = "http://192.168.1.41:8080/windJsonProject/json.jsp";
        JSONArray jo = HandleDeviceData.httpRequest(url, "GET");
        WindDataPojo windDataPojo = new WindDataPojo(jo);
        WindPojoToEntityUtil.updateRealData(now.getTime(),coalPipingEntity,windDataPojo);
        //更新coalPipingEntity对象
        coalPipingRepository.saveAndFlush(coalPipingEntity);

    }


    public CoalPipingEntity getCoalPipingEntity() { return coalPipingEntity; }

    public void setCoalPipingEntity(CoalPipingEntity coalPipingEntity) { this.coalPipingEntity = coalPipingEntity; }

    public Date getNow() { return now; }

    public void setNow(Date now) { this.now = now; }
}
