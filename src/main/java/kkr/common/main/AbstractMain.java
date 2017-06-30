package kkr.common.main;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

import kkr.common.errors.BaseException;
import kkr.common.errors.ConfigurationException;
import kkr.common.utils.UtilsBean;

public abstract class AbstractMain {
	private static final Logger LOG = Logger.getLogger(AbstractMain.class);

	public static <T> T createBeanNoLog(Config config, Class<T> clazz, String configDefault, String beanDefault) throws BaseException {
		T retval = createBean(true, config, clazz, configDefault, beanDefault, null, null);
		return retval;
	}

	public static <T> T createBeanNoLog(Config config, Class<T> clazz, String configDefault, String beanDefault, Collection<String> propertiesListExt,
			Map<String, String> parametersExt) throws BaseException {
		T retval = createBean(true, config, clazz, configDefault, beanDefault, propertiesListExt, parametersExt);
		return retval;
	}

	public static <T> T createBean(Config config, Class<T> clazz, String configDefault, String beanDefault) throws BaseException {
		LOG.trace("BEGIN: " + clazz.getName());
		try {
			T retval = createBean(true, config, clazz, configDefault, beanDefault, null, null);
			LOG.trace("OK");
			return retval;
		} finally {
			LOG.trace("END");
		}
	}

	public static <T> T createBean(Config config, Class<T> clazz, String configDefault, String beanDefault, Collection<String> propertiesListExt,
			Map<String, String> parametersExt) throws BaseException {
		LOG.trace("BEGIN: " + clazz.getName());
		try {
			T retval = createBean(true, config, clazz, configDefault, beanDefault, propertiesListExt, parametersExt);
			LOG.trace("OK");
			return retval;
		} finally {
			LOG.trace("END: " + clazz.getName());
		}
	}

	private static <T> T createBean(boolean log, Config config, Class<T> clazz, String configDefault, String beanDefault,
			Collection<String> propertiesListExt, Map<String, String> parametersExt) throws BaseException {
		if (log) {
			LOG.trace("BEGIN: " + clazz.getName());
		}
		try {
			try {
				Map<String, String> parameters = new HashMap<String, String>();
				if (parametersExt != null) {
					parameters.putAll(parametersExt);
				}

				List<String> propertiesList = new ArrayList<String>();
				propertiesList.addAll(config.getProperties());

				if (propertiesListExt != null) {
					propertiesList.addAll(propertiesListExt);
				}

				String configFile = config.getConfig() != null ? config.getConfig() : configDefault;

				BeanFactory beanFactory = UtilsBean.createBeanFactory(configFile, propertiesList, parameters);

				String idBean = config.getBean() != null ? config.getBean() : beanDefault;

				T bean = beanFactory.getBean(idBean, clazz);
				if (log) {
					LOG.trace("OK");
				}
				return bean;
			} catch (BeansException ex) {
				throw new ConfigurationException("Cannot load the bean: " + (config.getBean() != null ? config.getBean() : beanDefault), ex);
			}
		} finally {
			if (log) {
				LOG.trace("END");
			}
		}
	}

	public <TMain, TConfig> TConfig config(Class<TMain> classMain, Class<TConfig> classConfig, String[] args) throws ConfigurationException {
		Collection<String> collectionArgs = Arrays.asList(args);
		ConfigurationException configurationException = null;
		Config config = null;
		try {
			Constructor<TConfig> constructor = classConfig.getConstructor(Collection.class);
			TConfig tConfig = constructor.newInstance(collectionArgs);
			config = (Config) tConfig;
			initializeLog(config);
		} catch (Exception ex) {
			configurationException = new ConfigurationException("Cannot create config: " + classConfig.getName(), ex);
		}

		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		List<String> jvmArgs = runtimeMxBean.getInputArguments();

		LOG.info("##################################################################################");
		LOG.info("### CLASS: " + classMain.getName());
		LOG.info("### PARAM:\t" + toStringArgs(args));
		LOG.info("###===============================================================================");
		LOG.info("### JVM:\t" + toStringArgs(jvmArgs));
		try {
			LOG.info("###===============================================================================");
			File currentDir = new File(".").getCanonicalFile();
			LOG.info("### CurrentDir: " + currentDir.getAbsolutePath());
		} catch (Exception ex) {
			//
		}
		LOG.info("##################################################################################");
		if (configurationException != null) {
			throw configurationException;
		}
		return (TConfig) config;
	}

	private static String toStringArgs(String[] args) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(" [").append(i).append("] ").append(args[i]);
		}
		return buffer.toString();
	}

	private static String toStringArgs(Collection<String> arguments) {
		StringBuffer buffer = new StringBuffer();
		for (String argument : arguments) {
			buffer.append(" ").append(argument);
		}
		return buffer.toString();
	}

	protected static void initializeLog(Config config) {
		if (config.getConfigLog() != null) {
			PropertyConfigurator.configure(config.getConfigLog());
			LOG.info("Log initialised from file: " + config.getConfigLog());
		}
	}

	protected static void printOutput(String output) {
		System.out.print(output);
	}

	protected static void printError(String output) {
		System.err.print(output);
	}
}
