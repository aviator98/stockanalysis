package cn.stockanalysis.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.stockanalysis.dao.DailyTradeBatchDao;
import cn.stockanalysis.domain.DailyTrade;
import cn.stockanalysis.domain.SplitHistory;
import cn.stockanalysis.domain.TradeRecord;
import cn.stockanalysis.repository.DailyTradeRepository;
import cn.stockanalysis.repository.DailyTradeSpec;
import cn.stockanalysis.repository.SplitHistoryRepository;
import cn.stockanalysis.repository.TradeRecordRepository;
import cn.stockanalysis.utils.PropertiesUtil;
import cn.stockanalysis.utils.StockUtil;

public class DailyTradeImport implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(DailyTradeImport.class);
	
	private DailyTradeBatchDao dtBatchDao;
	private DailyTradeRepository dtRep;
	private SplitHistoryRepository shRep;
	private TradeRecordRepository trRep;
	private String companyCode;
	private String startDate;
	private String endDate;
	
	public DailyTradeImport() {
		this.dtBatchDao = null;
		this.dtRep = null;
		this.shRep = null;
		this.trRep = null;
		this.companyCode = "";
		this.startDate = StockUtil.getTodayString();
		this.endDate = StockUtil.getTodayString();
	}
	
/*	public ImportDataThread(DailyTradeBatchDao dao, String companyCode, String startDate, String endDate) {
		this.dtBatchDao = dao;
		this.companyCode = companyCode;
		this.startDate = startDate;
		this.endDate = endDate;
	}	*/
	
	@Override
    public void run() {
		importSingleTradeCsv();
	}
	
	public void setDtBatchDao(DailyTradeBatchDao dtBatchDao) {
		this.dtBatchDao = dtBatchDao;
	}

	public void setDtRep(DailyTradeRepository dtRep) {
		this.dtRep = dtRep;
	}

	public void setShRep(SplitHistoryRepository shRep) {
		this.shRep = shRep;
	}

	public void setTrRep(TradeRecordRepository trRep) {
		this.trRep = trRep;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 从CSV中读取交易数据
	 * @param companyCode
	 * @param startDate
	 * @param endData
	 * @return
	 */
	private List<DailyTrade> readTradeCsv() {
		String foldName = "";
        if (startDate.equals(endDate)) {
        	foldName = startDate;
        } else {
        	foldName = startDate+"_"+endDate;
        }
        String csvBasePath = PropertiesUtil.getEnvElement("csv.base.path");
    	File csv = new File(csvBasePath+foldName+"\\"+companyCode+".csv");
        BufferedReader br = null;
        try {
        	InputStreamReader isr = new InputStreamReader(new FileInputStream(csv), "GBK");
        	br = new BufferedReader(isr);

            String line = "";
        	br.readLine();

    		List<DailyTrade> list = new ArrayList<DailyTrade>();
        	while ((line = br.readLine()) != null) {
        		String[] data = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
        		DailyTrade dt = new DailyTrade();
        		LocalDate tradeDate = StockUtil.parseToDate(data[0]);
	        	String sTradeDate = StockUtil.formatToString2(tradeDate);
	        	String code = companyCode;
	        	dt.setId(code+sTradeDate);
	        	dt.setCode(code);
	        	dt.setTradeDate(tradeDate);
        		dt.setTclose(StockUtil.parseDouble(data[3]));
        		dt.setHigh(StockUtil.parseDouble(data[4]));
        		dt.setLow(StockUtil.parseDouble(data[5]));
        		dt.setTopen(StockUtil.parseDouble(data[6]));
        		dt.setLclose(StockUtil.parseDouble(data[7]));
        		dt.setChg(StockUtil.parseDouble(data[8]));
        		dt.setPchg(StockUtil.parseDouble(data[9]));
        		dt.setTurnover(StockUtil.parseDouble(data[10]));
        		dt.setVoturnover(StockUtil.parseDouble(data[11]));
        		dt.setVaturnover(StockUtil.parseDouble(data[12]));
        		dt.setTcap(StockUtil.parseDouble(data[13]));
        		dt.setMcap(StockUtil.parseDouble(data[14]));
        		list.add(dt);
        	}
        	Collections.sort(list, new Comparator<DailyTrade>() {
        		@Override
        		public int compare(DailyTrade dt1, DailyTrade dt2) {
        			if (dt1.getTradeDate().isAfter(dt2.getTradeDate())) {
        				return 1;
        			} else {
        				return -1;
        			}
        		}
        	});
    		logger.info("read data success companyCode->"+companyCode+"   data count->"+list.size());
    		return list;
        } catch (FileNotFoundException e) {
        	logger.error("companyCode->" + companyCode + "#" + e.toString());
            return null;
        } catch (UnsupportedEncodingException e) {
        	logger.error("companyCode->" + companyCode + "#" + e.toString());
        	return null;
        } catch (IOException e) {
        	logger.error("companyCode->" + companyCode + "#" + e.toString());
        	return null;
        } finally {
		    try {
		        br.close();
		    } catch (IOException e) {
		    	logger.error("companyCode->" + companyCode + "#" + e.toString());
		        return null;
		    }
        }
	}
	
	/**
	 * 导入指定公司的csv数据至DB中
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @return 0:失败，1：成功
	 */
	private int importSingleTradeCsv() {
		// 从CSV读取日次交易数据
		List<DailyTrade> list = readTradeCsv();

		// 计算拆股比例，拆股记录，高低点，后复权收盘价
		if (list != null && !list.isEmpty()) {
			updateTradeData(list);
			updateTradeRecord();
		}
		return 1;
	}
	
	/**
	 * 计算拆股比例的记录，后复权收盘价和历史最高，最低收盘价
	 * @param tradeList
	 * @return 0:失败，1：成功
	 */
	private int updateTradeData(List<DailyTrade> tradeList) {
		TradeRecord tr = trRep.findOne(companyCode);
		if (tr == null) {
			tr = new TradeRecord(companyCode);
		}

		List<SplitHistory> splitHistoryList = new ArrayList<SplitHistory>();
		double lastTclose = tr.getLatestTclose();   // 获取最后一天收盘价格
		double totalSplitRate = tr.getTotalSplitRate();		// 获取之前总拆分比例
		double splitRate = 1.00;			// 拆分比例

		for (int i=0; i<tradeList.size(); i++) {
			DailyTrade dt = tradeList.get(i);
			if (lastTclose == 0.0) {
				lastTclose = dt.getLclose();				
			}

			if (lastTclose != 0.0 && lastTclose != dt.getLclose()) {
				// 记录拆股历史
				splitRate = lastTclose / dt.getLclose();
				totalSplitRate = totalSplitRate * splitRate;

				SplitHistory sh = new SplitHistory();
				sh.setId(dt.getId());
				sh.setCode(dt.getCode());
				sh.setTradeDate(dt.getTradeDate());
				sh.setSplitRate(splitRate);
				sh.setTotalSplitRate(totalSplitRate);
				splitHistoryList.add(sh);
			}

			// 更新后复权交易值
			double tcloseAfter = dt.getTclose() * totalSplitRate;
			dt.setTcloseAfter(tcloseAfter);
			
			// 更新历史最高值
			if (tcloseAfter > tr.getMaxHistoryTclose()) {
				tr.setMaxHistoryTclose(tcloseAfter);
				tr.setMaxHistoryDate(dt.getTradeDate());
			}
			// 更新历史最低值
			if (tr.getMinHistoryTclose() == 0.0 || (tcloseAfter != 0.0 && tcloseAfter < tr.getMinHistoryTclose())) {
				tr.setMinHistoryTclose(tcloseAfter);
				tr.setMinHistoryDate(dt.getTradeDate());
			}
			// 更新最近交易值
			if ((i+1) == tradeList.size()) {
				tr.setLatestTclose(dt.getTclose());
				tr.setLatestDate(dt.getTradeDate());
			}

			lastTclose = dt.getTclose();
		}
		tr.setTotalSplitRate(totalSplitRate);

		// 分拆比例写入数据库中
		if (!splitHistoryList.isEmpty()) {
			shRep.save(splitHistoryList);
			shRep.flush();			
		}
		trRep.saveAndFlush(tr);
		dtBatchDao.batchInsert(tradeList);

		return 1;
	}
	
	/**
	 * 计算并更新最后导入数据的最近一年，一季度，一月，一周的最高，最低收盘价
	 * @param tradeList
	 * @return 0:失败，1：成功
	 */
	private int updateTradeRecord() {
		TradeRecord tr = trRep.findOne(companyCode);
		LocalDate latestDate = tr.getLatestDate();
		LocalDate oneYearBefore = latestDate.minusYears(1);
		LocalDate oneSeasonBefore = latestDate.minusMonths(3);
		LocalDate oneMonthBefore = latestDate.minusMonths(1);
		LocalDate oneWeekBefore = latestDate.minusWeeks(1);
		double oneYearMax = 0.0;
		double oneYearMin = 0.0;
		double oneSeasonMax = 0.0;
		double oneSeasonMin = 0.0;
		double oneMonthMax = 0.0;
		double oneMonthMin = 0.0;
		double oneWeekMax = 0.0;
		double oneWeekMin = 0.0;
		LocalDate oneYearMaxDate = oneYearBefore;
		LocalDate oneYearMinDate = oneYearBefore;
		LocalDate oneSeasonMaxDate = oneSeasonBefore;
		LocalDate oneSeasonMinDate = oneSeasonBefore;		
		LocalDate oneMonthMaxDate = oneMonthBefore;
		LocalDate oneMonthMinDate = oneMonthBefore;
		LocalDate oneWeekMaxDate = oneWeekBefore;
		LocalDate oneWeekMinDate = oneWeekBefore;
		List<DailyTrade> tradeList = dtRep.findAll(DailyTradeSpec.getSpec(companyCode, oneYearBefore, latestDate));
		for (DailyTrade dt : tradeList) {
			double tcloseAfter = dt.getTcloseAfter();
			LocalDate tradeDate = dt.getTradeDate();

			if (tcloseAfter > oneYearMax) {
				oneYearMax = tcloseAfter;
				oneYearMaxDate = tradeDate;
			}
			if (tcloseAfter > oneSeasonMax && tradeDate.isAfter(oneSeasonBefore)) {
				oneSeasonMax = tcloseAfter;
				oneSeasonMaxDate = tradeDate;
			}
			if (tcloseAfter > oneMonthMax && tradeDate.isAfter(oneMonthBefore)) {
				oneMonthMax = tcloseAfter;
				oneMonthMaxDate = tradeDate;
			}
			if (tcloseAfter > oneWeekMax && tradeDate.isAfter(oneWeekBefore)) {
				oneWeekMax = tcloseAfter;
				oneWeekMaxDate = tradeDate;
			}
			if (oneYearMin == 0.0 || tcloseAfter < oneYearMin) {
				oneYearMin = tcloseAfter;
				oneYearMinDate = tradeDate;
			}
			if ((oneSeasonMin == 0.0 || tcloseAfter < oneSeasonMin)
					&& tradeDate.isAfter(oneSeasonBefore)) {
				oneSeasonMin = tcloseAfter;
				oneSeasonMinDate = tradeDate;
			}
			if ((oneMonthMin == 0.0 || tcloseAfter < oneMonthMin)
					&& tradeDate.isAfter(oneMonthBefore)) {
				oneMonthMin = tcloseAfter;
				oneMonthMinDate = tradeDate;
			}
			if ((oneWeekMin == 0.0 || tcloseAfter < oneWeekMin)
					&& tradeDate.isAfter(oneWeekBefore)) {
				oneWeekMin = tcloseAfter;
				oneWeekMinDate = tradeDate;
			}
		}
		tr.setMaxYearTclose(oneYearMax);
		tr.setMaxYearDate(oneYearMaxDate);
		tr.setMaxSeasonTclose(oneSeasonMax);
		tr.setMaxSeasonDate(oneSeasonMaxDate);
		tr.setMaxMonthTclose(oneMonthMax);
		tr.setMaxMonthDate(oneMonthMaxDate);
		tr.setMaxWeekTclose(oneWeekMax);
		tr.setMaxWeekDate(oneWeekMaxDate);
		tr.setMinYearTclose(oneYearMin);
		tr.setMinYearDate(oneYearMinDate);
		tr.setMinSeasonTclose(oneSeasonMin);
		tr.setMinSeasonDate(oneSeasonMinDate);
		tr.setMinMonthTclose(oneMonthMin);
		tr.setMinMonthDate(oneMonthMinDate);
		tr.setMinWeekTclose(oneWeekMin);
		tr.setMinWeekDate(oneWeekMinDate);
		trRep.saveAndFlush(tr);

		return 1;
	}

//	/**
//	 * 导入指定公司的CSV数据至DB中
//	 * @param companyCode
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 */
//	private int importSingleTradeCsv(String companyCode, String startDate, String endDate) {
//		String foldName = "";
//        if (startDate.equals(endDate)) {
//        	foldName = startDate;
//        } else {
//        	foldName = startDate+"_"+endDate;
//        }
//        String csvBasePath = PropertiesUtil.getEnvElement("csv.base.path");
//    	File csv = new File(csvBasePath+foldName+"\\"+companyCode+".csv");
//        BufferedReader br = null;
//        try {
//        	InputStreamReader isr = new InputStreamReader(new FileInputStream(csv), "GBK");
//        	br = new BufferedReader(isr);
//        	
//            String line = "";
//            int i = 0;
//        	br.readLine();
//
//    		List<DailyTrade> list = new ArrayList<DailyTrade>();
//        	while ((line = br.readLine()) != null) {
//        		i++;
//        		String[] data = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
//        		DailyTrade dt = new DailyTrade();
//        		LocalDate tradeDate = StockUtil.parseToDate(data[0]);
//	        	String sTradeDate = StockUtil.formatToString2(tradeDate);
//	        	String code = companyCode;
//	        	dt.setId(code+sTradeDate);
//	        	dt.setCode(code);
//	        	dt.setTradeDate(tradeDate);
//        		dt.setTclose(StockUtil.parseDouble(data[3]));
//        		dt.setHigh(StockUtil.parseDouble(data[4]));
//        		dt.setLow(StockUtil.parseDouble(data[5]));
//        		dt.setTopen(StockUtil.parseDouble(data[6]));
//        		dt.setLclose(StockUtil.parseDouble(data[7]));
//        		dt.setChg(StockUtil.parseDouble(data[8]));
//        		dt.setPchg(StockUtil.parseDouble(data[9]));
//        		dt.setTurnover(StockUtil.parseDouble(data[10]));
//        		dt.setVoturnover(StockUtil.parseDouble(data[11]));
//        		dt.setVaturnover(StockUtil.parseDouble(data[12]));
//        		dt.setTcap(StockUtil.parseDouble(data[13]));
//        		dt.setMcap(StockUtil.parseDouble(data[14]));
//
//        		list.add(dt);
//
//        		if (i == 100) {
//        			dtBatchDao.batchInsert(list);
//        			i = 0;
//        			list.clear();
//        		}
//        	}
//        	dtBatchDao.batchInsert(list);
//        } catch (FileNotFoundException e) {
//        	logger.error("companyCode->" + companyCode + "#" + e.toString());
//            return 0;
//        } catch (UnsupportedEncodingException e) {
//        	logger.error("companyCode->" + companyCode + "#" + e.toString());
//        	return 0;
//        } catch (IOException e) {
//        	logger.error("companyCode->" + companyCode + "#" + e.toString());
//        	return 0;
//        } finally {
//		    try {
//		        br.close();
//		    } catch (IOException e) {
//		    	logger.error("companyCode->" + companyCode + "#" + e.toString());
//		        return 0;
//		    }        	
//        }
//        logger.info("import data companyCode->"+companyCode+"   successful");
//		return 1;
//	}
}
