package com.gaussic.controller;

import com.gaussic.model.AlarmHistoryEntity;
import com.gaussic.repository.AlarmHistoryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class AlarmHistoryController {
    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;

    @RequestMapping(value = "getAlarmHistoryPaging", method = RequestMethod.GET)
    @ResponseBody
    public String getAlarmHistoryPaging(@RequestParam(value = "limit", required = false) int limit, @RequestParam
            (value = "offset", required = false) int offset) {

//        List<AlarmHistoryEntity> alarmHistoryEntityList = alarmHistoryRepository.findAlarmHistoryByPage(limit,offset);
        Pageable pageable = new PageRequest(offset, limit);
        Page<AlarmHistoryEntity> alarmHistoryEntityPage = alarmHistoryRepository.findAll(pageable);
        long totalElements = alarmHistoryEntityPage.getTotalElements();
        long totalPages = alarmHistoryEntityPage.getTotalPages();
        List<AlarmHistoryEntity> list = alarmHistoryEntityPage.getContent();
        System.out.println("+++ " + list.get(0).getaAlarmTime());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", totalElements);
//        jsonObject.put("rows",totalPages);
        JSONArray jsonArray = new JSONArray();
        for (AlarmHistoryEntity a : list) {
            LocalDateTime localDateTime = a.getaAlarmTime().toLocalDateTime();
            LocalDate localDate = localDateTime.toLocalDate();
            LocalTime localTime = localDateTime.toLocalTime();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", a.getId());
            jsonObject1.put("alarmDate", localDate);
            jsonObject1.put("alarmTime", localTime);
            jsonArray.put(jsonObject1);

        }
        jsonObject.put("rows", jsonArray);
        return jsonObject.toString();
    }
}
