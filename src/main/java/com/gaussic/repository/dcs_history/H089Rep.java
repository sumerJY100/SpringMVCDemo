package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H089Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H089Rep extends JpaRepository<H089Pojo,Long> {
   List<H089Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
