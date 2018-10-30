package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H038Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H038Rep extends JpaRepository<H038Pojo,Long> {
   List<H038Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
