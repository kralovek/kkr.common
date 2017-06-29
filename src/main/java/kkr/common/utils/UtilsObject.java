package kkr.common.utils;

public class UtilsObject {
	public static <T> int compare(Comparable<T> object1, T object2) {
		if (object1 != null && object2 != null) {
			return object1.compareTo(object2);
		}
		if (object1 != null) {
			return -1;
		}
		if (object2 != null) {
			return +1;
		}
		return 0;
	}
}
