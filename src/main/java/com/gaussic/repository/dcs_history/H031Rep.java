package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H031Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H031Rep extends JpaRepository<H031Pojo,Long> {
   List<H031Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
