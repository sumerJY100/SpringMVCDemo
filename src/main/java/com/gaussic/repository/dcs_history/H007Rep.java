package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H007Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H007Rep extends JpaRepository<H007Pojo,Long> {
   List<H007Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
