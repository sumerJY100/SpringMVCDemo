package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H054Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H054Rep extends JpaRepository<H054Pojo,Long> {
   List<H054Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
