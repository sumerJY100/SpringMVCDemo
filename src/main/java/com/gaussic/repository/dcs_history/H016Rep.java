package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H016Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H016Rep extends JpaRepository<H016Pojo,Long> {
   List<H016Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
