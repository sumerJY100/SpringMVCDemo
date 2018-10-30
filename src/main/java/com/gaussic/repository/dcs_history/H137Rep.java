package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H137Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H137Rep extends JpaRepository<H137Pojo,Long> {
   List<H137Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
