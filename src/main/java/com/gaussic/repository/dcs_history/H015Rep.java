package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H015Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H015Rep extends JpaRepository<H015Pojo,Long> {
   List<H015Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
