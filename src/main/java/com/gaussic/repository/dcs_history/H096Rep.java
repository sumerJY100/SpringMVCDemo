package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H096Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H096Rep extends JpaRepository<H096Pojo,Long> {
   List<H096Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
