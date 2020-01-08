package cn.stockanalysis.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trn_daily_trade")
public class DailyTrade {
    @Id
    @Column(name="id")
    private String id;
	@Column(name="code")
	private String code;
	@Column(name="tradeDate")
	private LocalDate tradeDate;
    @Column(name="tclose")
	private double tclose;
    @Column(name="high")
	private double high;
    @Column(name="low")
	private double low;
    @Column(name="topen")
	private double topen;
    @Column(name="lclose")
	private double lclose;
    @Column(name="chg")
	private double chg;
    @Column(name="pchg")
	private double pchg;
    @Column(name="turnover")
	private double turnover;
    @Column(name="voturnover")
	private double voturnover;
    @Column(name="vaturnover")
	private double vaturnover;
    @Column(name="tcap")
	private double tcap;
    @Column(name="mcap")
	private double mcap;
    @Column(name="tcloseAfter")
    private double tcloseAfter;
    @Column(name="last_modify_time")
	private LocalDate lastModifyTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDate getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}
    /**
     * 收盘价
     * @return
     */
	public double getTclose() {
		return tclose;
	}
	public void setTclose(double tclose) {
		this.tclose = tclose;
	}
    /**
     * 最高价
     * @return
     */
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
    /**
     * 最低价
     * @return
     */
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
    /**
     * 开盘价
     * @return
     */
	public double getTopen() {
		return topen;
	}
	public void setTopen(double topen) {
		this.topen = topen;
	}
    /**
     * 前收盘
     * @return
     */
	public double getLclose() {
		return lclose;
	}
	public void setLclose(double lclose) {
		this.lclose = lclose;
	}
    /**
     * 涨跌额
     * @return
     */
	public double getChg() {
		return chg;
	}
	public void setChg(double chg) {
		this.chg = chg;
	}
    /**
     * 涨跌幅
     * @return
     */
	public double getPchg() {
		return pchg;
	}
	public void setPchg(double pchg) {
		this.pchg = pchg;
	}
    /**
     * 换手率
     * @return
     */
	public double getTurnover() {
		return turnover;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
    /**
     * 成交量
     * @return
     */
	public double getVoturnover() {
		return voturnover;
	}
	public void setVoturnover(double voturnover) {
		this.voturnover = voturnover;
	}
    /**
     * 成交金额
     * @return
     */
	public double getVaturnover() {
		return vaturnover;
	}
	public void setVaturnover(double vaturnover) {
		this.vaturnover = vaturnover;
	}
    /**
     * 总市值
     * @return
     */
	public double getTcap() {
		return tcap;
	}
	public void setTcap(double tcap) {
		this.tcap = tcap;
	}
    /**
     * 流通市值
     * @return
     */
	public double getMcap() {
		return mcap;
	}
	public void setMcap(double mcap) {
		this.mcap = mcap;
	}
	/**
	 * 后复权收盘价
	 * @return
	 */
	public double getTcloseAfter() {
		return tcloseAfter;
	}
	public void setTcloseAfter(double tcloseAfter) {
		this.tcloseAfter = tcloseAfter;
	}
	public LocalDate getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(LocalDate lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@Override
	public String toString() {
		return "DailyTrade [id=" + id + ", code=" + code + ", tradeDate="
				+ tradeDate + ", tclose=" + tclose + ", high=" + high
				+ ", low=" + low + ", topen=" + topen + ", lclose=" + lclose
				+ ", chg=" + chg + ", pchg=" + pchg + ", turnover=" + turnover
				+ ", voturnover=" + voturnover + ", vaturnover=" + vaturnover
				+ ", tcap=" + tcap + ", mcap=" + mcap + ", tcloseAfter="
				+ tcloseAfter + ", lastModifyTime=" + lastModifyTime + "]";
	}
}
