package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H101Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H101Rep extends JpaRepository<H101Pojo,Long> {
   List<H101Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
