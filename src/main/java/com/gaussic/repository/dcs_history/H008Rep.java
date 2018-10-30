package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H008Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H008Rep extends JpaRepository<H008Pojo,Long> {
   List<H008Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
