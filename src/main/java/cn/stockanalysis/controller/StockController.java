package cn.stockanalysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.stockanalysis.service.AnalysisService;
import cn.stockanalysis.service.StockService;

@RestController
@RequestMapping(value = "/stock")
public class StockController {
	@Autowired
    private StockService stockService;
	
	@Autowired
	private AnalysisService analysisService;

	/**
	 * 爬虫程序，爬取所有上市公司
	 * @return
	 */
    @RequestMapping(value = "/execStockSpider", method = RequestMethod.GET)
    public int execStockSpider(@RequestParam(value="sector", required = false) String sector) {
    	if (sector == null) {
    		sector = "";
    	}
        return stockService.execStockSpider(sector);
    }
	
	/**
	 * 下载交易数据
	 * @param startDate YYYYMMDD
	 * @param endDate YYYYMMDD
	 * @return 1:成功，0:失败
	 */
    @RequestMapping(value = "/downloadTradeCsv", method = RequestMethod.GET)
    public int downloadTradeCsv(@RequestParam(value="startDate", required = false) String startDate,
    		@RequestParam(value="endDate", required = false) String endDate) {
		return stockService.downloadTradeCsv(startDate, endDate);
    }
    /**
     * 异步下载交易数据
     * @param startDate YYYYMMDD
     * @param endDate YYYYMMDD
     * @return 1:成功，0:失败
     */
    @RequestMapping(value = "/asynDownloadTradeCsv", method = RequestMethod.GET)
    public String asynDownloadTradeCsv(@RequestParam(value="startDate", required = false) String startDate,
    		@RequestParam(value="endDate", required = false) String endDate) {
		return stockService.asynDownLoadTradeCsv(startDate, endDate);
    }

    /**
     * 导入交易数据
     * @param startDate YYYYMMDD
     * @param endDate YYYYMMDD
     * @return 1:成功，0:失败
     */
    @RequestMapping(value = "/importTradeCsv", method = RequestMethod.GET)
    public int importTradeCsv(@RequestParam(value="startDate", required = false) String startDate,
    		@RequestParam(value="endDate", required = false) String endDate) {
		return stockService.importTradeCsv(startDate, endDate);
    }
    
    /**
     * 分析交易
     * @param startDate YYYYMMDD
     * @param endDate YYYYMMDD
     * @param strategy 1.Default
     * @return
     */
    @RequestMapping(value = "/compareReturn", method = RequestMethod.GET)
    public int compareReturn(@RequestParam(value="startDate") String startDate,
    		@RequestParam(value="endDate") String endDate,
    		@RequestParam(value="strategy", required = false) String strategy) {
    	if (strategy==null || strategy.length()==0) {
    		strategy = "Default";
    	}
		return analysisService.compareReturn(startDate, endDate, strategy);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
    	stockService.test();
    	return "success";
    }
}
