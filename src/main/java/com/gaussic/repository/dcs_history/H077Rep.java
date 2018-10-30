package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H077Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H077Rep extends JpaRepository<H077Pojo,Long> {
   List<H077Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
