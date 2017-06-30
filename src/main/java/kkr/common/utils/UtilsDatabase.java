package kkr.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class UtilsDatabase {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String adaptQueryForLog(String query, Collection<String> values) {
		if (values != null && !values.isEmpty()) {
			String[] arrValues = values.toArray(new String[values.size()]);
			return adaptQueryForLog(query, arrValues);
		} else {
			return query;
		}
	}

	public static String adaptQueryForLog(String query, String... values) {
		if (values == null || values.length == 0) {
			return query;
		}

		String QM = "QUESTION_MARK";

		query = query.replaceAll("\\?", QM);

		for (String value : values) {
			if (value == null) {
				query = query.replaceFirst(QM, "NULL");
			} else if ("?".equals(value)) {
				query = query.replaceFirst(QM, "?");
			} else {
				query = query.replaceFirst(QM, "'" + value + "'");
			}
		}
		query = query.replaceAll(QM, "?");
		return query;
	}

	public static String adaptQueryForLog(String query, Object... values) {
		if (values == null || values.length == 0) {
			return query;
		}

		String QM = "QUESTION_MARK";

		query = query.replaceAll("\\?", QM);

		for (Object value : values) {
			if (value == null) {
				query = query.replaceFirst(QM, "NULL");
			} else if ("?".equals(value)) {
				query = query.replaceFirst(QM, "?");
			} else {
				if (value instanceof Date) {
					String dateStr = DATE_FORMAT.format(value);
					String valueStr = "TO_DATE('" + dateStr + "', 'YYYYMMDDHH24MISS')";
					query = query.replaceFirst(QM, valueStr);
				} else {
					String valueAdapted = "'" + value.toString() + "'";
					// KKR: there was a problem with String.replaceFirst 
					query = UtilsString.replaceFirst(query, QM, valueAdapted);
				}
			}
		}
		query = query.replaceAll(QM, "?");
		return query;
	}
}
