package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H047Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H047Rep extends JpaRepository<H047Pojo,Long> {
   List<H047Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
