package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H063Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H063Rep extends JpaRepository<H063Pojo,Long> {
   List<H063Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
