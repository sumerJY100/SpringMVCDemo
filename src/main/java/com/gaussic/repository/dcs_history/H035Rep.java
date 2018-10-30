package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H035Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H035Rep extends JpaRepository<H035Pojo,Long> {
   List<H035Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
