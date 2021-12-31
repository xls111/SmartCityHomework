package methods.Database;

import domain.GridFileHead;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class GetData {
    public static void main(String[] args) throws SQLException, IOException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库引擎加载成功");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        Properties props = new Properties();
        FileInputStream fis;
        fis = new FileInputStream("src/main/resources/db.properties");
        props.load(fis);

        PreparedStatement pstmt;
        ResultSet rs;
        Connection conn = DriverManager.getConnection(props.getProperty("db_url","jdbc:mysql://localhost:3306/smart_city"),props.getProperty("db_username","root"),props.getProperty("db_password","root"));
        System.out.println("连接至智慧城市数据库");
        Statement stat = conn.createStatement();
        System.out.println("statement对象建立成功");

        String sql = "select filename,data from dem_file where id =?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,1);

        rs = pstmt.executeQuery();
        rs.next();
        String filename = rs.getString("filename");
        InputStream input = rs.getBinaryStream("data");

        GridFileHead grid=new GridFileHead();
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

        double[][] ter;
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
