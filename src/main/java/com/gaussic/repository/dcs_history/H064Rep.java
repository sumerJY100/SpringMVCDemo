package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H064Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H064Rep extends JpaRepository<H064Pojo,Long> {
   List<H064Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
