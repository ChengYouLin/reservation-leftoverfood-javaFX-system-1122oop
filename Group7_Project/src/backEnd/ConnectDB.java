package backEnd;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDB {
    private String server = "jdbc:mysql://140.119.19.73:3315/";
    private String database = "111304019"; // change to your own database
    protected String url = server + database + "?useSSL=false";
    private String username = "111304019"; // change to your own username
    private String password = "bovpk"; // change to your own password

    public ConnectDB() {}

    public ArrayList<Object> connectDB(String query, String condition) {
        ArrayList<Object> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stat = conn.createStatement()) {
            System.out.println("DB Connected");
            ResultSet resultSet = stat.executeQuery(query);
            result = showResultSet(resultSet, condition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean insertDeleteDB(String query) {
        boolean success = false;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stat = conn.createStatement()) {
            System.out.println("DB Connected");
            stat.execute(query);
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public ArrayList<Object> showResultSet(ResultSet resultSet, Object condition) throws SQLException {
        ArrayList<Object> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(resultSet.getObject(condition.toString()));
        }
        return result;
    }

    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stat = conn.createStatement()) {
            System.out.println("DB Connected");
            resultSet = stat.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ArrayList<ArrayList<Object>> fetchAll(String query) {
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stat = conn.createStatement();
             ResultSet resultSet = stat.executeQuery(query)) {
            System.out.println("DB Connected");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
