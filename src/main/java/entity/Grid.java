package entity;

/**
 * 网格
 *
 * @author PC
 * @date 2021/12/29
 */
public class Grid {
    private int rows;                    //行数
    private int cols;                    //列数
    private double xllcorner;                   //左下角横坐标
    private double yllcorner;                   //左下角纵坐标
    private double cellsize;             //网格大小
    private int NODATA_value;            //无数据时的保留值
    private double[][] map;              //流量网格

    /**
     * domain.Grid 无参构造函数，初始化网格设置
     */
    public Grid() {
        this.xllcorner = 0;
        this.yllcorner = 0;
        this.rows = 10;
        this.cols = 10;
        this.cellsize = 100;
        this.map = new double[this.rows][this.cols];
        this.NODATA_value = -9999;
    }

    /**
     * domain.Grid 有参构造函数
     *
     * @param rows         行数
     * @param cols         列数
     * @param bx           左下角横坐标
     * @param by           左下角纵坐标
     * @param cellsize     网格宽度
     * @param NODATA_value 无数据时的保留值
     * @param map          流量网格
     */
    public Grid(int rows, int cols, double bx, double by, double cellsize, int NODATA_value, double[][] map) {
        this.rows = rows;
        this.cols = cols;
        this.xllcorner = bx;
        this.yllcorner = by;
        this.cellsize = cellsize;
        this.NODATA_value = NODATA_value;
        this.map = map;
    }

    /**
     * 返回网格的地理坐标
     *
     * @param x 横坐标索引
     * @param y 纵坐标索引
     * @return {@link double[]} 二维double数组，第一项为横坐标，第二项为纵坐标
     */
    public double[] getPosition(int x, int y) {
        double[] positions = new double[2];
        positions[0] = (0.5 + x) * this.cellsize + this.xllcorner;
        positions[1] = (0.5 + y) * this.cellsize + this.yllcorner;
        return positions;
    }

    public int[] parsePosition(double px, double py) {
        int[] index = new int[2];
        index[0] = (int) Math.floor((px - this.xllcorner) / this.cellsize);
        index[1] = (int) Math.floor((py - this.yllcorner) / this.cellsize);
        return index;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public double getXllcorner() {
        return xllcorner;
    }

    public void setXllcorner(double xllcorner) {
        this.xllcorner = xllcorner;
    }

    public double getYllcorner() {
        return yllcorner;
    }

    public void setYllcorner(double yllcorner) {
        this.yllcorner = yllcorner;
    }

    public double getCellsize() {
        return cellsize;
    }

    public void setCellsize(double cellsize) {
        this.cellsize = cellsize;
    }

    public int getNODATA_value() {
        return NODATA_value;
    }

    public void setNODATA_value(int NODATA_value) {
        this.NODATA_value = NODATA_value;
    }

    public double[][] getMap() {
        return map;
    }

    public void setMap(double[][] map) {
        this.map = map;
    }
}

