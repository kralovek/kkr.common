package kkr.common.utils;

import java.io.Closeable;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import kkr.common.utils.excel.poi.PoiManipulator;
import kkr.common.utils.excel.poi.PoiWorkbook;

public class UtilsResource {

	public static void closeResource(HttpURLConnection closeable) {
		if (closeable != null) {
			try {
				closeable.disconnect();
			} catch (Exception ex) {
				// nothing to do
			}
		}
	}

	public static void closeResource(Socket closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
				// nothing to do
			}
		}
	}

	public static void closeResource(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
				// nothing to do
			}
		}
	}

	public static void closeResource(ResultSet closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
				// nothing to do
			}
		}
	}

	public static void closeResource(Statement closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
				// nothing to do
			}
		}
	}

	public static void closeResource(Connection closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
				// nothing to do
			}
		}
	}

	public static void closeResource(PoiWorkbook poiWorkbook) {
		try {
			PoiManipulator.closeWorkbook(poiWorkbook);
		} catch (Exception ex) {
			// nothing to do
		}
	}
}
