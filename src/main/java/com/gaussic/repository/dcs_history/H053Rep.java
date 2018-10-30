package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H053Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H053Rep extends JpaRepository<H053Pojo,Long> {
   List<H053Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
