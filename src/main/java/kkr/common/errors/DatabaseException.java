package kkr.common.errors;

import kkr.common.components.datasource.DataSource;

public class DatabaseException extends TechnicalException {

	private DataSource dataSource;

	public DatabaseException(DataSource dataSource, String message, Throwable cause) {
		super(message, cause);
		this.dataSource = dataSource;
	}

	public DatabaseException(DataSource dataSource, String message) {
		super(message);
		this.dataSource = dataSource;
	}

	public DatabaseException(DataSource dataSource, Throwable cause) {
		super(cause);
		this.dataSource = dataSource;
	}

	public String getMessage() {
		String message = super.getMessage();
		message = message != null ? message : "";
		return (dataSource != null ? dataSource.toString() + " " : "") + message;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
