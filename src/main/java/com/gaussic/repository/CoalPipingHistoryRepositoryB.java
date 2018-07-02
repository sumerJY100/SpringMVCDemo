package com.gaussic.repository;

import com.gaussic.model.history.AcoalPipingHistoryEntity;
import com.gaussic.model.history.BcoalPipingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoalPipingHistoryRepositoryB extends JpaRepository<BcoalPipingHistoryEntity,Long> {
}
