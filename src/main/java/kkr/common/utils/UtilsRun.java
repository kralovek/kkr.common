package kkr.common.utils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.apache.log4j.Logger;

import kkr.common.main.Config;

public class UtilsRun {
	private static final Logger LOG = Logger.getLogger(UtilsRun.class);

	public static void printClasspath() {
		LOG.trace("BEGIN");
		try {
			ClassLoader cl = ClassLoader.getSystemClassLoader();

			URL[] urls = ((URLClassLoader) cl).getURLs();

			LOG.info("CLASSPATH:");
			for (URL url : urls) {
				LOG.info("\t" + url.getFile());
			}

			LOG.trace("OK");
		} finally {
			LOG.trace("END");
		}
	}

	public static void printMainHeader(Class<?> clazz, String[] args) {
		printMainHeader(clazz, args);
	}

	public static void printMainHeader(Class<?> clazz, String[] args, Config config) {
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		List<String> jvmArgs = runtimeMxBean.getInputArguments();

		LOG.info("##################################################################################");
		LOG.info("### CLASS: " + clazz.getName());
		LOG.info("### PARAM:\t" + toString(args));
		LOG.info("###===============================================================================");
		LOG.info("### JVM:\t" + toString(jvmArgs));
		LOG.info("##################################################################################");
		try {
			File currentDir = new File(".").getCanonicalFile();
			LOG.info("currentDir: " + currentDir.getAbsolutePath());
		} catch (Exception ex) {
			//
		}
	}

	private static String toString(String[] args) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(" [").append(i).append("] ").append(args[i]);
		}
		return buffer.toString();
	}

	private static String toString(List<String> arguments) {
		StringBuffer buffer = new StringBuffer();
		for (String argument : arguments) {
			buffer.append(" ").append(argument);
		}
		return buffer.toString();
	}
}
