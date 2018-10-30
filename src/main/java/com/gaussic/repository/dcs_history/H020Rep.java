package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H020Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H020Rep extends JpaRepository<H020Pojo,Long> {
   List<H020Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
