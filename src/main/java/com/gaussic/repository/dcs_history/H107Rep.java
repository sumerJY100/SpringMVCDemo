package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H107Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H107Rep extends JpaRepository<H107Pojo,Long> {
   List<H107Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
