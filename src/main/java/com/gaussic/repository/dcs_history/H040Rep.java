package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H040Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H040Rep extends JpaRepository<H040Pojo,Long> {
   List<H040Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
