package kkr.common.utils.excel;

import kkr.common.errors.ConfigurationException;

public class ExcelConfigurationException extends ConfigurationException {
    private ExcelPosition position;

    public ExcelConfigurationException(ExcelPosition excelPosition,
            String message) {
        super(message);
        this.position = excelPosition;
    }

    public ExcelConfigurationException(ExcelPosition excelPosition,
            String message, Throwable cause) {
        super(message, cause);
        this.position = excelPosition;
    }

    public ExcelPosition getPosition() {
        return position;
    }

    public String getMessage() {
        return (position != null ? position.toString() : "[?] ") //
                + " - "
                + (super.getMessage() != null ? super.getMessage() : "");
    }
}