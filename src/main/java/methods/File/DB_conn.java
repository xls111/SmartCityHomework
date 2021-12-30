package methods.File;

import java.io.*;
import java.sql.*;

public class DB_conn {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库引擎加载成功");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        PreparedStatement pstmt;
        ResultSet rs;
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_city","root","lz218218");
        System.out.println("连接至智慧城市数据库");
        Statement stat = conn.createStatement();
        System.out.println("statement对象建立成功");

        String sql = "insert into dem_file(id,filename,data) values(?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,"1");
        pstmt.setString(2,"dem.asc");
        InputStream input = new FileInputStream("D:\\文件\\课件\\智慧城市建设原理与方法\\work\\data\\dem.asc");
        pstmt.setBinaryStream(3,input);
        pstmt.executeUpdate();
    }
}
