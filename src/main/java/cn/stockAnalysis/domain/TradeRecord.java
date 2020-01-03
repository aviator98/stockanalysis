package cn.stockAnalysis.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.stockAnalysis.utils.StockUtil;
import cn.stockAnalysis.vo.CsvEntity;

@Entity
@Table(name="tbl_trade_record")
public class TradeRecord implements CsvEntity {
    @Id
    @Column(name="code")
	private String code;
	@Column(name="max_history_tclose")
	private double maxHistoryTclose;
	@Column(name="max_history_date")
	private LocalDate maxHistoryDate;
	@Column(name="max_year_tclose")
	private double maxYearTclose;
	@Column(name="max_year_date")
	private LocalDate maxYearDate;
	@Column(name="max_season_tclose")
	private double maxSeasonTclose;
	@Column(name="max_season_date")
	private LocalDate maxSeasonDate;
	@Column(name="max_month_tclose")
	private double maxMonthTclose;
	@Column(name="max_month_date")
	private LocalDate maxMonthDate;
	@Column(name="max_week_tclose")
	private double maxWeekTclose;
	@Column(name="max_week_date")
	private LocalDate maxWeekDate;
	@Column(name="min_history_tclose")
	private double minHistoryTclose;
	@Column(name="min_history_date")
	private LocalDate minHistoryDate;
	@Column(name="min_year_tclose")
	private double minYearTclose;
	@Column(name="min_year_date")
	private LocalDate minYearDate;
	@Column(name="min_season_tclose")
	private double minSeasonTclose;
	@Column(name="min_season_date")
	private LocalDate minSeasonDate;
	@Column(name="min_month_tclose")
	private double minMonthTclose;
	@Column(name="min_month_date")
	private LocalDate minMonthDate;
	@Column(name="min_week_tclose")
	private double minWeekTclose;
	@Column(name="min_week_date")
	private LocalDate minWeekDate;
	@Column(name="latest_tclose")
	private double latestTclose;
	@Column(name="latest_date")
	private LocalDate latestDate;
	@Column(name="total_split_rate")
	private double totalSplitRate;
	@Column(name="last_modify_time")
	private LocalDate lastModifyTime;

	public TradeRecord() {}

