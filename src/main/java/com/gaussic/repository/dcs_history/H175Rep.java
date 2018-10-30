package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H175Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H175Rep extends JpaRepository<H175Pojo,Long> {
   List<H175Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
