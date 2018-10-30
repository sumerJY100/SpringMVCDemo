package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H142Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H142Rep extends JpaRepository<H142Pojo,Long> {
   List<H142Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
