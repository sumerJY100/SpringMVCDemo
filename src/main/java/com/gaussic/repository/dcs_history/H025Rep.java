package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H025Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H025Rep extends JpaRepository<H025Pojo,Long> {
   List<H025Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
