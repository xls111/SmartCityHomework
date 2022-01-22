package analysis;

import Database.ReadDataFromDB;
import entity.GridFileHead;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Slope {
    /**
     * @param Dem Dem数组
     * @param nrows 行数
     * @param cols 列数
     * @param Nodata 无数据值
     * @param cellsize 像元大小
     * @return 坡度表
     * @throws IOException
     */
    public static double[][] getSlope(double Dem[][], int nrows, int cols, double Nodata, double cellsize) throws IOException{

        //定义坡度矩阵
        double[][] slope=new double[nrows][cols];

        for (int i=0;i<nrows;i++)
            for (int j=0;j<cols;j++){
                double dz_dx;
                double dz_dy;
                //判断是否为Nodata区域
                if(i==0||j==0||i==nrows||j==cols||Dem[i][j]==Nodata)
                    slope[i][j]=Nodata;

                else{
                    //计算x方向变化量
                    dz_dx=((Dem[i-1][j+1]+2*Dem[i][j+1]+Dem[i+2][j+1])-(Dem[i-1][j-1]+2*Dem[i][j-1]+Dem[i+2][j-1]))/(8*cellsize);
                    //计算y方向变化量
                    dz_dy=((Dem[i+1][j-1]+2*Dem[i+1][j]+Dem[i+1][j+1])-(Dem[i-1][j-1]+2*Dem[i-1][j]+Dem[i-1][j+1]))/(8*cellsize);
                    //计算坡度
                    slope[i][j]= Math.atan(Math.sqrt(Math.pow(dz_dx,2.0)+Math.pow(dz_dy,2.0)))*180/Math.PI;
                }
            }
        return slope;
    }

    /**
     * @param head 头文件
     * @return
     * @throws IOException
     */
    public static double[][] getSlope(GridFileHead head) throws IOException {
        int rows = head.nrows;
        int cols = head.ncols;
        int noData = head.NODATA_value;
        double cellSize = head.cellsize;
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] dem = reader.readDemFromDB(head);

        return getSlope(dem,rows,cols,noData,cellSize);
    }
}
