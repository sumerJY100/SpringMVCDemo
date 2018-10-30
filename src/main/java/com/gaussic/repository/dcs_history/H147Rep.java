package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H147Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H147Rep extends JpaRepository<H147Pojo,Long> {
   List<H147Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
