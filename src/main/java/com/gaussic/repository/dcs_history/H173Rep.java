package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H173Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H173Rep extends JpaRepository<H173Pojo,Long> {
   List<H173Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
