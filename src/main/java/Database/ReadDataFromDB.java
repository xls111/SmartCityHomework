package Database;

import entity.GridFileHead;
import entity.RainSite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadDataFromDB {
    public double[][] readDemFromDB(GridFileHead head) {
        try {
            String sql1 = "select * from dem";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            double[][] demData = new double[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                System.out.println(p);
                int q = id - p * cols - 1;
                demData[p][q] = resultSet.getDouble("dem");
            }

            return demData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[0][];
    }

    public RainSite[][] initSitesFromDB(GridFileHead head){
        try {
            String sql1 = "select * from dem";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            RainSite[][] sitesData = new RainSite[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                sitesData[p][q] = new RainSite();
                double x=resultSet.getDouble("x");
                sitesData[p][q].setX(x);
                double y=resultSet.getDouble("y");
                sitesData[p][q].setY(y);
                double elevation = resultSet.getDouble("dem");
                sitesData[p][q].setElevation(elevation);
            }
            return sitesData;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RainSite[0][];
    }

    public List<List<?>> readStationFromDB() {
        try {
            String sql1 = "select * from station";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            List<List<?>> station = new ArrayList<>();

            while (resultSet.next()) {
                List<Object> tempList = new ArrayList<>();
                int id = resultSet.getInt("id");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                tempList.add(id);
                tempList.add(x);
                tempList.add(y);
                station.add(tempList);
                System.out.println("id:" + id + "x:" + x + "y:" + y);
            }
            return station;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<List<?>> readRainFromDB() {
        try {
            String sql1 = "select * from rain1";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            List<List<?>> rain = new ArrayList<>();
            while (resultSet.next()) {
                List<Object> tempList = new ArrayList<>();
                int id = resultSet.getInt("id");
                double flow = resultSet.getDouble("flow");
                int huanglongdai = resultSet.getInt("huanglongdai");
                int lianxing = resultSet.getInt("lianxing");
                int fengmulang = resultSet.getInt("fengmulang");
                tempList.add(id);
                tempList.add(flow);
                tempList.add(huanglongdai);
                tempList.add(lianxing);
                tempList.add(fengmulang);
                rain.add(tempList);
                System.out.println("id:" + id + "flow:" + flow);
            }
            return rain;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
