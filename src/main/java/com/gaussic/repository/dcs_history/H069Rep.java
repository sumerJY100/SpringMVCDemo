package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H069Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H069Rep extends JpaRepository<H069Pojo,Long> {
   List<H069Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
