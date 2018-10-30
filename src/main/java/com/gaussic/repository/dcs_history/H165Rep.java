package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H165Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H165Rep extends JpaRepository<H165Pojo,Long> {
   List<H165Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
