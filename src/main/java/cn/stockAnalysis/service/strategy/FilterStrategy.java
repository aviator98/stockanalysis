package cn.stockAnalysis.service.strategy;

import java.util.List;

import cn.stockAnalysis.vo.TradeCal;

public interface FilterStrategy {
	
	public List<TradeCal> filter(List<TradeCal> sourceList);

}