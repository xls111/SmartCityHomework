package Database;

import Entity.GridFileHead;
import Entity.RainSite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从数据库读取数据
 */
public class ReadDataFromDB {

    /**
     * 从数据库从读取DEM数据
     *
     * @param head 文件头
     * @return {@link double[][]} DEM数据
     */
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
                int p = (int) Math.floor((id - 1) / cols);  //计算id值对应的栅格横坐标
                int q = id - p * cols - 1;  //计算id值对应的栅格纵坐标
                demData[p][q] = resultSet.getDouble("dem");
            }

            return demData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[0][];
    }

    /**
     * 从数据库初始化雨量站
     *
     * @param head 文件头
     * @return {@link RainSite[][]}  雨量站数据
     */
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
                int p = (int) Math.floor((id - 1) / cols);  //计算id值对应的栅格横坐标
                int q = id - p * cols - 1;  //计算id值对应的栅格横坐标
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

    /**
     * 从数据库读取参考测站点
     *
     * @return {@link List}<{@link List}<{@link ?}>>  参考测量站点数据
     */
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

    /**
     * 从数据库中读取测站点雨量数据
     *
     * @return {@link List}<{@link List}<{@link ?}>>  测站点雨量数据
     */
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

    /**
     * 从数据库读取水流方向
     *
     * @param head 文件头
     * @return {@link int[][]} 流向二维数组
     */
    public int[][] readFlowDirectionFromDB(GridFileHead head){
        try {
            String sql1 = "select * from flowdirection";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            int[][] dirData = new int[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                dirData[p][q] = resultSet.getInt("direction");
            }

            return dirData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[0][];
    }

    /**
     * 从数据库读取累计流量
     *
     * @param head 文件头
     * @return {@link int[][]}  累计流量二维数组
     */
    public int[][] readFlowAccumulationFromDB(GridFileHead head){
        try {
            String sql1 = "select * from flowaccumulation";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            int[][] accData = new int[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                accData[p][q] = resultSet.getInt("accumulation");
            }

            return accData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[0][];
    }

    /**
     * 从数据库读取坡度
     *
     * @param head 文件头
     * @return {@link double[][]}  坡度二维数组
     */
    public double[][] readSlopeFromDB(GridFileHead head){
        try {
            String sql1 = "select * from slope";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            double[][] slopeData = new double[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                slopeData[p][q] = resultSet.getDouble("slope");
            }

            return slopeData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[0][];
    }

    /**
     * 从数据库读取坡向数据
     *
     * @param head 文件头
     * @return {@link double[][]}
     */
    public double[][] readAspectFromDB(GridFileHead head){
        try {
            String sql1 = "select * from aspect";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            double[][] aspectData = new double[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                aspectData[p][q] = resultSet.getDouble("aspect");
            }

            return aspectData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[0][];
    }

    /**
     * 从数据库读取水流长度
     *
     * @param head 文件头
     * @return {@link double[][]}  水流长度
     */
    public double[][] readFlowLengthFromDB(GridFileHead head){
        try {
            String sql1 = "select * from flowlength";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            double[][] flowLengthData = new double[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                flowLengthData[p][q] = resultSet.getDouble("length");
            }

            return flowLengthData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[0][];
    }

    /**
     * 从数据库读取洼地填充后DEM数据
     *
     * @param head 头
     * @return {@link double[][]}  无洼地DEM数据
     */
    public double[][] readHoleFillingFromDB(GridFileHead head){
        try {
            String sql1 = "select * from HoleFilling";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            double[][] holeFilling = new double[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                holeFilling[p][q] = resultSet.getDouble("holefilling");
            }

            return holeFilling;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[0][];
    }


    /**
     * 从数据库读取提取的河网数据
     *
     * @param head 头
     * @return {@link int[][]}  河网提取数据
     */
    public int[][] readStreamExtrcationFromDB(GridFileHead head){
        try {
            String sql1 = "select * from StreamExtraction";
            connectDB conn = new connectDB(sql1);
            ResultSet resultSet = conn.statement.executeQuery();
            int rows = head.nrows;
            int cols = head.ncols;

            int[][] river = new int[rows][cols];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int p = (int) Math.floor((id - 1) / cols);
                int q = id - p * cols - 1;
                river[p][q] = resultSet.getInt("river");
            }

            return river;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[0][];
    }

    /**
     * 从数据库读取上游累计流量单元数
     *
     * @param head 头
     * @return {@link int[][]}  上游累计流量单元数
     */
    public int[][] readUpStreamUnitsCountFromDB(GridFileHead head){
       try {
           String sql1 = "select * from UpStreamUnitsCount";
           connectDB conn = new connectDB(sql1);
           ResultSet resultSet = conn.statement.executeQuery();
           int rows = head.nrows;
           int cols = head.ncols;

           int[][] count = new int[rows][cols];
           while (resultSet.next()) {
               int id = resultSet.getInt("id");
               int p = (int) Math.floor((id - 1) / cols);
               int q = id - p * cols - 1;
               count[p][q] = resultSet.getInt("count");
           }

           return count;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return new int[0][];
   }
}
