package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H117Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H117Rep extends JpaRepository<H117Pojo,Long> {
   List<H117Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
