package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H180Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H180Rep extends JpaRepository<H180Pojo,Long> {
   List<H180Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
