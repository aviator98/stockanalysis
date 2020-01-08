package cn.stockanalysis.vo;

import java.time.LocalDate;
import cn.stockanalysis.utils.StockUtil;

public class TradeCal implements CsvEntity {
	private String code;
	private String name;
	private String publicTime;
	private double lowestTclose;
	private LocalDate lowestDate;
	private double highestTclose;
	private LocalDate highestDate;
	private double beginTclose;
	private LocalDate beginDate;
	private double lastestTclose;
	private LocalDate lastestDate;
	private double maxRange;
	private double lastestRange;
	private double increaseRange;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}
	public double getLowestTclose() {
		return lowestTclose;
	}
	public void setLowestTclose(double lowestTclose) {
		this.lowestTclose = lowestTclose;
	}
	public LocalDate getLowestDate() {
		return lowestDate;
	}
	public void setLowestDate(LocalDate lowestDate) {
		this.lowestDate = lowestDate;
	}
	public double getHighestTclose() {
		return highestTclose;
	}
	public void setHighestTclose(double highestTclose) {
		this.highestTclose = highestTclose;
	}
	public LocalDate getHighestDate() {
		return highestDate;
	}
	public void setHighestDate(LocalDate highestDate) {
		this.highestDate = highestDate;
	}
	public double getBeginTclose() {
		return beginTclose;
	}
	public void setBeginTclose(double beginTclose) {
		this.beginTclose = beginTclose;
	}
	public LocalDate getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}
	public double getLastestTclose() {
		return lastestTclose;
	}
	public void setLastestTclose(double lastestTclose) {
		this.lastestTclose = lastestTclose;
	}
	public LocalDate getLastestDate() {
		return lastestDate;
	}
	public void setLastestDate(LocalDate lastestDate) {
		this.lastestDate = lastestDate;
	}
	public double getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(double maxRange) {
		this.maxRange = maxRange;
	}
	public double getLastestRange() {
		return lastestRange;
	}
	public void setLastestRange(double lastestRange) {
		this.lastestRange = lastestRange;
	}
	public double getIncreaseRange() {
		return increaseRange;
	}
	public void setIncreaseRange(double increaseRange) {
		this.increaseRange = increaseRange;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(code).append(",");
		sb.append(name).append(",");
		sb.append(publicTime).append(",");
		sb.append(lowestTclose).append(",");
		sb.append(StockUtil.formatToString2(lowestDate)).append(",");
		sb.append(highestTclose).append(",");
		sb.append(StockUtil.formatToString2(highestDate)).append(",");
		sb.append(beginTclose).append(",");
		sb.append(StockUtil.formatToString2(beginDate)).append(",");
		sb.append(lastestTclose).append(",");
		sb.append(StockUtil.formatToString2(lastestDate)).append(",");
		sb.append(maxRange).append(",").append(lastestRange).append(",").append(increaseRange);
		return sb.toString();
	}
	
	@Override
	public String parseToCsvLine() {
		StringBuffer sb = new StringBuffer();
		sb.append(code).append(",");
		sb.append(name).append(",");
		sb.append(publicTime).append(",");
		sb.append(lowestTclose).append(",");
		sb.append(StockUtil.formatToString2(lowestDate)).append(",");
		sb.append(highestTclose).append(",");
		sb.append(StockUtil.formatToString2(highestDate)).append(",");
		sb.append(beginTclose).append(",");
		sb.append(StockUtil.formatToString2(beginDate)).append(",");
		sb.append(lastestTclose).append(",");
		sb.append(StockUtil.formatToString2(lastestDate)).append(",");
		sb.append(maxRange).append(",").append(lastestRange);
		sb.append(",").append(increaseRange);
		return sb.toString();		
	}
}