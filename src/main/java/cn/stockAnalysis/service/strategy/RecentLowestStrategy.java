package cn.stockAnalysis.service.strategy;

import java.time.Period;
import java.util.*;

import cn.stockAnalysis.vo.TradeCal;

public class RecentLowestStrategy implements FilterStrategy {

	@Override
	public List<TradeCal> filter(List<TradeCal> sourceList) {
		List<TradeCal> newList = new ArrayList<TradeCal>();
		for (TradeCal tc : sourceList) {
			int daysNum = Period.between(tc.getLowestDate(), tc.getLastestDate()).getDays();
			if (daysNum < 3) {
				newList.add(tc);
			}
		}
		return newList;
	}
}
