package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H164Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H164Rep extends JpaRepository<H164Pojo,Long> {
   List<H164Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
