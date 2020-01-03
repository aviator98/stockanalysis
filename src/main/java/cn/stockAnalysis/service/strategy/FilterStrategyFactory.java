package cn.stockAnalysis.service.strategy;

public interface FilterStrategyFactory {

	public FilterStrategy createInstance(String strategy);

}