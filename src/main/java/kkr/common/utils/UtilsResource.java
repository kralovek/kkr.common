package kkr.common.utils;

import java.io.Closeable;
import java.net.HttpURLConnection;
import java.net.Socket;

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

	public static void closeResource(AutoCloseable closeable) {
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
}
