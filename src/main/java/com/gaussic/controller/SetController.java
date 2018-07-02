package com.gaussic.controller;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.CoalPipingSetEntity;
import com.gaussic.repository.CoalMillPipingSetRepository;
import com.gaussic.repository.CoalMillRepository;
import com.gaussic.repository.CoalPipingRepository;
import com.gaussic.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SetController {

    @Autowired
    private SetService setService;

    @RequestMapping(value = "set/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        List<CoalMillEntity> coalMillEntityList = setService.findAllCoalMillEntity();
        modelMap.put("coalMillList", coalMillEntityList);
        return "setPages/index";
    }
}
