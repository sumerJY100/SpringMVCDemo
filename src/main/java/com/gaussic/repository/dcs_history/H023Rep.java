package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H023Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H023Rep extends JpaRepository<H023Pojo,Long> {
   List<H023Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
