package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H185Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H185Rep extends JpaRepository<H185Pojo,Long> {
   List<H185Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
