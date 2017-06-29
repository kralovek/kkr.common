package kkr.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

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
}
