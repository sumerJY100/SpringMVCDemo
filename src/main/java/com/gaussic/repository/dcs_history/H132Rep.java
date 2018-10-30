package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H132Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H132Rep extends JpaRepository<H132Pojo,Long> {
   List<H132Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
