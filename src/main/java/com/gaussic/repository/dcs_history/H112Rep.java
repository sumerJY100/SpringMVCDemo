package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H112Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H112Rep extends JpaRepository<H112Pojo,Long> {
   List<H112Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
