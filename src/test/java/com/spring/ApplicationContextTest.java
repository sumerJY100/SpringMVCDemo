package com.spring;

import com.gaussic.model.dcs_history.DcsHistoryCashePojo;
import com.gaussic.repository.dcs_history.DcsHistoryCasheRep;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ApplicationContextTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src\\main\\webapp\\WEB-INF\\mvc-dispatcher-servlet.xml");
//        applicationContext.getBean("alarmHistoryRepository");

        DcsHistoryCasheRep rep = (DcsHistoryCasheRep) applicationContext.getBean("dcsHistoryCasheRep");
        String str = "1";
        int offset = 1;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        DcsHistoryCashePojo dcsHistoryCashePojo = new DcsHistoryCashePojo();
        dcsHistoryCashePojo.setValues(str);
        dcsHistoryCashePojo.setvOffset(offset);
        dcsHistoryCashePojo.setvTime(timestamp);
        System.out.println(str.length());
        rep.saveAndFlush(dcsHistoryCashePojo);
    }
}
