package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H141Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H141Rep extends JpaRepository<H141Pojo,Long> {
   List<H141Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
