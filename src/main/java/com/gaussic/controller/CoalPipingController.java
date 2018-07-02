package com.gaussic.controller;

import com.gaussic.model.CoalPipingEntity;
import com.gaussic.repository.CoalPipingRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CoalPipingController {
    @Autowired
    private CoalPipingRepository coalPipingRepository;

    @RequestMapping(value = "/getRealTimeData", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getRealTimeData(){
        CoalPipingEntity coalPipingEntity = coalPipingRepository.findOne(11L);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("density",coalPipingEntity.getpDencity());
        jsonObject.put("velocity",coalPipingEntity.getpVelocity());
        return jsonObject.toString();
    }
}
