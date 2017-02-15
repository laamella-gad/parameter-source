package com.laamella.parameter_source;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class JdbcDatabaseParameterSourceTest {
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    @BeforeClass
    public static void setupDatabase() throws SQLException, ClassNotFoundException {
        try (Connection connection = getDBConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE CONFIG(key varchar primary key, value varchar)");
                statement.execute("INSERT INTO CONFIG(key, value) VALUES('abc', 'def')");
            }
            connection.commit();
        }
    }

    @Test
    public void getExistingValue() {
        final JdbcDatabaseParameterSource source = new JdbcDatabaseParameterSource(JDBC_URL, "", "", "config", "key", "value");

        final String value = source.getString("abc");

        assertEquals("def", value);
    }

    @Test
    public void getNonExistingValue() {
        final JdbcDatabaseParameterSource source = new JdbcDatabaseParameterSource(JDBC_URL, "", "", "config", "key", "value");

        final Optional<String> value = source.getOptionalString("qix");

        assertEquals(false, value.isPresent());
    }

    private static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(JDBC_URL, "", "");
    }
}
