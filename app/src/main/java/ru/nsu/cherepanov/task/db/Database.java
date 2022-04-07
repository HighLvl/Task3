package ru.nsu.cherepanov.task.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Database {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_NAME = "osm";
    private static final String SERVER_URL = "jdbc:postgresql://127.0.0.1:5432/";
    private static final String DB_URL = SERVER_URL + DB_NAME;
    private static final String USER = "postgres";
    private static final String PASS = "root";
    private static Connection connection;
    private static final Logger logger = LogManager.getLogger(Database.class);

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(SERVER_URL, USER, PASS);
        var statement = connection.createStatement();
        if (isDbAlreadyExists()) {
            logger.info("Db already exists");
            var terminateQuery = "SELECT pg_terminate_backend(pid) " +
                    "FROM pg_stat_activity " +
                    "WHERE datname = '" + DB_NAME + "';";
            statement.executeQuery(terminateQuery);

            logger.info("Drop db");
            var dropDbQuery = "DROP DATABASE " + DB_NAME;
            statement.executeUpdate(dropDbQuery);
        }
        logger.info("Create db");
        var createDbQuery = "CREATE DATABASE " + DB_NAME;
        statement.executeUpdate(createDbQuery);
        logger.info("Create tables");
        connection.close();
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        createTables();
        return connection;
    }

    private boolean isDbAlreadyExists() throws SQLException {
        var ps = connection.prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
        var resultSet = ps.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(DB_NAME)) {
                return true;
            }
        }
        return false;
    }


    private void createTables() throws SQLException {
        var statement = connection.createStatement();
        logger.info("Create tables begin " + connection.getCatalog());
        var query = "CREATE EXTENSION hstore;\n" +
                "CREATE TYPE member AS (type varchar, ref bigint, role varchar);\n" +
                "CREATE TABLE relation (id int8 NOT NULL,\n" +
                "                user_name varchar NOT NULL,\n" +
                "                uid bigint NOT NULL,\n" +
                "                version bigint NOT NULL,\n" +
                "                changeset bigint NOT NULL,\n" +
                "                timestamp timestamp NOT NULL,\n" +
                "                members member[] NOT NULL,\n" +
                "                tags hstore NOT NULL,\n" +
                "                PRIMARY KEY (id));\n" +
                "\t\t\t\t\n" +
                "CREATE TABLE Node (\n" +
                "                id bigint NOT NULL,\n" +
                "                lat float8 NOT NULL,\n" +
                "                lon float8 NOT NULL,\n" +
                "                user_name varchar NOT NULL,\n" +
                "                uid bigint NOT NULL,\n" +
                "                version bigint NOT NULL,\n" +
                "                changeset bigint NOT NULL,\n" +
                "                timestamp timestamp NOT NULL,\n" +
                "\t            tags hstore NOT NULL,\n" +
                "                PRIMARY KEY (id));\n" +
                "CREATE TABLE Way (\n" +
                "                id bigint NOT NULL,\n" +
                "                user_name varchar NOT NULL,\n" +
                "                uid bigint NOT NULL,\n" +
                "                version bigint NOT NULL,\n" +
                "                changeset bigint NOT NULL,\n" +
                "                timestamp timestamp NOT NULL,\n" +
                "\t            refs bigint[] NOT NULL,\n" +
                "\t\t        tags hstore NOT NULL,\n" +
                "                PRIMARY KEY (id));\t\t\t\t";
        statement.executeUpdate(query);
        logger.info("Tables created");
    }
}
