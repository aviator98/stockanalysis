package cn.stockanalysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.stockanalysis.domain.SplitHistory;

public interface SplitHistoryRepository extends JpaRepository<SplitHistory, String> {

}
