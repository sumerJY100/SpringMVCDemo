package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H097Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H097Rep extends JpaRepository<H097Pojo,Long> {
   List<H097Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
