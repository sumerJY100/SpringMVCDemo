package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H066Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H066Rep extends JpaRepository<H066Pojo,Long> {
   List<H066Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
