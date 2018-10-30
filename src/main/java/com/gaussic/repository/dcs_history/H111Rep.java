package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H111Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H111Rep extends JpaRepository<H111Pojo,Long> {
   List<H111Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
