package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H027Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H027Rep extends JpaRepository<H027Pojo,Long> {
   List<H027Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
