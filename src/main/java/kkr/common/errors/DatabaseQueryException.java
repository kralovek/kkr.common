package kkr.common.errors;

import kkr.common.components.datasource.DataSource;

public class DatabaseQueryException extends DatabaseException {

	private String query;

	public DatabaseQueryException(DataSource dataSource, String query, String message, Throwable cause) {
		super(dataSource, message, cause);
		this.query = query;
	}

	public DatabaseQueryException(DataSource dataSource, String query, String message) {
		super(dataSource, message);
		this.query = query;
	}

	public DatabaseQueryException(DataSource dataSource, String query, Throwable cause) {
		super(dataSource, cause);
		this.query = query;
	}

	public String getMessage() {
		return super.getMessage() + "\nQUERY: " + query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
