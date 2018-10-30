package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H048Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H048Rep extends JpaRepository<H048Pojo,Long> {
   List<H048Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
