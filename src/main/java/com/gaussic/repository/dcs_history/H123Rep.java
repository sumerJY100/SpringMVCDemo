package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H123Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H123Rep extends JpaRepository<H123Pojo,Long> {
   List<H123Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
