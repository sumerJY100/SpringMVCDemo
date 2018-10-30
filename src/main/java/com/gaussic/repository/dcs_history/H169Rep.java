package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H169Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H169Rep extends JpaRepository<H169Pojo,Long> {
   List<H169Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
