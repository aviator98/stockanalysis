package cn.stockanalysis.service.strategy;

public interface FilterStrategyFactory {

	public FilterStrategy createInstance(String strategy);

}