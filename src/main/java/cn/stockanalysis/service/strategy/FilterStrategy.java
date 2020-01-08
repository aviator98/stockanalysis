package cn.stockanalysis.service.strategy;

import java.util.List;

import cn.stockanalysis.vo.TradeCal;

public interface FilterStrategy {
	
	public List<TradeCal> filter(List<TradeCal> sourceList);

}