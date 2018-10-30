package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H061Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H061Rep extends JpaRepository<H061Pojo,Long> {
   List<H061Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
