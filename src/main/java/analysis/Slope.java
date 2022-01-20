package analysis;

import java.io.IOException;


public class Slope {
    public static double[][] slope(double Dem[][],int nrows,int nclos,double Nodata,double cellsize) throws IOException{

        double[][] slope=new double[nrows][nclos];

        for (int i=0;i<nrows;i++)
            for (int j=0;j<nclos;j++){
                double dz_dx;
                double dz_dy;
                //判断是否为Nodata区域
                if(i==0||j==0||i==nrows||j==nclos||Dem[i][j]==Nodata)
                    slope[i][j]=Nodata;

                else{
                    dz_dx=((Dem[i-1][j+1]+2*Dem[i][j+1]+Dem[i+2][j+1])-(Dem[i-1][j-1]+2*Dem[i][j-1]+Dem[i+2][j-1]))/(8*cellsize);
                    dz_dy=((Dem[i+1][j-1]+2*Dem[i+1][j]+Dem[i+1][j+1])-(Dem[i-1][j-1]+2*Dem[i-1][j]+Dem[i-1][j+1]))/(8*cellsize);
                    slope[i][j]= Math.atan(Math.sqrt(Math.pow(dz_dx,2.0)+Math.pow(dz_dy,2.0)))*180/Math.PI;
                }
            }

        return slope;
    }
}
