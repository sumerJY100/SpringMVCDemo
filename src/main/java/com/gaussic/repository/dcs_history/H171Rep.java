package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H171Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H171Rep extends JpaRepository<H171Pojo,Long> {
   List<H171Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
