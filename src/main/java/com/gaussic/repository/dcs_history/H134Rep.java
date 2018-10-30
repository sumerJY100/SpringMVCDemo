package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H134Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H134Rep extends JpaRepository<H134Pojo,Long> {
   List<H134Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
