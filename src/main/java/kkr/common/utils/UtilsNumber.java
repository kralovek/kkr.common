package kkr.common.utils;

public class UtilsNumber {

	public static Number reduceNumber(Number number) {
		if (number == null) {
			return null;
		}
		double d = number.doubleValue();
		long l = number.longValue();
		if (d == (double) l) {
			short s = number.shortValue();
			if (s == (short) l) {
				return s;
			} else if (number.intValue() == (int) l) {
				return number.intValue();
			} else {
				return l;
			}
		} else {
			if (number instanceof Float) {
				return Double.parseDouble(number.toString());
			}
			return number.doubleValue();
		}
	}
}
