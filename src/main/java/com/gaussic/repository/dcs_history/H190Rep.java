package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H190Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H190Rep extends JpaRepository<H190Pojo,Long> {
   List<H190Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
