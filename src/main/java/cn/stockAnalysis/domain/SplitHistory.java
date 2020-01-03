package cn.stockAnalysis.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trn_split_history")
public class SplitHistory {
    @Id
    @Column(name="id")
    private String id;
	@Column(name="code")
	private String code;
	@Column(name="tradeDate")
	private LocalDate tradeDate;
	@Column(name="split_rate")
	private double splitRate;
	@Column(name="total_split_rate")
	private double totalSplitRate;
	@Column(name="last_modify_time")
	private LocalDate lastModifyTime;
	public String getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public LocalDate getTradeDate() {
		return tradeDate;
	}
	public double getSplitRate() {
		return splitRate;
	}
	public LocalDate getLastModifyTime() {
		return lastModifyTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}
	public void setSplitRate(double splitRate) {
		this.splitRate = splitRate;
	}
	public double getTotalSplitRate() {
		return totalSplitRate;
	}
	public void setTotalSplitRate(double totalSplitRate) {
		this.totalSplitRate = totalSplitRate;
	}
	public void setLastModifyTime(LocalDate lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
