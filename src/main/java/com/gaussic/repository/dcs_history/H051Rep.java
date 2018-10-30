package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H051Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H051Rep extends JpaRepository<H051Pojo,Long> {
   List<H051Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
