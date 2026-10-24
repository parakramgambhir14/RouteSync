package com.routesync.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConnection
 * -------------------
 * This is the ONLY class in the project that knows how to talk JDBC.
 * It reads db.driver / db.url / db.user / db.password from resources/db.properties
 * and hands back a live java.sql.Connection.
 *
 * You should not need to change any code here — just edit resources/db.properties
 * to point at your own database. Everywhere else in the app, get a connection with:
 *
 *      Connection conn = DatabaseConnection.getConnection();
 */
public class DatabaseConnection {

    // Reused across the app instead of opening a brand-new connection every time (simple singleton).
    private static Connection connection = null;

    /** Reads resources/db.properties from the classpath. */
    private static Properties loadConfig() throws IOException {
        Properties props = new Properties();

        // getResourceAsStream looks on the classpath, so make sure the "resources"
        // folder is included when you compile/run (see README.md for the exact command).
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException(
                        "db.properties not found on classpath. Check that the 'resources' " +
                        "folder is included in your classpath when running the app.");
            }
            props.load(input);
        }
        return props;
    }

    /**
     * Returns a live connection to the database described in db.properties.
     * Opens a new one only if none exists yet, or the old one got closed.
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = loadConfig();

                String driver   = props.getProperty("db.driver");
                String url      = props.getProperty("db.url");
                String user     = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                // Explicitly loading the driver class registers it with DriverManager.
                Class.forName(driver);

                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (IOException | ClassNotFoundException e) {
            // Wrap everything as SQLException so callers only need to catch one type.
            throw new SQLException("Could not open database connection: " + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Quick true/false connectivity check.
     * Used by the UI at startup to show a green/red status dot — nothing more.
     */
    public static boolean testConnection() {
        try {
            Connection c = getConnection();
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }

    /** Call this once, when the application is closing, to release the connection cleanly. */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error while closing connection: " + e.getMessage());
        }
    }
}
