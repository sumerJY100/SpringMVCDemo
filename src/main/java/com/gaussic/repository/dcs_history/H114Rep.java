package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H114Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H114Rep extends JpaRepository<H114Pojo,Long> {
   List<H114Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