	public TradeRecord(String code) {
		this.code = code;
		this.maxHistoryTclose = 0.0;
		this.maxHistoryDate = LocalDate.now();
		this.maxYearTclose = 0.0;
		this.maxYearDate = LocalDate.now();
		this.maxSeasonTclose = 0.0;
		this.maxSeasonDate = LocalDate.now();
		this.maxMonthTclose = 0.0;
		this.maxMonthDate = LocalDate.now();
		this.maxWeekTclose = 0.0;
		this.maxWeekDate = LocalDate.now();
		this.minHistoryTclose = 0.0;
		this.minHistoryDate = LocalDate.now();
		this.minYearTclose = 0.0;
		this.minYearDate = LocalDate.now();
		this.minSeasonTclose = 0.0;
		this.minSeasonDate = LocalDate.now();
		this.minMonthTclose = 0.0;
		this.minMonthDate = LocalDate.now();
		this.minWeekTclose = 0.0;
		this.minWeekDate = LocalDate.now();
		this.latestTclose = 0.0;
		this.latestDate = LocalDate.now();
		this.totalSplitRate = 1.0;
	}
	public String getCode() {
		return code;
	}
	public double getMaxHistoryTclose() {
		return maxHistoryTclose;
	}
	public LocalDate getMaxHistoryDate() {
		return maxHistoryDate;
	}
	public double getMaxYearTclose() {
		return maxYearTclose;
	}
	public LocalDate getMaxYearDate() {
		return maxYearDate;
	}
	public double getMaxSeasonTclose() {
		return maxSeasonTclose;
	}
	public LocalDate getMaxSeasonDate() {
		return maxSeasonDate;
	}
	public double getMaxMonthTclose() {
		return maxMonthTclose;
	}
	public LocalDate getMaxMonthDate() {
		return maxMonthDate;
	}
	public double getMaxWeekTclose() {
		return maxWeekTclose;
	}
	public LocalDate getMaxWeekDate() {
		return maxWeekDate;
	}
	public double getMinHistoryTclose() {
		return minHistoryTclose;
	}
	public LocalDate getMinHistoryDate() {
		return minHistoryDate;
	}
	public double getMinYearTclose() {
		return minYearTclose;
	}
	public LocalDate getMinYearDate() {
		return minYearDate;
	}
	public double getMinSeasonTclose() {
		return minSeasonTclose;
	}
	public LocalDate getMinSeasonDate() {
		return minSeasonDate;
	}
	public double getMinMonthTclose() {
		return minMonthTclose;
	}
	public LocalDate getMinMonthDate() {
		return minMonthDate;
	}
	public double getMinWeekTclose() {
		return minWeekTclose;
	}
	public LocalDate getMinWeekDate() {
		return minWeekDate;
	}
	public double getLatestTclose() {
		return latestTclose;
	}
	public LocalDate getLatestDate() {
		return latestDate;
	}
	public double getTotalSplitRate() {
		return totalSplitRate;
	}
	public void setTotalSplitRate(double totalSplitRate) {
		this.totalSplitRate = totalSplitRate;
	}
	public LocalDate getLastModifyTime() {
		return lastModifyTime;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMaxHistoryTclose(double maxHistoryTclose) {
		this.maxHistoryTclose = maxHistoryTclose;
	}
	public void setMaxHistoryDate(LocalDate maxHistoryDate) {
		this.maxHistoryDate = maxHistoryDate;
	}
	public void setMaxYearTclose(double maxYearTclose) {
		this.maxYearTclose = maxYearTclose;
	}
	public void setMaxYearDate(LocalDate maxYearDate) {
		this.maxYearDate = maxYearDate;
	}
	public void setMaxSeasonTclose(double maxSeasonTclose) {
		this.maxSeasonTclose = maxSeasonTclose;
	}
	public void setMaxSeasonDate(LocalDate maxSeasonDate) {
		this.maxSeasonDate = maxSeasonDate;
	}
	public void setMaxMonthTclose(double maxMonthTclose) {
		this.maxMonthTclose = maxMonthTclose;
	}
	public void setMaxMonthDate(LocalDate maxMonthDate) {
		this.maxMonthDate = maxMonthDate;
	}
	public void setMaxWeekTclose(double maxWeekTclose) {
		this.maxWeekTclose = maxWeekTclose;
	}
	public void setMaxWeekDate(LocalDate maxWeekDate) {
		this.maxWeekDate = maxWeekDate;
	}
	public void setMinHistoryTclose(double minHistoryTclose) {
		this.minHistoryTclose = minHistoryTclose;
	}
	public void setMinHistoryDate(LocalDate minHistoryDate) {
		this.minHistoryDate = minHistoryDate;
	}
	public void setMinYearTclose(double minYearTclose) {
		this.minYearTclose = minYearTclose;
	}
	public void setMinYearDate(LocalDate minYearDate) {
		this.minYearDate = minYearDate;
	}
	public void setMinSeasonTclose(double minSeasonTclose) {
		this.minSeasonTclose = minSeasonTclose;
	}
	public void setMinSeasonDate(LocalDate minSeasonDate) {
		this.minSeasonDate = minSeasonDate;
	}
	public void setMinMonthTclose(double minMonthTclose) {
		this.minMonthTclose = minMonthTclose;
	}
	public void setMinMonthDate(LocalDate minMonthDate) {
		this.minMonthDate = minMonthDate;
	}
	public void setMinWeekTclose(double minWeekTclose) {
		this.minWeekTclose = minWeekTclose;
	}
	public void setMinWeekDate(LocalDate minWeekDate) {
		this.minWeekDate = minWeekDate;
	}
	public void setLatestTclose(double latestTclose) {
		this.latestTclose = latestTclose;
	}
	public void setLatestDate(LocalDate latestDate) {
		this.latestDate = latestDate;
	}

	public void setLastModifyTime(LocalDate lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@Override
	public String toString() {
		return "TradeRecord [code=" + code + ", maxHistoryTclose="
				+ maxHistoryTclose + ", maxHistoryDate=" + maxHistoryDate
				+ ", maxYearTclose=" + maxYearTclose + ", maxYearDate="
				+ maxYearDate + ", maxSeasonTclose=" + maxSeasonTclose
				+ ", maxSeasonDate=" + maxSeasonDate + ", maxMonthTclose="
				+ maxMonthTclose + ", maxMonthDate=" + maxMonthDate
				+ ", maxWeekTclose=" + maxWeekTclose + ", maxWeekDate="
				+ maxWeekDate + ", minHistoryTclose=" + minHistoryTclose
				+ ", minHistoryDate=" + minHistoryDate + ", minYearTclose="
				+ minYearTclose + ", minYearDate=" + minYearDate
				+ ", minSeasonTclose=" + minSeasonTclose + ", minSeasonDate="
				+ minSeasonDate + ", minMonthTclose=" + minMonthTclose
				+ ", minMonthDate=" + minMonthDate + ", minWeekTclose="
				+ minWeekTclose + ", minWeekDate=" + minWeekDate
				+ ", latestTclose=" + latestTclose + ", latestDate="
				+ latestDate + ", totalSplitRate=" + totalSplitRate
				+ ", lastModifyTime=" + lastModifyTime + "]";
	}
	
	@Override
	public String parseToCsvLine() {
		StringBuffer sb = new StringBuffer();

		sb.append(code).append(",")
		  .append(maxHistoryTclose).append(",")
		  .append(StockUtil.formatToString2(maxHistoryDate)).append(",")
		  .append(maxYearTclose).append(",")
		  .append(StockUtil.formatToString2(maxYearDate)).append(",")
		  .append(maxSeasonTclose).append(",")
		  .append(StockUtil.formatToString2(maxSeasonDate)).append(",")
		  .append(maxMonthTclose).append(",")
		  .append(StockUtil.formatToString2(maxMonthDate)).append(",")
		  .append(maxWeekTclose).append(",")
		  .append(StockUtil.formatToString2(maxWeekDate)).append(",")
		  .append(minHistoryTclose).append(",")
		  .append(StockUtil.formatToString2(minHistoryDate)).append(",")
		  .append(minYearTclose).append(",")
		  .append(StockUtil.formatToString2(minYearDate)).append(",")
		  .append(minSeasonTclose).append(",")
		  .append(StockUtil.formatToString2(minSeasonDate)).append(",")
		  .append(minMonthTclose).append(",")
		  .append(StockUtil.formatToString2(minMonthDate)).append(",")
		  .append(minWeekTclose).append(",")
		  .append(StockUtil.formatToString2(minWeekDate)).append(",")
		  .append(latestTclose).append(",")
		  .append(StockUtil.formatToString2(latestDate)).append(",")
		  .append(totalSplitRate).append(",");

		return sb.toString();
	}
}
