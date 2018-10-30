package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H022Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H022Rep extends JpaRepository<H022Pojo,Long> {
   List<H022Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
