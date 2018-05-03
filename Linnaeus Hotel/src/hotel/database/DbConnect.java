
package hotel.database;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnect {
    private static final String connection_url = "jdbc:mysql://eu-cdbr-west-02.cleardb.net:3306/heroku_039d3586b62093c";
    private final String username = "b93a451b88a7b2";
    private final String password = "676a64b2";
    private Connection connectionDB;
    public static final String dbName = "heroku_039d3586b62093c";
    //Old database and connection. If needed for any reason.
    //    private static final String connection_url = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11225424";
    //    private final String username = "sql11225424";
    //    private final String password = "uWhgJij7AD";
    //    private Connection connectionDB;
    //    public static final String dbName = "sql11225424";

    public DbConnect() {
    }

    public ResultSet execute(String sqlQuery) {
        if (connectionDB == null)
            Connection();
        ResultSet rs = null;
        try {
            PreparedStatement stat = connectionDB.prepareStatement(sqlQuery);
            stat.execute();
            rs = stat.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet executeWithParameters(String sqlQuery, ArrayList parameters) {
        if (connectionDB == null)
            Connection();
        ResultSet rs = null;
        try {
            PreparedStatement stat = connectionDB.prepareStatement(sqlQuery);
            for (int i = 1; i <= parameters.size(); i++) {
                stat.setString(i, parameters.get(i - 1).toString());
            }
            stat.execute();
            rs = stat.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet update(String sqlQuery) {
        if (connectionDB == null)
            Connection();
        ResultSet rs = null;
        try {
            PreparedStatement stat = connectionDB.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stat.execute();
            rs = stat.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private void Connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", username);
            user.put("password", password);
            connectionDB = DriverManager.getConnection(connection_url, user);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connectionDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}