package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H098Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H098Rep extends JpaRepository<H098Pojo,Long> {
   List<H098Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
