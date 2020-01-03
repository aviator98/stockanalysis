package cn.stockAnalysis.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StockUtil {
	final static DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyyMMdd");

	public static double parseDouble(String s) {
		if (s==null || "".equals(s) || "None".equals(s))
			return 0.0;
		return Double.parseDouble(s);
	}
	
    public static double formatDouble(double d) {
    	DecimalFormat df = new DecimalFormat("#.0000");
    	return parseDouble(df.format(d));
    }
    
    /**
     * 计算增长率   (d1-d2)/d2
     * @param d1
     * @param d2
     * @return
     */
    public static double calRatio(double d1, double d2) {
    	if (d2 == 0.0)
    		return 0.0;
    	return (d1-d2)/d2;
    }
	
	/**
	 * YYYY-MM-DD类型字符串转成Date
	 * @param s
	 * @return Date
	 */
	public static LocalDate parseToDate(String s) {
		if (s == null)
			return LocalDate.now();
		return LocalDate.parse(s);
	}

	/**
	 * YYYYMMDD类型字符串转成Date
	 * @param s
	 * @return Date
	 */
	public static LocalDate parseToDate2(String s) {
		if (s == null)
			return LocalDate.now();
		return LocalDate.parse(s, df2);
	}

	/**
	 * Date转成YYYY-MM-DD类型字符串
	 * @param s
	 * @return Date
	 */	
	public static String formatToString(LocalDate d) {
		if (d == null)
			return LocalDate.now().toString();
		return d.toString();
	}

	/**
	 * Date转成YYYYMMDD类型字符串
	 * @param s
	 * @return Date
	 */	
	public static String formatToString2(LocalDate d) {
		if (d == null)
			return LocalDate.now().format(df2);
		return d.format(df2);
	}
	
	/**
	 * 获取今日的YYYYMMDD类型字符串
	 * @return
	 */
	public static String getTodayString() {
		return LocalDate.now().format(df2);
	}
}
