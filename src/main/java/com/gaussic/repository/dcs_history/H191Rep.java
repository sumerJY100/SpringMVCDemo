package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H191Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H191Rep extends JpaRepository<H191Pojo,Long> {
   List<H191Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
