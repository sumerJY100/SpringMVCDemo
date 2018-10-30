package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H181Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H181Rep extends JpaRepository<H181Pojo,Long> {
   List<H181Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
