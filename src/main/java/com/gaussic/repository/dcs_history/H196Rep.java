package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H196Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H196Rep extends JpaRepository<H196Pojo,Long> {
   List<H196Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
