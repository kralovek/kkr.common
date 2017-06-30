package kkr.common.errors;

import kkr.common.utils.excel.ExcelPosition;

public class ExcelException extends ConfigurationException {
	private static final long serialVersionUID = -2222237289970246694L;

	private ExcelPosition position;

	public ExcelException(ExcelPosition excelPosition, String message) {
		super(message);
		this.position = excelPosition;
	}

	public ExcelException(ExcelPosition excelPosition, String message, Throwable cause) {
		super(message, cause);
		this.position = excelPosition;
	}

	public ExcelPosition getPosition() {
		return position;
	}

	public String getMessage() {
		return (position != null ? position.toString() : "[?] ") //
				+ " - " + (super.getMessage() != null ? super.getMessage() : "");
	}
}
