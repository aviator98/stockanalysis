package cn.stockAnalysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.stockAnalysis.domain.SplitHistory;

public interface SplitHistoryRepository extends JpaRepository<SplitHistory, String> {

}
