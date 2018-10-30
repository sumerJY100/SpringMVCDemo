package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H199Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H199Rep extends JpaRepository<H199Pojo,Long> {
   List<H199Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
