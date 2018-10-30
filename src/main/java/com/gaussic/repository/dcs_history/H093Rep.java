package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H093Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H093Rep extends JpaRepository<H093Pojo,Long> {
   List<H093Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
