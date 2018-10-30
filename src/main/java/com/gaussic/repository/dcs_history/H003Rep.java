package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H003Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H003Rep extends JpaRepository<H003Pojo,Long> {
   List<H003Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
