package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H033Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H033Rep extends JpaRepository<H033Pojo,Long> {
   List<H033Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
