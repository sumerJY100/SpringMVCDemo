package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H178Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H178Rep extends JpaRepository<H178Pojo,Long> {
   List<H178Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
