package kkr.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;

public class UtilsString {
	public static String listToString(Collection<String> values, String before, String after, String separator) {
		StringBuffer buffer = new StringBuffer();
		for (String value : values) {
			if (buffer.length() != 0) {
				buffer.append(separator);
			}
			if (before != null) {
				buffer.append(before);
			}
			buffer.append(value);
			if (after != null) {
				buffer.append(after);
			}
		}
		return buffer.toString();
	}

	public static String cutStringToBytesLength(String text, int toLength) {
		if (text == null) {
			return text;
		}
		try {
			int lengthBytes = text.getBytes("UTF8").length;
			int diff = lengthBytes - toLength;
			if (diff > 0) {
				int newLength = text.length() - diff;
				if (newLength > 0) {
					return text.substring(0, newLength - 1);
				} else {
					return "";
				}
			}
		} catch (UnsupportedEncodingException ex) {
			// Never happens
		}
		return text;
	}

	public static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	public static String tabBlock(String block, String tab) {
		if (block.isEmpty()) {
			return "";
		}
		block = tab + block.replace("\n", "\n" + tab);
		return block;
	}

	public static String right(String string, int index) {
		return string == null ? null : string.substring(string.length() - Math.min(string.length(), index));
	}

	public static String toStringDateDelta(Date dateBegin, Date dateEnd) {
		long delta = dateEnd.getTime() - dateBegin.getTime();

		long ms = delta % 1000;
		delta = (delta - ms) / 1000;

		long sec = delta % 60;
		delta = (delta - sec) / 60;

		if (delta == 0) {
			return String.format("%d.%03d", sec, ms);
		}

		long min = delta % 60;
		delta = (delta - min) / 60;

		if (delta == 0) {
			return String.format("%d:%02d.%03d", min, sec, ms);
		}

		long hour = delta % 24;
		delta = (delta - hour) / 24;

		if (delta == 0) {
			return String.format("%d:%02d:%02d.%03d", hour, min, sec, ms);
		}

		long day = delta;
		return String.format("%d days %d:%02d:%02d.%03d", day, hour, min, sec, ms);
	}

	public static String replaceFirst(String text, String key, String value) {
		if (text == null || key == null) {
			return text;
		}
		int iPos = text.indexOf(key);
		if (iPos == -1) {
			return text;
		}
		return text.substring(0, iPos) + value + text.substring(iPos + key.length());
	}

	public static String toStringException(Throwable ex) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		ex.printStackTrace(printStream);
		printStream.close();
		return byteArrayOutputStream.toString();
	}
}
