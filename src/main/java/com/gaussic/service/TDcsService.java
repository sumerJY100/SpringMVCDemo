package com.gaussic.service;

import com.gaussic.model.dcsTemp.*;
import com.gaussic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class TDcsService {
    public static final int BE_OFFSET = 1,
            A_NOX=2,
            B_NOX = 3,
            BE_TOTAL_COAL_OFFSET = 74,
            BE_TOTAL_FIRST_WIND = 6,
            BE_TOTAL_SECOND_WIND = 7,
            BE_TOTAL_THIRD_WIND = 9,
            AIR_LEFT = 73,
            CM1_COAL = 75,
            CM2_COAL = 76,
            CM3_COAL = 77,
            CM4_COAL = 78,
            CM1_WIND = 79,
            CM2_WIND = 80,
            CM3_WIND = 80,
            CM4_WIND = 82;

    @Autowired
    private TAirLeftRepository tAirLeftRepository;
    @Autowired
    private TBeFirstWindRepository tBeFirstWindRepository;
    @Autowired
    private TBeRepository tBeRepository;
    @Autowired
    private TBeSecondRepository tBeSecondRepository;
    @Autowired
    private TBeThirdRepository tBeThirdRepository;
    @Autowired
    private TBeToalCoalRepository tBeToalCoalRepository;
    @Autowired
    private TCm1CoalRepository tCm1CoalRepository;
    @Autowired
    private TCm2CoalRepository tCm2CoalRepository;
    @Autowired
    private TCm3CoalRepository tCm3CoalRepository;
    @Autowired
    private TCm4CoalRepository tCm4CoalRepository;
    @Autowired
    private TCm1WindRepository tCm1WindRepository;
    @Autowired
    private TCm2WindRepository tCm2WindRepository;
    @Autowired
    private TCm3WindRepository tCm3WindRepository;
    @Autowired
    private TCm4WindRepository tCm4WindRepository;
    @Autowired
    private TBeNOXARepository tBeNOXARepository;
    @Autowired
    private TBeNOXBRepository tBeNOXBRepository;

    public void saveDcsPoint(int offSet, int value) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        float v = Integer.valueOf(value).floatValue();

        if(offSet == BE_OFFSET){
            TBePojo t = new TBePojo();
            t.setvTime(timestamp);
            t.setV(v);
            tBeRepository.save(t);
        }else if(offSet == BE_TOTAL_COAL_OFFSET){
            TBeTotalCoalPojo t = new TBeTotalCoalPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tBeToalCoalRepository.save(t);
        }else if(offSet == BE_TOTAL_FIRST_WIND){
            TBeTotalFirstWindPojo t = new TBeTotalFirstWindPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tBeFirstWindRepository.save(t);
        }else if(offSet == BE_TOTAL_SECOND_WIND){
            TBeTotalSecondWindPojo t = new TBeTotalSecondWindPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tBeSecondRepository.save(t);
        }else if(offSet == BE_TOTAL_THIRD_WIND){
            TBeTotalThirdWindPojo t = new TBeTotalThirdWindPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tBeThirdRepository.save(t);
        }else if(offSet == AIR_LEFT){
            TAirLeftPojo t = new TAirLeftPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tAirLeftRepository.save(t);
        }else if(offSet == CM1_COAL){
            TCm1CoalPojo t = new TCm1CoalPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm1CoalRepository.save(t);
        }else if(offSet == CM2_COAL){
            TCm2CoalPojo t = new TCm2CoalPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm2CoalRepository.save(t);
        }else if(offSet == CM3_COAL){
            TCm3CoalPojo t = new TCm3CoalPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm3CoalRepository.save(t);
        }else if(offSet == CM4_COAL){
            TCm4CoalPojo t = new TCm4CoalPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm4CoalRepository.save(t);
        }else if(offSet == CM1_WIND){
            TCm1WindPojo t = new TCm1WindPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm1WindRepository.save(t);
        }else if(offSet == CM2_WIND){
            TCm2WindPojo t = new TCm2WindPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm2WindRepository.save(t);
        }else if(offSet == CM3_WIND){
            TCm3WindPojo t = new TCm3WindPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm3WindRepository.save(t);
        }else if(offSet == CM4_WIND){
            TCm4WindPojo t = new TCm4WindPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tCm4WindRepository.save(t);
        }else if(offSet == A_NOX){
            TBeNOXAPojo t = new TBeNOXAPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tBeNOXARepository.save(t);
        }else if(offSet == B_NOX){
            TBeNOXBPojo t = new TBeNOXBPojo();
            t.setvTime(timestamp);
            t.setV(v);
            tBeNOXBRepository.save(t);
        }

    }
}
