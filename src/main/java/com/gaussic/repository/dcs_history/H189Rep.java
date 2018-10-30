package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H189Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H189Rep extends JpaRepository<H189Pojo,Long> {
   List<H189Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
