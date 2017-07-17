package kkr.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import kkr.common.errors.ConfigurationException;

public class UtilsConfig {
	public static Pattern checkPattern(String mask, String name) throws ConfigurationException {
		Pattern retval = null;
		if (mask != null) {
			try {
				retval = Pattern.compile(mask);
			} catch (Exception ex) {
				throw new ConfigurationException("Parameter '" + name + "' has bad value: " + mask, ex);
			}
		}
		return retval;
	}

	public static Collection<Pattern> checkCollectionPatterns(Collection<String> data, String name) throws ConfigurationException {
		Collection<Pattern> retval = new ArrayList<Pattern>();
		if (data != null) {
			retval = new ArrayList<Pattern>();
			int i = 1;
			for (String mask : data) {
				if (mask == null) {
					throw new ConfigurationException("Parameter '" + name + "[" + i + "]' has bad value: " + mask);
				}
				try {
					Pattern pattern = Pattern.compile(mask);
					retval.add(pattern);
				} catch (Exception ex) {
					throw new ConfigurationException("Parameter '" + name + "[" + i + "]' has bad value: " + mask, ex);
				}
				i++;
			}
		}
		return retval;
	}

	public static <T> Map<Pattern, T> checkMapPatterns(Map<String, T> data, String name) throws ConfigurationException {
		Map<Pattern, T> retval = new LinkedHashMap<Pattern, T>();
		if (data == null) {
			// OK
		} else {
			int i = 0;
			for (Map.Entry<String, T> entry : data.entrySet()) {
				if (entry.getValue() == null) {
					throw new ConfigurationException("Parameter '" + name + "[" + i + "]' value is null");
				}
				try {
					Pattern pattern = Pattern.compile(entry.getKey());
					retval.put(pattern, entry.getValue());
				} catch (Exception ex) {
					throw new ConfigurationException("Parameter '" + name + "[" + i + "]' has bad key: " + entry.getKey());
				}
				i++;
			}
		}
		return retval;
	}
}
