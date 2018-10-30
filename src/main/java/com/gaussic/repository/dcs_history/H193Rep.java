package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H193Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H193Rep extends JpaRepository<H193Pojo,Long> {
   List<H193Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
