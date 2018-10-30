package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H160Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H160Rep extends JpaRepository<H160Pojo,Long> {
   List<H160Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
