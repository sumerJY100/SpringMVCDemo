package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H106Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H106Rep extends JpaRepository<H106Pojo,Long> {
   List<H106Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
