package cn.stockAnalysis.vo;

public interface CsvEntity {

	/**
	 * 将实例对象转成CSV格式的行文字
	 * @return String
	 */
	public String parseToCsvLine();
}
