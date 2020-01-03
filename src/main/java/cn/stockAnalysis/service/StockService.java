package cn.stockAnalysis.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import cn.stockAnalysis.dao.DailyTradeBatchDao;
import cn.stockAnalysis.domain.Company;
import cn.stockAnalysis.domain.TradeRecord;
import cn.stockAnalysis.repository.CompanyRepository;
import cn.stockAnalysis.repository.DailyTradeRepository;
import cn.stockAnalysis.repository.SplitHistoryRepository;
import cn.stockAnalysis.repository.TradeRecordRepository;
import cn.stockAnalysis.utils.CsvHandleUtil;
import cn.stockAnalysis.utils.PropertiesUtil;
import cn.stockAnalysis.utils.StockUtil;

@Transactional
@Service
public class StockService {
	private static Logger logger = LoggerFactory.getLogger(StockService.class);
	
	@Autowired
    private CompanyRepository comRep;
	
	@Autowired
	private TradeRecordRepository trRep;
	
	@Autowired
	private SplitHistoryRepository shRep;
	
	@Autowired
	private DailyTradeBatchDao dtBatchDao;
	
	@Autowired
	private DailyTradeRepository dtRep;
	
	// 下载CSV数据用线程池
	private final static ExecutorService dles = Executors.newSingleThreadExecutor();

	// csv数据写入DB线程池
	private final static ExecutorService ies = Executors.newFixedThreadPool(4);

	/**
	 * 爬取所有上市公司信息
	 * @return
	 */
	public int execStockSpider(String sector) {
		int result = 0;
		if ("sh".equals(sector)) {
			String companyUrl = PropertiesUtil.getEnvElement("sh.company.url");
			result = grabCompanys(companyUrl);
		} else if ("sz".equals(sector)) {
			String companyUrl = PropertiesUtil.getEnvElement("sz.company.url");
			result = grabCompanys(companyUrl);
		} else if ("new".equals(sector)) {
			String companyUrl = PropertiesUtil.getEnvElement("new.company.url");
			result = grabCompanys(companyUrl);
		} else {
			String companyUrl = PropertiesUtil.getEnvElement("company.url");
			result = grabCompanys(companyUrl);			
		}
		return result;
	}

