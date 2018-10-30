package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H151Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H151Rep extends JpaRepository<H151Pojo,Long> {
   List<H151Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
