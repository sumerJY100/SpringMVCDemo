package com.gaussic.repository;

import com.gaussic.model.history.CcoalPipingHistoryEntity;
import com.gaussic.model.history.DcoalPipingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoalPipingHistoryRepositoryC extends JpaRepository<CcoalPipingHistoryEntity, Long> {

}