	private int grabCompanys(String url) {
    	WebClient wc = new WebClient(BrowserVersion.CHROME);
		try {
			// 启用JS解释器，默认为true
			wc.getOptions().setJavaScriptEnabled(true);
			// 禁用css支持
			wc.getOptions().setCssEnabled(false);
			// js运行错误时，是否抛出异常
			wc.getOptions().setThrowExceptionOnScriptError(false);
			// 状态码错误时，是否抛出异常
			wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
			// 设置连接超时时间 ，这里是5S。如果为0，则无限期等待
			wc.getOptions().setTimeout(5000);
			// 是否允许使用ActiveX
			wc.getOptions().setActiveXNative(false);
			// 等待js时间
			wc.waitForBackgroundJavaScript(2000);
			// 设置Ajax异步处理控制器即启用Ajax支持
			wc.setAjaxController(new NicelyResynchronizingAjaxController());
			// 不跟踪抓取
			wc.getOptions().setDoNotTrackEnabled(false);
			DomText dt = wc.getPage(url);
			HtmlPage page = dt.getHtmlPageOrNull();
			// 以xml的形式获取响应文本
			while (true) {
				String pageXml = page.asXml();
				Document doc = Jsoup.parse(pageXml);
				Elements es = doc.select("td.listview-col-Code");
				Elements nes = doc.select("td.listview-col-Name");
				Elements des = doc.select("td.listview-col-ListingDate");
				if (es.size() > 0) {
					List<Company> list = new ArrayList<Company>();
					for (int i=0; i<es.size(); i++) {
						Element e = (Element) es.get(i);
						Company c = new Company();
						String code = e.text();
						c.setCode(code);
						if (code.startsWith("002")) {
							// 中小板
							c.setListSector(2);
						} else if (code.startsWith("300")) {
							// 创业板
							c.setListSector(3);
						} else if (code.startsWith("60")) {
							// 上证A股
							c.setListSector(0);
						} else {
							// 深证A股
							c.setListSector(1);
						}
						if (nes!=null && nes.size()>i) {
							Element ne = (Element) nes.get(i);
							c.setName(ne.text());
						}
						if (des!=null && des.size()>i) {
							Element de = (Element) des.get(i);
							c.setPublicTime(StockUtil.parseToDate(de.text()));
						}
						list.add(c);
					}
					comRep.save(list);
					comRep.flush();
				}

				if (page.getElementById("main-table_next") == null
						|| page.getElementById("main-table_next").getAttribute("class").contains("disabled"))
					break;
				page = page.getElementById("main-table_next").click();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			return 0;
		} finally {
			wc.close();
		}
		return 1;
	}	
	
	/**
	 * 异步下载所有上市公司交易数据
	 */
	public String asynDownLoadTradeCsv(String startDate, String endDate) {
		Future<Integer> future = dles.submit(new Callable<Integer>() {
			public Integer call() {
				return downloadTradeCsv(startDate, endDate);	
			}
		});
		return "request success";
	}
	
	/**
	 * 下载所有上市公司交易数据
	 * @return
	 */
	public int downloadTradeCsv(String startDate, String endDate) {
		List<Company> allCompanys = comRep.findAll();
		if (allCompanys != null) {
			String today = StockUtil.getTodayString();
			if (startDate==null || startDate.length()==0) {
				startDate = today;
			}
			if (endDate==null || endDate.length()==0) {
				endDate = today;
			}

			for (int i=0; i<allCompanys.size(); i++) {
				Company c = allCompanys.get(i);
				int resultCode = downloadSingleTradeCsv(c.getCode(), c.getListSector(), startDate, endDate);
				if(resultCode == 1) {
					logger.info("download csv No->"+i+"   companyCode->"+c.getCode()+"  successful");
				} else {
					logger.error("download csv companyCode->"+c.getCode()+"  failure");
				}
			}
		}
		return 1;
	}
	
	/**
	 * 下载指定的一个公司的交易数据
	 * @param companyCode
	 * @param listSector
	 * @param startDate
	 * @param endDate
	 * @return 下载结果 1：成功，0：失败
	 */
	private int downloadSingleTradeCsv(String companyCode, int listSector, String startDate, String endDate) {
		String code = "";
		if (listSector == 0) {
			// 上证A股
			code = "0" + companyCode;
		} else {
			// 其他股票
			code = "1" + companyCode;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesUtil.getEnvElement("trade.url"));
		sb.append("?code=").append(code);
		sb.append("&start=").append(startDate);
		sb.append("&end=").append(endDate);
		sb.append("&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP");

		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(sb.toString());
			HttpResponse respone = client.execute(httpGet);
		    if(respone.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
		    	return 0;
		    }
		    HttpEntity entity = respone.getEntity();
		    if(entity != null) {
		        InputStream is = entity.getContent();
		        String foldName = "";
		        if (startDate.equals(endDate)) {
		        	foldName = startDate;
		        } else {
		        	foldName = startDate+"_"+endDate;
		        }
		        String csvBasePath = PropertiesUtil.getEnvElement("csv.base.path");
		        File file = new File(csvBasePath+foldName+"\\"+companyCode+".csv");
		        if(!file.getParentFile().exists()) {
		        	file.getParentFile().mkdir();
		        }
		        FileOutputStream fos = new FileOutputStream(file); 
		        byte[] buffer = new byte[4096];
		        int len = -1;
		        while((len = is.read(buffer) )!= -1){
		            fos.write(buffer, 0, len);
		        }
		        fos.close();
		        is.close();
		    }
		} catch (Exception e) {
			logger.error("companyCode->" + companyCode + "#" + e.toString());
			return 0;
		} finally {
		    try {
		        client.close();
		    } catch (IOException e) {
		        logger.error("companyCode->" + companyCode + "#" + e.toString());
		    }
		}
		return 1;
    }

	/**
	 * 导入所有公司交易数据至DB中
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int importTradeCsv(String startDate, String endDate) {
		List<Company> allCompanys = comRep.findAll();
		if (allCompanys != null) {
			String today = StockUtil.getTodayString();
			if (startDate == null || "".equals(startDate)) {
				startDate = today;
			}
			if (endDate == null || "".equals(endDate)) {
				endDate = today;
			}
			for (int i=0; i<allCompanys.size(); i++) {
				Company c = allCompanys.get(i);
				DailyTradeImport idt = new DailyTradeImport();
				idt.setDtBatchDao(dtBatchDao);
				idt.setDtRep(dtRep);
				idt.setShRep(shRep);
				idt.setTrRep(trRep);
				idt.setCompanyCode(c.getCode());
				idt.setStartDate(startDate);
				idt.setEndDate(endDate);
				ies.execute(idt);
			}
		}
		return 1;
	}
	
	public void test() {
		List<TradeRecord> dataList = trRep.findAll();
		String fileName = PropertiesUtil.getEnvElement("record.base.path") + "20191031.csv";
		String head = "企业,历史最高,历史最高日期,本年最高,本年最高日期,本季最高,本季最高日期,本月最高,本月最高日期,本周最高,本周最高日期,历史最低,历史最低日期,本年最低,本年最低日期,本季最低,本季最低日期,本月最低,本月最低日期,本周最低,本周最低日期,最后价,最后日期,累计拆分比例";
		CsvHandleUtil.writeToCsv(fileName, head, dataList);
	}
}
