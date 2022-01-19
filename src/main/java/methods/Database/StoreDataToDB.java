package methods.Database;

import entity.Dem;
import entity.GridFileHead;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StoreDataToDB {

    public boolean validateTableNameExist(String tableName) {
        connectDB connect = new connectDB();
        ResultSet rs = null;
        try {
            rs = connect.connection.getMetaData().getTables(null, null, tableName, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void storeDemToDB(Dem dem){
        try {
            if (validateTableNameExist("dem")){
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS dem (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "dem double," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            String sql2="insert into dem(id,x,y,dem) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k=0;
            List<List<Double>> demList = dem.getDem();
            GridFileHead head = dem.getHead();
            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for(int i=0;i< demList.size();i++) {
                for (int j = 0; j < demList.get(j).size(); j++) {
                    k=k+1;
                    conn2.statement.setInt(1,k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);
                    conn2.statement.setDouble(4, demList.get(i).get(j));
                    conn2.statement.executeUpdate();
                }
            }
            System.out.println(k);

            System.out.printf("dem已存入数据库");

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void storeRainToDB(Dem dem){
        try {
            if (validateTableNameExist("rain1")){
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS rain1 (" +
                    "id int(4) not null," +
                    "flow double," +
                    "huanglongdai int(4)," +
                    "lianxing int(4)," +
                    "fengmulang int(4)," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            String sql2="insert into rain1(id,flow,huanglongdai,lianxing,fengmulang) values(?,?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            List<List<?>> rainList = dem.getRain();
            for(int i=0;i< rainList.size();i++){
                conn2.statement.setInt(1,i+1);
                conn2.statement.setDouble(2, (double) rainList.get(i).get(1));
                conn2.statement.setInt(3,(int) rainList.get(i).get(2));
                conn2.statement.setInt(4,(int) rainList.get(i).get(3));
                conn2.statement.setInt(5,(int) rainList.get(i).get(4));
                conn2.statement.executeUpdate();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void storeStationToDB(Dem dem){
        try{
            if (validateTableNameExist("station")){
                return;
            }

            String sql1="CREATE TABLE IF NOT EXISTS station (" +
                    "id int(4) not null," +
                    "x  double," +
                    "y  double," +
                    "PRIMARY KEY (`id`)" +
                    ");";

            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立站点属性表成功");
            String sql2="insert into station(id,x,y) values(?,?,?)";
            connectDB conn2 = new connectDB(sql2);

            List<List<?>> stationList= dem.getStation();

            for(int i=0;i<stationList.size();i++){
                conn2.statement.setInt(1,i+1);
                conn2.statement.setDouble(2, (double) stationList.get(i).get(3));
                conn2.statement.setDouble(3, (double) stationList.get(i).get(4));
                conn2.statement.executeUpdate();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

    }
}
