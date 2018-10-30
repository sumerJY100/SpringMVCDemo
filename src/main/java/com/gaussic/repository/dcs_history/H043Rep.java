package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H043Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H043Rep extends JpaRepository<H043Pojo,Long> {
   List<H043Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
