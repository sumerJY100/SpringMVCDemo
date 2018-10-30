package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H088Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H088Rep extends JpaRepository<H088Pojo,Long> {
   List<H088Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
