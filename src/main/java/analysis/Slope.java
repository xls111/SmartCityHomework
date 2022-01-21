package analysis;

import Database.ReadDataFromDB;
import entity.GridFileHead;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Slope {
    public static double[][] getSlope(double Dem[][], int nrows, int cols, double Nodata, double cellsize) throws IOException{

        double[][] slope=new double[nrows][cols];

        for (int i=0;i<nrows;i++)
            for (int j=0;j<cols;j++){
                double dz_dx;
                double dz_dy;
                //判断是否为Nodata区域
                if(i==0||j==0||i==nrows||j==cols||Dem[i][j]==Nodata)
                    slope[i][j]=Nodata;

                else{
                    dz_dx=((Dem[i-1][j+1]+2*Dem[i][j+1]+Dem[i+2][j+1])-(Dem[i-1][j-1]+2*Dem[i][j-1]+Dem[i+2][j-1]))/(8*cellsize);
                    dz_dy=((Dem[i+1][j-1]+2*Dem[i+1][j]+Dem[i+1][j+1])-(Dem[i-1][j-1]+2*Dem[i-1][j]+Dem[i-1][j+1]))/(8*cellsize);
                    slope[i][j]= Math.atan(Math.sqrt(Math.pow(dz_dx,2.0)+Math.pow(dz_dy,2.0)))*180/Math.PI;
                }
            }
        return slope;
    }

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
