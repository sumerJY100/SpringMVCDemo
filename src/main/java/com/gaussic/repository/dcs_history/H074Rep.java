package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H074Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H074Rep extends JpaRepository<H074Pojo,Long> {
   List<H074Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
