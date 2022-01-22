package Database;

import Entity.Dem;
import Entity.GridFileHead;
import Entity.Rain;
import Entity.Station;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *  数据库存储
 */
public class StoreDataToDB {

    /**
     * 验证表是否存在
     *
     * @param tableName 表名
     * @return boolean 是否存在
     */
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

    /**
     * 存储dem数据至数据库的dem表
     *
     * @param dem 民主党
     */
    public void storeDemToDB(Dem dem) {
        try {
            if (validateTableNameExist("dem")) {
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

            String sql2 = "insert into dem(id,x,y,dem) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;
            List<List<Double>> demList = dem.getDem();
            GridFileHead head = dem.getHead();
            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < demList.size(); i++) {
                for (int j = 0; j < demList.get(j).size(); j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);    //根据单元格的索引确定横坐标
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);  //根据单元格的索引确定纵坐标
                    conn2.statement.setDouble(4, demList.get(i).get(j));
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("dem已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 储存测站点雨量数据到数据库的rain1表
     *
     * @param rain 测站点雨量
     */
    public void storeRainToDB(Rain rain) {
        try {
            if (validateTableNameExist("rain1")) {
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

            String sql2 = "insert into rain1(id,flow,huanglongdai,lianxing,fengmulang) values(?,?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            List<List<?>> rainList = rain.getRain();
            for (int i = 0; i < rainList.size(); i++) {
                conn2.statement.setInt(1, i + 1);
                conn2.statement.setDouble(2, (double) rainList.get(i).get(1));
                conn2.statement.setInt(3, (int) rainList.get(i).get(2));
                conn2.statement.setInt(4, (int) rainList.get(i).get(3));
                conn2.statement.setInt(5, (int) rainList.get(i).get(4));
                conn2.statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 存储测站点数据到数据库的station表
     *
     * @param station 测站点
     */
    public void storeStationToDB(Station station) {
        try {
            if (validateTableNameExist("station")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS station (" +
                    "id int(4) not null," +
                    "x  double," +
                    "y  double," +
                    "PRIMARY KEY (`id`)" +
                    ");";

            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立站点属性表成功");
            String sql2 = "insert into station(id,x,y) values(?,?,?)";
            connectDB conn2 = new connectDB(sql2);

            List<List<?>> stationList = station.getStation();

            for (int i = 0; i < stationList.size(); i++) {
                conn2.statement.setInt(1, i + 1);       //设置id值，从1开始
                conn2.statement.setDouble(2, (double) stationList.get(i).get(3));   //设置x横坐标，对应stationList列表的第4列数据
                conn2.statement.setDouble(3, (double) stationList.get(i).get(4));   //设置y纵坐标，对应statioList列表的第5列数据
                conn2.statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 存储累积流量到数据库的flowaccumulation表
     *
     * @param acc  累积流量
     * @param head 文件头
     */
    public void storeFlowAccumulationToDB(int[][] acc,GridFileHead head) {
        try {
            if (validateTableNameExist("FlowAccumulation")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS FlowAccumulation (" +
                    "id int(4) not null," +
                    "x  double," +
                    "y  double," +
                    "accumulation  int," +
                    "PRIMARY KEY (`id`)" +
                    ");";

            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立流量属性表成功");
            String sql2 = "insert into FlowAccumulation(id,x,y,accumulation) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;

            for (int i = 0; i < acc.length; i++) {
                for (int j = 0; j < acc[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);    //根据单元格的索引确定横坐标
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);  //根据单元格的索引确定纵坐标
                    conn2.statement.setInt(4, acc[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("流量已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储水流方向到数据库的flowdirection表
     *
     * @param dir  水流方向
     * @param head 文件头
     */
    public void storeFlowDirectionToDB(int[][] dir,GridFileHead head){
        try {
            if (validateTableNameExist("FlowDirection")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS FlowDirection (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "direction int," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立流向属性表成功");
            String sql2 = "insert into FlowDirection(id,x,y,direction) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < dir.length; i++) {
                for (int j = 0; j < dir[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);    //根据单元格的索引确定横坐标
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);  //根据单元格的索引确定纵坐标
                    conn2.statement.setDouble(4, dir[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("流向已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 存放坡度到数据库表
     *
     * @param slope 坡度
     * @param head  文件头
     */
    public void storeSlopeToDB(double[][] slope,GridFileHead head){
        try {
            if (validateTableNameExist("Slope")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS Slope (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "slope double," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立坡度属性表成功");
            String sql2 = "insert into Slope(id,x,y,slope) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < slope.length; i++) {
                for (int j = 0; j < slope[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);    //根据单元格的索引确定横坐标
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);  //根据单元格的索引确定纵坐标
                    conn2.statement.setDouble(4, slope[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("坡度已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储坡向数据到数据库表的aspect表
     *
     * @param aspect 坡向
     * @param head   文件头
     */
    public void storeAspectToDB(double[][] aspect,GridFileHead head){
        try {
            if (validateTableNameExist("Aspect")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS Aspect (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "aspect double," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立坡向属性表成功");
            String sql2 = "insert into Aspect(id,x,y,aspect) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < aspect.length; i++) {
                for (int j = 0; j < aspect[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);    //根据单元格的索引确定横坐标
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);  //根据单元格的索引确定纵坐标
                    conn2.statement.setDouble(4, aspect[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("坡向已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储水流长度到数据库的flowlength表
     *
     * @param length 水流长度
     * @param head   文件头
     */
    public void storeFlowLengthToDB(double[][] length,GridFileHead head){
        try {
            if (validateTableNameExist("FlowLength")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS FlowLength (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "length double," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立水流长度属性表成功");
            String sql2 = "insert into FlowLength(id,x,y,length) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < length.length; i++) {
                for (int j = 0; j < length[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);
                    conn2.statement.setDouble(4, length[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("水流长度已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 储存洼地填充后的DEM到数据库的holefilling表
     *
     * @param filling 无洼地DEM
     * @param head    文件头
     */
    public void storeHoleFillingToDB(double[][] filling,GridFileHead head){
        try {
            if (validateTableNameExist("HoleFilling")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS HoleFilling (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "holefilling double," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立洼地填充属性表成功");
            String sql2 = "insert into HoleFilling(id,x,y,holefilling) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < filling.length; i++) {
                for (int j = 0; j < filling[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);
                    conn2.statement.setDouble(4, filling[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("洼地填充表已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储河网提取数据到数据库的streamextraction表
     *
     * @param river 河网提取数据
     * @param head  文件头
     */
    public void storeStreamExtractionToDB(int[][] river,GridFileHead head){
        try {
            if (validateTableNameExist("StreamExtraction")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS StreamExtraction (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "river int," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立河网提取表成功");
            String sql2 = "insert into StreamExtraction(id,x,y,river) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < river.length; i++) {
                for (int j = 0; j < river[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);
                    conn2.statement.setDouble(4, river[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("河网提取结果已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 储存上游累计单位数到数据库的upstreamunits表
     *
     * @param count 上游累计单位数
     * @param head  文件头
     */
    public void storeUpStreamUnitsCountToDB(int[][] count,GridFileHead head){
        try {
            if (validateTableNameExist("UpStreamUnitsCount")) {
                return;
            }

            String sql1 = "CREATE TABLE IF NOT EXISTS UpStreamUnitsCount (" +
                    "id int(4) not null," +
                    "x double," +                       //中心点x坐标
                    "y double," +                       //中心点y坐标
                    "count int," +
                    "PRIMARY KEY (`id`)" +
                    ");";
            connectDB conn1 = new connectDB(sql1);
            conn1.statement.executeUpdate();

            System.out.printf("建立河网提取表成功");
            String sql2 = "insert into UpStreamUnitsCount(id,x,y,count) values(?,?,?,?)";
            connectDB conn2 = new connectDB(sql2);
            int k = 0;

            double cellSize = head.cellsize;
            double xllcorner = head.xllcorner;
            double yllcorner = head.yllcorner;
            int nrows = head.nrows;
            for (int i = 0; i < count.length; i++) {
                for (int j = 0; j < count[i].length; j++) {
                    k = k + 1;
                    conn2.statement.setInt(1, k);
                    conn2.statement.setDouble(2, j * cellSize + 0.5 * cellSize + xllcorner);
                    conn2.statement.setDouble(3, (nrows - i - 1) * cellSize + 0.5 * cellSize + yllcorner);
                    conn2.statement.setDouble(4, count[i][j]);
                    conn2.statement.executeUpdate();
                }
            }

            System.out.printf("上游汇入单元结果已存入数据库");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
