package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H128Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H128Rep extends JpaRepository<H128Pojo,Long> {
   List<H128Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
