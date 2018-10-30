package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H079Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H079Rep extends JpaRepository<H079Pojo,Long> {
   List<H079Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
