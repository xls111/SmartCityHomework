package analysis;

import Database.ReadDataFromDB;
import entity.GridFileHead;

public class Aspect {
    public static double[][] getAspect(double Dem[][], int nrows, int ncols, double Nodata, double cellsize){
        double[][] aspect=new double[nrows][ncols];

        for (int i=0;i<nrows;i++)
            for (int j=0;j<ncols;j++){
                double dz_dx;
                double dz_dy;
                if(i==0||j==0||i==nrows||j==ncols||Dem[i][j]==Nodata)
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

    public static double[][] getAspect(GridFileHead head){
        int rows = head.nrows;
        int cols = head.ncols;
        int noData = head.NODATA_value;
        double cellSize = head.cellsize;
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] dem = reader.readDemFromDB(head);

        return getAspect(dem,rows,cols,noData,cellSize);
    }
}
