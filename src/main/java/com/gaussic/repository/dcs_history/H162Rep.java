package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H162Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H162Rep extends JpaRepository<H162Pojo,Long> {
   List<H162Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
