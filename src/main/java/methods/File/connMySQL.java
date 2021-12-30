package methods.File;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class connMySQL {
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
//        stat.executeUpdate("drop database if exists grid");
//        stat.executeUpdate("create database grid");
//        System.out.println("grid创建成功");
//        stat.close();
//        conn.close();
//        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grid","root","lz218218");
//        System.out.println("Connected to database grid!");
//        stat = conn.createStatement();
//        System.out.println("Connected to object statement!");
//        stat.executeUpdate("drop table if exists GaugeProperty");
//        stat.executeUpdate("create table GaugeProperty(id int(3),name varchar(10),sid char(4), x double, y double, enname varchar(10))");
//        System.out.println("Created GaugeProperty!");
//        stat.close();
//        conn.close();


        String sql = "insert into dem_file(id,filename,data) values(?,?,?)";
//        pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1,"1");
//        pstmt.setString(2,"dem.asc");
//        InputStream in = new FileInputStream("D:\\文件\\课件\\智慧城市建设原理与方法\\work\\data\\dem.asc");
//        pstmt.setBinaryStream(3,in);
//        pstmt.executeUpdate();
        sql = "select filename,data from dem_file where id =?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,1);

        rs = pstmt.executeQuery();
        rs.next();
        String filename = rs.getString("filename");
        InputStream input = rs.getBinaryStream("data");

        Direction2.GridFileHead grid=new Direction2.GridFileHead();
        try{
            DataInputStream in=new DataInputStream(input);
            int fileSize=512;
            byte []btData=new byte[fileSize];
            in.read(btData);
            String str=new String(btData);
            String splitTab="\t";
            String []strData=str.split("\n");
            int tabIndex=strData[0].indexOf(splitTab);
            if(tabIndex==-1){
                splitTab=" ";
            }
            String []tempData=strData[0].split(splitTab);
            grid.ncols=Integer.parseInt(tempData[tempData.length-1].trim());
            tempData=strData[1].split(splitTab);
            grid.nrows=Integer.parseInt(tempData[tempData.length-1].trim());
            tempData=strData[2].split(splitTab);
            grid.xllcorner=Double.parseDouble(tempData[tempData.length-1].trim());
            tempData=strData[3].split(splitTab);
            grid.yllcorner=Double.parseDouble(tempData[tempData.length-1].trim());
            tempData=strData[4].split(splitTab);
            grid.cellsize=Double.parseDouble(tempData[tempData.length-1].trim());
            tempData=strData[5].split(splitTab);
            grid.NODATA_value=Integer.parseInt(tempData[tempData.length-1].trim());
            in.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        double[][] ter=null;
        try{
            DataInputStream in=new DataInputStream(input);
            int fileSize=in.available();
            byte []btData=new byte[fileSize];
            in.read(btData);
            String str=new String(btData);
            String splitTab="\t";
            String []strData=str.split("\n");
            int tabIndex=strData[0].indexOf(splitTab);
            if(tabIndex==-1){
                splitTab=" ";
            }
            String []tempData=strData[0].split(splitTab);
            int ncols=Integer.parseInt(tempData[tempData.length-1].trim());
            tempData=strData[1].split(splitTab);
            int nrows=Integer.parseInt(tempData[tempData.length-1].trim());
            ter=new double[nrows][ncols];
            splitTab="\t";
            tabIndex=strData[6].indexOf(splitTab);
            if(tabIndex==-1){
                splitTab=" ";
            }
            for(int i=0;i<nrows;i++){
                tempData=strData[i+6].split(splitTab);
                for(int j=0;j<ncols;j++){
                    ter[i][j] = Double.parseDouble(tempData[j]);
                }
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println();
    }
}
