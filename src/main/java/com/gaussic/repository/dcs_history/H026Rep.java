package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H026Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H026Rep extends JpaRepository<H026Pojo,Long> {
   List<H026Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
