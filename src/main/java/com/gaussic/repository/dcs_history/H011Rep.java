package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H011Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H011Rep extends JpaRepository<H011Pojo,Long> {
   List<H011Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
