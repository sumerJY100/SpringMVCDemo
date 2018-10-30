package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H174Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H174Rep extends JpaRepository<H174Pojo,Long> {
   List<H174Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
