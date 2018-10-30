package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H182Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H182Rep extends JpaRepository<H182Pojo,Long> {
   List<H182Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
