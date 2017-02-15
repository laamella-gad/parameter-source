package com.laamella.parameter_source;

import java.sql.*;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Queries a database table for values using JDBC.
 */
public class JdbcDatabaseParameterSource extends StringParameterSource {
    private final String selectStatement;
    private final Supplier<Connection> connectionSupplier;

    private static class JdbcConnectionSupplier implements Supplier<Connection> {
        private final String jdbcUrl;
        private final String username;
        private final String password;

        private JdbcConnectionSupplier(String jdbcUrl, String username, String password) {
            this.jdbcUrl = jdbcUrl;
            this.username = username;
            this.password = password;
        }

        @Override
        public Connection get() {
            try {
                return DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException e) {
                throw new ParameterSourceException(e, "Cannot get a connection to %s.", jdbcUrl);
            }
        }
    }

    /**
     * @param connectionSupplier the factory for JDBC connections.
     * @param selectStatement the query that results in a one column, one or zero row result set that contains the value
     * for the single stored procedure parameter that stores the key.
     */
    public JdbcDatabaseParameterSource(Supplier<Connection> connectionSupplier, String selectStatement) {
        this.connectionSupplier = connectionSupplier;
        this.selectStatement = selectStatement;
    }

    /**
     * @param jdbcUrl the JDBC url for the connection.
     * @param username the user name for the JDBC connection.
     * @param password the password for the JDBC connection.
     * @param selectStatement the query that results in a one column, one or zero row result set that contains the
     * value. for the single stored procedure parameter that stores the key.
     */
    public JdbcDatabaseParameterSource(String jdbcUrl, String username, String password, String selectStatement) {
        this(new JdbcConnectionSupplier(jdbcUrl, username, password), selectStatement);
    }

    /**
     * @param jdbcUrl the JDBC url for the connection.
     * @param username the user name for the JDBC connection.
     * @param password the password for the JDBC connection.
     * @param tableName the table that contains the configuration.
     * @param keyColumnName the name of the column that contains the keys.
     * @param valueColumnName the name of the column that contains the values.
     */
    public JdbcDatabaseParameterSource(String jdbcUrl, String username, String password, String tableName, String keyColumnName, String valueColumnName) {
        this(new JdbcConnectionSupplier(jdbcUrl, username, password), String.format("SELECT %s FROM %s WHERE %s=?", valueColumnName, tableName, keyColumnName));
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        try (Connection connection = connectionSupplier.get()) {
            try (PreparedStatement statement = connection.prepareStatement(selectStatement)) {
                statement.setString(1, key);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(resultSet.getString(1));
                    } else {
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new ParameterSourceException(e, "Cannot get a parameter value with query %s.", selectStatement);
        }
    }
}
