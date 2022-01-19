package methods.Database;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class connectDB {

    public PreparedStatement statement;

    public Connection connection;

    public connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库引擎加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Reader fr = new FileReader("src/main/resources/db.properties");
            Properties prop = new Properties();
            prop.load(fr);
            String url = prop.getProperty("db_url", "jdbc:mysql://localhost:3306/smart_city");
            String user = prop.getProperty("db_username", "root");
            String password = prop.getProperty("db_password", "root");

            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("连接至智慧城市数据库");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public connectDB(String sql) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库引擎加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Reader fr = new FileReader("src/main/resources/db.properties");
            Properties prop = new Properties();
            prop.load(fr);
            String url = prop.getProperty("db_url", "jdbc:mysql://localhost:3306/smart_city");
            String user = prop.getProperty("db_username", "root");
            String password = prop.getProperty("db_password", "root");

            Connection conn = DriverManager.getConnection(url, user, password);
            this.connection = conn;
            System.out.println("连接至智慧城市数据库");
            this.statement = conn.prepareStatement(sql);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
