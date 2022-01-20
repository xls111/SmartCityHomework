package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Aspect {
    public static double[][] aspect(double Dem[][],int nrows,int nclos,double Nodata,double cellsize){
        double[][] aspect=new double[nrows][nclos];

        for (int i=0;i<nrows;i++)
            for (int j=0;j<nclos;j++){
                double dz_dx;
                double dz_dy;
                if(i==0||j==0||i==nrows||j==nclos||Dem[i][j]==Nodata)
                    aspect[i][j]=Nodata;

                else{
                    dz_dx=((Dem[i-1][j+1]+2*Dem[i][j+1]+Dem[i+2][j+1])-(Dem[i-1][j-1]+2*Dem[i][j-1]+Dem[i+2][j-1]))/8;
                    dz_dy=((Dem[i+1][j-1]+2*Dem[i+1][j]+Dem[i+1][j+1])-(Dem[i-1][j-1]+2*Dem[i-1][j]+Dem[i-1][j+1]))/8;
                    aspect[i][j]= Math.atan2(dz_dy,-dz_dx)*180/Math.PI;

                    if(aspect[i][j]<0)
                        aspect[i][j]=90-aspect[i][j];
                    else if(aspect[i][j]>90)
                        aspect[i][j]=360-aspect[i][j]+90;
                    else
                        aspect[i][j]=90-aspect[i][j];
                }
            }

        return aspect;

    }
}
