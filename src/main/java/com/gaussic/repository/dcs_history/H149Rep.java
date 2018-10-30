package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H149Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H149Rep extends JpaRepository<H149Pojo,Long> {
   List<H149Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
