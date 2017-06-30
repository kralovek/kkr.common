package kkr.common.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kkr.common.errors.ConfigurationException;

public class Config {
	protected List<String> propertiesFiles = new ArrayList<String>();
	protected Map<String, String> parameters = new LinkedHashMap<String, String>();
	protected String config;
	protected String bean;
	protected String configLog;

	public Config(Collection<String> collectionArgs) throws ConfigurationException {
		String[] args = collectionArgs.toArray(new String[collectionArgs.size()]);
		config(args);
	}

	private void config(String[] args) throws ConfigurationException {
		if (args != null) {
			for (int i = 0; i < args.length; i += 2) {
				String paramName = args[i];
				if (i + 1 >= args.length) {
					throw new ConfigurationException("Command line parameter '" + paramName + "' does not have a value");
				}
				String paramValue = args[i + 1];
				if (!configureParameter(paramName, paramValue)) {
					throw new ConfigurationException("Unknown commandline parameter: " + paramName);
				}
			}
		}
	}

	protected boolean configureParameter(String paramName, String paramValue) throws ConfigurationException {
		if ("-bean".equals(paramName)) {
			bean = paramValue;
			return true;
		} else if ("-configLog".equals(paramName)) {
			configLog = paramValue;
			return true;
		} else if ("-config".equals(paramName)) {
			config = paramValue;
			return true;
		} else if ("-propertiesFiles".equals(paramName)) {
			String[] parts = paramValue.split("\\|");
			for (String part : parts) {
				part = part.trim();
				if (part.isEmpty()) {
					throw new ConfigurationException("Command line parameter '-propertiesFiles' has bad value in part: " + part);
				}
				propertiesFiles.add(part);
			}
			return true;
		} else if (paramName.startsWith("-parameter:")) {
			String propertyName = paramName.substring("-parameter:".length());
			if (propertyName.isEmpty() || !propertyName.matches("[_a-zA-Z][a-zA-Z0-9]*")) {
				throw new ConfigurationException("Command line parameter '-parameter' has bad value in propertyName part: '" + propertyName
						+ "'. Syntax is -parameter:parameterName=parameterValue");
			}
			parameters.put(propertyName, paramValue);
			return true;
		} else {
			return false;
		}
	}

	public String getConfig() {
		return config;
	}

	public List<String> getProperties() {
		return propertiesFiles;
	}

	public String getBean() {
		return bean;
	}

	public String getConfigLog() {
		return configLog;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
}
