package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H145Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H145Rep extends JpaRepository<H145Pojo,Long> {
   List<H145Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
