package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H010Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H010Rep extends JpaRepository<H010Pojo,Long> {
   List<H010Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
