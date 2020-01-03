package cn.stockAnalysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.stockAnalysis.domain.TradeRecord;

public interface TradeRecordRepository extends JpaRepository<TradeRecord, String> {

}