package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H076Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H076Rep extends JpaRepository<H076Pojo,Long> {
   List<H076Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
