package cn.stockanalysis.service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.stockanalysis.domain.Company;
import cn.stockanalysis.domain.DailyTrade;
import cn.stockanalysis.repository.CompanyRepository;
import cn.stockanalysis.repository.DailyTradeRepository;
import cn.stockanalysis.repository.DailyTradeSpec;
import cn.stockanalysis.service.strategy.DefaultFilterStrategyFactory;
import cn.stockanalysis.service.strategy.FilterStrategy;
import cn.stockanalysis.service.strategy.FilterStrategyFactory;
import cn.stockanalysis.utils.PropertiesUtil;
import cn.stockanalysis.utils.StockUtil;
import cn.stockanalysis.vo.TradeCal;

@Transactional
@Service
public class AnalysisService {

	private static Logger logger = LoggerFactory.getLogger(AnalysisService.class);

	@Autowired
    private CompanyRepository comRep;

	@Autowired
	private DailyTradeRepository dtRep;
	
	private FilterStrategyFactory factory = null;

	private Map<String, List<TradeCal>> dataMap = new HashMap<String, List<TradeCal>>();
	
	private Map<String, Company> comMap = new HashMap<String, Company>();
	
	public AnalysisService() {
		factory = new DefaultFilterStrategyFactory();
	}
	
	/**
	 * 获取指定时间内的交易数据
	 */
	private Map<String, List<DailyTrade>> getTradeList(String startDate, String endDate) {
	    LocalDate sDate = StockUtil.parseToDate2(startDate);
	    LocalDate eDate = StockUtil.parseToDate2(endDate);
		List<DailyTrade> tradeList = dtRep.findAll(DailyTradeSpec.getSpec(sDate, eDate));
		Map<String, List<DailyTrade>> resultMap = tradeList.stream().collect(Collectors.groupingBy(DailyTrade::getCode));
		return resultMap;
	}
	
	/**
	 * 计算各公司指定时间的收益
	 */
	private List<TradeCal> getReturnList(String startDate, String endDate) {
		if (comMap.isEmpty()) {
			List<Company> allCompanys = comRep.findAll();
			for (Company c : allCompanys) {
				comMap.put(c.getCode(), c);
			}
		}

		List<TradeCal> tradeCalList = (List<TradeCal>) dataMap.get(startDate+"_"+endDate);
		if (tradeCalList==null) {
			tradeCalList = new ArrayList<TradeCal>();
			Map<String, List<DailyTrade>> dailyTradeGroup = getTradeList(startDate, endDate);
			for (Map.Entry<String, List<DailyTrade>> entry : dailyTradeGroup.entrySet()) {
				TradeCal tc = new TradeCal();
	    		tc.setCode(entry.getKey());
	    		Company c = comMap.get(entry.getKey());
	    		tc.setName(c.getName());
	    		if (c.getPublicTime() != null) {
		    		tc.setPublicTime(c.getPublicTime().toString());	
	    		}
	    		List<DailyTrade> singleTradeList = entry.getValue();
	            for (int i=0; i<singleTradeList.size(); i++) {
	            	DailyTrade dt = singleTradeList.get(i);
	            	double tclose = dt.getTclose();
	            	tclose = tclose==0?dt.getLclose():tclose;
	            	if (i==0) {
	            		tc.setHighestDate(dt.getTradeDate());
	            		tc.setHighestTclose(tclose);
	            		tc.setLowestDate(dt.getTradeDate());
	            		tc.setLowestTclose(tclose);
	            		tc.setBeginTclose(tclose);
	            		tc.setBeginDate(dt.getTradeDate());
	            	}
	            	if (tclose > tc.getHighestTclose()) {
	            		tc.setHighestDate(dt.getTradeDate());
	            		tc.setHighestTclose(tclose);         		
	            	}
	            	if (tclose < tc.getLowestTclose()) {
	            		tc.setLowestDate(dt.getTradeDate());
	            		tc.setLowestTclose(tclose);            		
	            	}
	            	if ((i+1)==singleTradeList.size()) {
	            		tc.setLastestDate(dt.getTradeDate());
	            		tc.setLastestTclose(tclose);
	            	}
	            }
	            double maxRange = StockUtil.calRatio(tc.getHighestTclose(), tc.getLowestTclose());
	            tc.setMaxRange(StockUtil.formatDouble(maxRange) * 100);
	            double lastestRange = StockUtil.calRatio(tc.getLastestTclose(), tc.getLowestTclose());
	            tc.setLastestRange(StockUtil.formatDouble(lastestRange) * 100);
	            double increaseRange = StockUtil.calRatio(tc.getLastestTclose(), tc.getBeginTclose());
	            tc.setIncreaseRange(StockUtil.formatDouble(increaseRange) * 100);
	            tradeCalList.add(tc);
			}
		}
		return tradeCalList;
	}

	/**
	 * 指定的时间内比较所有公司的收益
	 * @param startDate	YYYYMMDD
	 * @param endDate YYYYMMDD
	 * @return 0:失败，1:成功
	 */
	public int compareReturn(String startDate, String endDate, String strategy) {
		List<TradeCal> tradeCalList = getReturnList(startDate, endDate);
		FilterStrategy fs = factory.createInstance(strategy);
		tradeCalList = fs.filter(tradeCalList);

        tradeCalList.sort(new Comparator<TradeCal>() {
            @Override
            public int compare(TradeCal o1, TradeCal o2) {
                // TODO Auto-generated method stub
            	if (o1.getLastestRange() > o2.getLastestRange()) {
            		return -1;
            	} else if (o1.getLastestRange() < o2.getLastestRange()) {
            		return 1;
            	} else if (o1.getIncreaseRange() > o2.getIncreaseRange()) {
            		return -1;
            	} else if (o1.getIncreaseRange() < o2.getIncreaseRange()) {
            		return 1;
            	} else {	
            		return 0;
            	}
            }
        });

        int outputResult = outputToCSV(startDate+"_"+endDate+"_"+strategy, tradeCalList);
		return outputResult;
	}

	public int outputToCSV(String title, List<TradeCal> list) {
		String resultPath = PropertiesUtil.getEnvElement("result.base.path");
		File file = new File(resultPath+title+".csv");
		try {
	        if(!file.getParentFile().exists()) {
	        	file.getParentFile().mkdir();
	        }
	        FileOutputStream fos = new FileOutputStream(file);
	        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
	        BufferedWriter bw = new BufferedWriter(osw);
	        String csvTitle = "SearchCondition,"+title;
	        bw.write(csvTitle+"\n");
	        String headNames = "No,code,name,publicTime,lowestTclose,lowestDate,highestTclose,highestDate,beginTclose,beginDate,lastestTclose,lastestDate,maxRange(%),lastestRange(%),increaseRange(%)";
	        bw.write(headNames+"\n");
	        for(int i=0; i<list.size(); i++) {
	            TradeCal tc = list.get(i);
	            String outputLine = String.valueOf(i+1)+","+tc+"\n";
	        	bw.write(outputLine);
	        }
	        bw.close();
	        osw.close();
	        fos.close();
		} catch (FileNotFoundException e) {
			logger.error("file not found error");
			return 0;
		} catch (UnsupportedEncodingException e) {
			logger.error("encoding error");
			return 0;		
		} catch (IOException e) {
			logger.error("io error");
			return 0;				
		}
		return 1;
	}
}
