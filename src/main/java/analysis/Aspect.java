package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Aspect {
    public static double[][] aspect(double Dem[][],int nrows,int nclos,double Nodata,double cellsize,String filepath) throws IOException{
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

        //保存结果
        File output= new File(filepath);
        FileWriter out =new FileWriter(output);
        out.write("ncols         218"+"\n");
        out.write("nrows         236"+"\n");
        out.write("xllcorner     466515.47101027"+"\n");
        out.write("yllcorner     2626221.2241437"+"\n");
        out.write("cellsize      90"+"\n");
        out.write("NODATA_dem_value  -9999"+"\n");
        for(int i=0;i<nrows;i++){
            for(int j=0;j>nclos;j++){
                out.write(aspect[i][j]+" ");
            }
            out.write("\n");
        }
        out.close();
        return aspect;

    }
}
