package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H116Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H116Rep extends JpaRepository<H116Pojo,Long> {
   List<H116Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
