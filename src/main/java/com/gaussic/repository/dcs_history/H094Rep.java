package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H094Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H094Rep extends JpaRepository<H094Pojo,Long> {
   List<H094Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
