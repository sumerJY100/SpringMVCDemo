package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H002Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H002Rep extends JpaRepository<H002Pojo,Long> {
   List<H002Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
