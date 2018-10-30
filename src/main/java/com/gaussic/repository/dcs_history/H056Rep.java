package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H056Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H056Rep extends JpaRepository<H056Pojo,Long> {
   List<H056Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
