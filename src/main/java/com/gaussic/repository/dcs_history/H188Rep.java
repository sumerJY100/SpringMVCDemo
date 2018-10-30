package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H188Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H188Rep extends JpaRepository<H188Pojo,Long> {
   List<H188Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
