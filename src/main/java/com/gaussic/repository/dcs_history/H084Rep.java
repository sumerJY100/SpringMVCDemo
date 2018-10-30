package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H084Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H084Rep extends JpaRepository<H084Pojo,Long> {
   List<H084Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
