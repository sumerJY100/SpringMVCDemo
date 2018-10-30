package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H100Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H100Rep extends JpaRepository<H100Pojo,Long> {
   List<H100Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
