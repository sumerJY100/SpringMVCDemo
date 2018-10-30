package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H052Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H052Rep extends JpaRepository<H052Pojo,Long> {
   List<H052Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
