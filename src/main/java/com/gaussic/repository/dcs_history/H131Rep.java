package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H131Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H131Rep extends JpaRepository<H131Pojo,Long> {
   List<H131Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
