package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H152Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H152Rep extends JpaRepository<H152Pojo,Long> {
   List<H152Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
