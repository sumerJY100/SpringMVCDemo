package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H012Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H012Rep extends JpaRepository<H012Pojo,Long> {
   List<H012Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
