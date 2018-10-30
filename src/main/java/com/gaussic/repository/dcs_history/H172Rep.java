package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H172Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H172Rep extends JpaRepository<H172Pojo,Long> {
   List<H172Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
