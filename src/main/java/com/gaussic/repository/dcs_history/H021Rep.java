package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H021Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H021Rep extends JpaRepository<H021Pojo,Long> {
   List<H021Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
