package cn.stockAnalysis.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mst_company")
public class Company {
    @Id
    @Column(name="code")
	private String code;
    @Column(name="name")
	private String name;
    @Column(name="list_sector")
	private int listSector;
    @Column(name="public_time")
	private LocalDate publicTime;
    @Column(name="total_stock")
	private double totalStock;
    @Column(name="circulationStock")
	private double circulationStock;
    @Column(name="is_delist")
	private int isDelist;
    @Column(name="is_st")
	private int isSt;
    @Column(name="import_status")
	private int importStatus;
    @Column(name="last_modify_time")
	private LocalDate lastModifyTime;

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
	public int getListSector() {
		return listSector;
	}
	public void setListSector(int listSector) {
		this.listSector = listSector;
	}
	public LocalDate getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(LocalDate publicTime) {
		this.publicTime = publicTime;
	}
	public double getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(double totalStock) {
		this.totalStock = totalStock;
	}
	public double getCirculationStock() {
		return circulationStock;
	}
	public void setCirculationStock(double circulationStock) {
		this.circulationStock = circulationStock;
	}
	public int getIsDelist() {
		return isDelist;
	}
	public void setIsDelist(int isDelist) {
		this.isDelist = isDelist;
	}
	public int getIsSt() {
		return isSt;
	}
	public void setIsSt(int isSt) {
		this.isSt = isSt;
	}
	public int getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(int importStatus) {
		this.importStatus = importStatus;
	}
	public LocalDate getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(LocalDate lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@Override
	public String toString() {
		return "Company [code=" + code + ", name=" + name + ", listSector="
				+ listSector + ", publicTime=" + publicTime + ", totalStock="
				+ totalStock + ", circulationStock=" + circulationStock
				+ ", isDelist=" + isDelist + ", isSt=" + isSt
				+ ", importStatus=" + importStatus + ", lastModifyTime="
				+ lastModifyTime + "]";
	}
}