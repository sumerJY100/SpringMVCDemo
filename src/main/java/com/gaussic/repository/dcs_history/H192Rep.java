package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H192Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H192Rep extends JpaRepository<H192Pojo,Long> {
   List<H192Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
