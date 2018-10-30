package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H041Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H041Rep extends JpaRepository<H041Pojo,Long> {
   List<H041Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
