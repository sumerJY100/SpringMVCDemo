package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H167Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H167Rep extends JpaRepository<H167Pojo,Long> {
   List<H167Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
