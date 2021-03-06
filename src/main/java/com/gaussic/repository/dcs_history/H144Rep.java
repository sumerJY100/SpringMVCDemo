package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H144Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H144Rep extends JpaRepository<H144Pojo,Long> {
   List<H144Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
