package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H139Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H139Rep extends JpaRepository<H139Pojo,Long> {
   List<H139Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
