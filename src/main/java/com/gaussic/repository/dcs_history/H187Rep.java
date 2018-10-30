package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H187Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H187Rep extends JpaRepository<H187Pojo,Long> {
   List<H187Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
