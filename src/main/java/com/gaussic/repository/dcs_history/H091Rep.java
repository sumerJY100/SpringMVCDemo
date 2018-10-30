package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H091Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H091Rep extends JpaRepository<H091Pojo,Long> {
   List<H091Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
