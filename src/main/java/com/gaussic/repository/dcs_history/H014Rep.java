package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H014Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H014Rep extends JpaRepository<H014Pojo,Long> {
   List<H014Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
