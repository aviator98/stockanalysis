package cn.stockanalysis.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertiesUtil {
	
	@Autowired
    private Environment environment;
	
	private static Environment env;

	private static Map<String, String> envMap = new HashMap<String, String>();
	
	@PostConstruct
    public void init() {
		env = environment;
    }
	
	public static String getEnvElement(String key) {
		if (envMap.containsKey(key)) {
			return envMap.get(key);
		}
		String value = env.getProperty(key);
		envMap.put(key, value);
		return value;
	}
}
