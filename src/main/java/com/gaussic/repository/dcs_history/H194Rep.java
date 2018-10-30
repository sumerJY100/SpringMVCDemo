package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H194Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H194Rep extends JpaRepository<H194Pojo,Long> {
   List<H194Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
