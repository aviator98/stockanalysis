package cn.stockanalysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.stockanalysis.domain.DailyTrade;

public interface DailyTradeRepository extends JpaRepository<DailyTrade, String>, JpaSpecificationExecutor<DailyTrade> {

}
