package kkr.common.components.datasource;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource extends javax.sql.DataSource {
    Connection getConnection() throws SQLException;

    String getName();

    String getSchema();
}
