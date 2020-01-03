package cn.stockAnalysis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.stockAnalysis.vo.CsvEntity;

public class CsvHandleUtil {
	private static Logger logger = LoggerFactory.getLogger(CsvHandleUtil.class);

	public static int writeToCsv(String fileName, String head, List<? extends CsvEntity> dataList) {
		return writeToCsv(fileName, "", head, dataList);
	}

	public static int writeToCsv(String fileName, String title, String head, List<? extends CsvEntity> dataList) {
		File file = new File(fileName);
        if(!file.getParentFile().exists()) {
        	file.getParentFile().mkdir();
        }
		if (file.exists()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage());
			} 
		}
		
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try {
	        fos = new FileOutputStream(file);
	        osw = new OutputStreamWriter(fos, "UTF-8");
	        bw = new BufferedWriter(osw);
	        if (!title.isEmpty()) {
	            bw.write(title + "\n");
	        }
	        if (!head.isEmpty()) {
	            bw.write(head + "\n");
	        }

	        for (CsvEntity ce : dataList) {
	        	String line = ce.parseToCsvLine() + "\n";
	        	bw.write(line);
	        }
	        return 1;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			return 0;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return 0;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return 0;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
}
