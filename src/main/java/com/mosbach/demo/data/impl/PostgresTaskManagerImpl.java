package com.mosbach.demo.data.impl;

import com.mosbach.demo.data.api.TaskManager;
import com.mosbach.demo.model.student.Student;
import com.mosbach.demo.model.task.Task;
import org.apache.commons.dbcp.BasicDataSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

public class PostgresTaskManagerImpl implements TaskManager  {

    // Make sure you build with 	implementation group: 'org.postgresql', name: 'postgresql', version: '42.7.2' or higher

    String databaseConnectionnUrl = "postgresql://WRONG:WRONG@c8m0261h0c7idk.cluster-czrs8kj4isg7.us-east-1.rds.amazonaws.com:5432/WRONG";
    URI dbUri;
    String dbUrl = "";
    String username = "";
    String password = "";

    static PostgresTaskManagerImpl postgresTaskManager = null;

    private PostgresTaskManagerImpl() {
    try {
            dbUri = new URI(databaseConnectionnUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];
        dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
    }

    static public PostgresTaskManagerImpl getPostgresTaskManagerImpl() {
        if (postgresTaskManager == null)
            postgresTaskManager = new PostgresTaskManagerImpl();
        return postgresTaskManager;
    }

    @Override
    public List<Task> getAllTasks(String email) {
        return null;
    }

    @Override
    public void addTask(Task task, String studentEmail) {
        Statement stmt = null;
        Connection connection = null;
    }

    @Override
    public void deleteTask(String name, Student student) {
    }

    public void createTableTask() {
        Statement stmt = null;
        Connection connection = null;
        Logger myLogger = Logger.getLogger("PostgresManagerLogger");

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            stmt = connection.createStatement();
            myLogger.info("BIN DRIN");

            String dropTable = "DROP TABLE IF EXISTS group24tasks";
            stmt.executeUpdate(dropTable);

            String createTable = "CREATE TABLE group24tasks (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name varchar(100) NOT NULL, " +
                    "priority int NOT NULL)";
            stmt.executeUpdate(createTable);

            /*
            String createTable = "CREATE TABLE IF NOT EXISTS tasks (id integer autoincrement," +
                    "email varchar(20), name varchar(20), module varchar(20), " +
                    "deadline varchar(20), recipient varchar(20), description varchar(20),"+
                    "location varchar(20), priority integer, grade decimal," +
                    "durationInHours integer, status varchar(20))";
            */
            myLogger.info("SQL Statement " + createTable);
            stmt.executeUpdate(createTable);

            myLogger.info("Created ");
            stmt.close();
            connection.close();

        } catch (Exception e) {
            myLogger.info("Error " + e);
        }

    }
}
