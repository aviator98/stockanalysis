package cn.stockAnalysis.service.strategy;

import java.util.List;
import cn.stockAnalysis.vo.TradeCal;

public class LowestStrategy implements FilterStrategy {

	@Override
	public List<TradeCal> filter(List<TradeCal> sourceList) {
		return sourceList;
	}
}