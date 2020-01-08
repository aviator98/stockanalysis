package cn.stockanalysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.stockanalysis.domain.TradeRecord;

public interface TradeRecordRepository extends JpaRepository<TradeRecord, String> {

}