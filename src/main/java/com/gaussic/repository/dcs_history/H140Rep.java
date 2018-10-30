package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H140Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H140Rep extends JpaRepository<H140Pojo,Long> {
   List<H140Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
