package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H058Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H058Rep extends JpaRepository<H058Pojo,Long> {
   List<H058Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
