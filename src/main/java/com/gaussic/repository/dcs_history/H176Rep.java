package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H176Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H176Rep extends JpaRepository<H176Pojo,Long> {
   List<H176Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
