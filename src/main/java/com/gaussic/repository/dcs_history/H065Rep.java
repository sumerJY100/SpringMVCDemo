package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H065Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H065Rep extends JpaRepository<H065Pojo,Long> {
   List<H065Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
