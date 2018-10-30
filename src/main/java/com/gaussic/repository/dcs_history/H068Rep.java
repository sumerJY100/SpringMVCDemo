package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H068Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H068Rep extends JpaRepository<H068Pojo,Long> {
   List<H068Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
