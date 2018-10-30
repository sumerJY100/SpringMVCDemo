package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H161Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H161Rep extends JpaRepository<H161Pojo,Long> {
   List<H161Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
