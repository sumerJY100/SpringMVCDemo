package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H119Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H119Rep extends JpaRepository<H119Pojo,Long> {
   List<H119Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
