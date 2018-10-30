package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H072Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H072Rep extends JpaRepository<H072Pojo,Long> {
   List<H072Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
