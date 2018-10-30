package com.gaussic.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Component
public class DevicePointRealtimeService {
    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public  void batchUpdate(List list){
        for(int i=0;i<list.size();i++){
            entityManager.persist(list.get(i));
        }
        entityManager.flush();
        entityManager.clear();
    }
}
