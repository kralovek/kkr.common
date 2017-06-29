package kkr.common.utils.excel;

import java.io.File;

public class ExcelPosition implements Cloneable {
    private File file;

    private String sheet;

    private Integer column;

    private Integer row;

    public File getFile() {
        return file;
    }

    public ExcelPosition setFile(File file) {
        this.file = file;
        return this;
    }

    public String getSheet() {
        return sheet;
    }

    public ExcelPosition setSheet(String sheet) {
        this.sheet = sheet;
        return this;
    }

    public Integer getColumn() {
        return column;
    }

    public ExcelPosition setColumn(Integer column) {
        this.column = column;
        return this;
    }

    public Integer getRow() {
        return row;
    }

    public ExcelPosition setRow(Integer row) {
        this.row = row;
        return this;
    }

    public String toString() {
        return "[(" //
                + (file != null ? file.getAbsoluteFile() : "") //
                + ") " //
                + "{" + (sheet != null ? sheet : "") + "}" //
                + " " //
                + toCellId(column, row) + "]";
    }

    public static String toCellId(Integer pColumn, Integer pRow) {
        String column;
        String row;
        if (pColumn != null) {
            if (pColumn <= 'Z' - 'A') {
                column = String.valueOf((char) ((int) 'A' + pColumn));
            } else {
                int range = ('Z' - 'A') + 1;
                int A = (int) 'A';
                int c1 = (pColumn) / range - 1;
                int c2 = (pColumn - (c1 + 1) * range);
                column = String.valueOf((char) (A + c1))
                        + String.valueOf((char) (A + c2));
            }
        } else {
            column = "?";
        }
        if (pRow != null) {
            row = String.valueOf(pRow + 1);
        } else {
            row = "?";
        }
        return column + row;
    }

    public ExcelPosition clone() {
        ExcelPosition ep = new ExcelPosition();
        ep.file = file;
        ep.row = row;
        ep.sheet = sheet;
        ep.column = column;
        return ep;
    }
}
