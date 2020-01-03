package cn.stockAnalysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.stockAnalysis.domain.DailyTrade;

public interface DailyTradeRepository extends JpaRepository<DailyTrade, String>, JpaSpecificationExecutor<DailyTrade> {

}
