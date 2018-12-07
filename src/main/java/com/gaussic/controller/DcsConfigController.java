package com.gaussic.controller;

import com.gaussic.model.AlarmHistoryEntity;
import com.gaussic.model.dcs.DeviceDcsPojo;
import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcsRemote.DcsRemotePointPojo;
import com.gaussic.repository.*;
import com.gaussic.service.DcsService;
import com.serotonin.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DcsConfigController
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/6 9:27
 * @Version 1.0
 */
@Controller
public class DcsConfigController {

    @Autowired
    private DcsRemotePointRepository dcsRemotePointRepository;
    @Autowired
    private DeviceDcsRepository deviceDcsRepository;
    @Autowired
    private DevicePointRepository devicePointRepository;
    @Autowired
    private DevicePointHistory1Repository devicePointHistory1Repository;
    @Autowired
    private DevicePointHistory2Repository devicePointHistory2Repository;
    @Autowired
    private DevicePointHistory3Repository devicePointHistory3Repository;
    @Autowired
    private DevicePointHistory4Repository devicePointHistory4Repository;
    @Autowired
    private DevicePointRealtimeRepository devicePointRealtimeRepository;

    @Autowired
    private DcsService dcsService;

    /**
     * @Description dcs配置页面【查询】
     * @Author jiangyong xia
     * @Date 9:31 18/9/6
     * @Param
     * @return
     **/
    @RequestMapping(value="dcsConfigIndex",method= RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dcsConfigIndex(){
        StringBuffer buffer = new StringBuffer();
        JSONObject resultJsonObject = new JSONObject();
        resultJsonObject.put("total",1);

        JSONArray jsonArray = new JSONArray();
        DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1L);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceId",deviceDcsPojo.getDeviceId());
        jsonObject.put("deviceName",deviceDcsPojo.getDeviceName());
        jsonObject.put("deviceAddress",deviceDcsPojo.getDeviceAddress());


        jsonObject.put("devicePort",deviceDcsPojo.getDevicePort());
        jsonObject.put("deviceBoundRate",deviceDcsPojo.getDeviceBoundRate());
        jsonObject.put("deviceLinkState",deviceDcsPojo.getDeviceLinkStateString());
        jsonObject.put("deviceNote",deviceDcsPojo.getDeviceNote());
        jsonObject.put("deviceNum",deviceDcsPojo.getDeviceNum());
        jsonObject.put("deviceFlowControlIn",deviceDcsPojo.getDeviceFlowControlIn());
        jsonObject.put("deviceFlowControlOut",deviceDcsPojo.getDeviceFlowControlOut());
        jsonObject.put("deviceDataBits",deviceDcsPojo.getDeviceDataBits());
        jsonObject.put("deviceStopBits",deviceDcsPojo.getDeviceStopBits());
        jsonObject.put("deviceParity",deviceDcsPojo.getDeviceParityString());
        jsonArray.put(jsonObject);

        resultJsonObject.put("rows",jsonArray);
        return resultJsonObject.toString();
    }
    /**
     * @Description dcs修改配置【修改】
     * @Author jiangyong xia
     * @Date 9:33 18/9/6
     * @Param
     * @return
     **/
    @RequestMapping(value="dcsConfigUpdate",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dcsConfigUpdate(DeviceDcsPojo deviceDcsPojo){
        StringBuffer buffer = new StringBuffer();
        System.out.println("deviceDcsPojo:" + deviceDcsPojo);
        buffer.append("success");
        JSONObject jsonObject = new JSONObject();

        dcsService.saveDeviceDcsWithMainAttr(deviceDcsPojo);

        jsonObject.put("data","success");
        return jsonObject.toString();
    }
    /**
     * @Description 远端获取DCS数据，配置【查询】
     * @Author jiangyong xia
     * @Date 9:37 18/9/6
     * @Param
     * @return
     **/
    @RequestMapping(value="remoteGetDataIndex" ,method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String remoteGetDataIndex(@RequestParam(value = "limit", required = false) int limit, @RequestParam
            (value = "offset", required = false) int offset){
        int page = offset/limit;
        Sort sort = new Sort(Sort.Direction.ASC,"pointHistoryDeviceTableName");
        Pageable pageable = new PageRequest(page, limit,sort);
        String pointNameNotLike = "";
        Specification<DevicePointPojo> specification = new Specification<DevicePointPojo>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> $PointName = root.get("pointName");
                Predicate key = criteriaBuilder.notEqual($PointName, "" + pointNameNotLike + "");

                return criteriaBuilder.and(key);
            }
        };
        Page<DevicePointPojo> devicePointPojoPage = devicePointRepository.findAll(specification, pageable);
        long totalElements = devicePointPojoPage.getTotalElements();
        long totalPages = devicePointPojoPage.getTotalPages();
        List<DevicePointPojo> devicePointPojoList = devicePointPojoPage.getContent();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", totalElements);
        try {
//            List<DevicePointPojo> devicePointPojoList = devicePointRepository.findByPointNameNotLike("");
            System.out.println("devicePointPojoList:" + devicePointPojoList.size() + ",totalElements:" + totalElements);
            if (null != devicePointPojoList && devicePointPojoList.size() > 0) {

//                jsonObject.put("total", devicePointPojoList.size());
                JSONArray jsonArray = new JSONArray();
                int count = 1;
//                devicePointPojoList.forEach((p) -> {
                for(int i=0;i<devicePointPojoList.size();i++){
                    DevicePointPojo p = devicePointPojoList.get(i);
                    if(null == p.getPointName()){
                        System.out.println("---------------------");
                    }
                    JSONObject jo = new JSONObject();
                    jo.put("index",i+1+offset);
                    jo.put("pointId", p.getPointId());
                    jo.put("dcsId", p.getDeviceDcsPojo().getDeviceId());
                    jo.put("dcsName", p.getDeviceDcsPojo().getDeviceName());

                    jo.put("pointName", p.getPointName());
                    jo.put("pointAddress", p.getPointAddress());
                    //数据类型参照 DataType的数据类型进行配置
                    jo.put("dataTyper", p.getDataTyper());
                    jo.put("pointNote", p.getPointNote());
                    jo.put("pointHistoryTableName", p.getPointHistoryDeviceTableName());
                    jo.put("pointHistoryColumnName", p.getPointHistoryColumnName());
                    jo.put("pointHistoryPropertyName", p.getPointHistoryPorpertyName());
                    jo.put("pointHistoryOffset", null==p.getPointHistoryOffset()?"":p.getPointHistoryOffset());
//                    jo.put("unit",p.getUnit());
                    if(null != p.getDevicePointRealtimePojo()) {
                        jo.put("realValue", p.getDevicePointRealtimePojo().getPointValue());
                        jo.put("realDate", p.getDevicePointRealtimePojo().getrTime());
                    }else{
                        jo.put("realData", "--");
                        jo.put("realTime", "--");
                    }
                    jsonArray.put(jo);
                }
                jsonObject.put("rows", jsonArray);
            }
//        System.out.println(jsonObject.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    /**
     * @Description 远端读取DCS数据，配置【修改】
     * @Author jiangyong xia
     * @Date 9:41 18/9/6
     * @Param
     * @return
     **/
    @RequestMapping(value="remoteUpdateGetDataIndex",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String remoteUpdateGetDataIndex(DevicePointPojo devicePointPojo){
        JSONObject jsonObject = new JSONObject();

        dcsService.saveDevicePointWithMainAttr(devicePointPojo);

        jsonObject.put("data","success");
        return jsonObject.toString();
    }
    /**
     * @Description 远端发送DCS数据，配置【查询】
     * @Author jiangyong xia
     * @Date 9:41 18/9/6
     * @Param
     * @return
     **/
    @RequestMapping(value="remoteSendDataIndex",method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String remoteSendDataIndex(@RequestParam(value = "limit", required = false) int limit, @RequestParam
            (value = "offset", required = false) int offset){

        int page = offset/limit;
        Sort sort = new Sort(Sort.Direction.ASC,"address");
        Pageable pageable = new PageRequest(page, limit,sort);
        Page<DcsRemotePointPojo> devicePointPojoPage = dcsRemotePointRepository.findAll(pageable);

        long totalElements = devicePointPojoPage.getTotalElements();
        long totalPages = devicePointPojoPage.getTotalPages();
        List<DcsRemotePointPojo> dcsRemotePointPojoList = devicePointPojoPage.getContent();
        System.out.println("dcsRemotePointPojoList:" + dcsRemotePointPojoList.size());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", totalElements);
        try {
//            List<DcsRemotePointPojo> dcsRemotePointPojoList = dcsRemotePointRepository.findAll();
            List<DcsRemotePointPojo> dcsRemotePointPojos = dcsRemotePointPojoList.stream().sorted(Comparator.comparing(
                    (DcsRemotePointPojo::getAddress)))
                    .collect(Collectors.toList());
//            jsonObject.put("total", dcsRemotePointPojos.size());
            JSONArray jsonArray = new JSONArray();
            for (int i=0;i<dcsRemotePointPojos.size();i++){
//            dcsRemotePointPojoList.forEach((p) -> {
                DcsRemotePointPojo p = dcsRemotePointPojos.get(i);
                JSONObject jsonObjectTemp = new JSONObject();

                jsonObjectTemp.put("index", i+1+offset);
                jsonObjectTemp.put("dcsRemotePointId", p.getDcsRemotePointId());
                jsonObjectTemp.put("remotePointName", p.getRemotePointName());
                jsonObjectTemp.put("dcsId", p.getDeviceDcsPojo().getDeviceId());
                jsonObjectTemp.put("dcsName", p.getDeviceDcsPojo().getDeviceName());
                jsonObjectTemp.put("address", p.getAddress());
                jsonObjectTemp.put("currentDate", p.getCurrTime().toLocalDateTime().toLocalDate());
                jsonObjectTemp.put("currentTime", p.getCurrTime().toLocalDateTime().toLocalTime());
                jsonObjectTemp.put("currentValue", p.getCurrentValue());
                jsonObjectTemp.put("note", p.getNote() == null ? "" : p.getNote());
                jsonObjectTemp.put("pipeId", p.getCoalPipingEntity().getId());
                jsonObjectTemp.put("pipeName", p.getCoalPipingEntity().getpNameUserDefined());
                jsonObjectTemp.put("densityOrVelocity", p.getDensityOrVelocity()==1?"浓度":"风速");
                jsonObjectTemp.put("slaveId", p.getSlaveId());

//            jsonObjectTemp.put("devcieName",p.getDevice)
                jsonArray.put(jsonObjectTemp);
            }
            jsonObject.put("rows", jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    /**
     * @Description 远端发送DCS数据，配置【修改】
     * @Author jiangyong xia
     * @Date 9:42 18/9/6
     * @Param
     * @return
     **/
    @RequestMapping(value="remoteUpdateSendDataIndex",method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String remoteUpdateSendDataIndex(DcsRemotePointPojo dcsRemotePointPojo){
        JSONObject jsonObject = new JSONObject();

        dcsService.saveDevicePointWithMainAttr(dcsRemotePointPojo);

        jsonObject.put("data","success");
        return jsonObject.toString();
    }
}
