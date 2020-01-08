package cn.stockanalysis.service.strategy;

import java.util.HashMap;
import java.util.Map;

public class DefaultFilterStrategyFactory implements FilterStrategyFactory {

	private Map<String, FilterStrategy> strategyMap = new HashMap<String, FilterStrategy>();

	@Override
	public FilterStrategy createInstance(String strategy) {
		FilterStrategy fs = (FilterStrategy) strategyMap.get(strategy);
		if (fs == null) {
			try {
				String className = "cn.stockanalysis.service.strategy."+strategy+"Strategy";
				Class<?> c = Class.forName(className);
				fs = (FilterStrategy) c.newInstance();
				strategyMap.put(strategy, fs);
			} catch(ClassNotFoundException e) {
				fs = (FilterStrategy) strategyMap.get("Default");
				if (fs == null) {
					fs = new DefaultStrategy();
					strategyMap.put("Default", fs);
				}
			} catch(Exception e) {
				fs = (FilterStrategy) strategyMap.get("Default");
				if (fs == null) {
					fs = new DefaultStrategy();
					strategyMap.put("Default", fs);
				}				
			}
		}
		return fs;
	}
}