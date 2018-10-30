package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H067Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H067Rep extends JpaRepository<H067Pojo,Long> {
   List<H067Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
