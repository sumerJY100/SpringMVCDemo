package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H157Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H157Rep extends JpaRepository<H157Pojo,Long> {
   List<H157Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
