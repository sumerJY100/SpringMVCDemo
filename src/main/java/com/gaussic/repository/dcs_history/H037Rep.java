package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H037Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H037Rep extends JpaRepository<H037Pojo,Long> {
   List<H037Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
