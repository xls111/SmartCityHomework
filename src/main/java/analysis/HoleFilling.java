package analysis;

import Database.ReadDataFromDB;
import entity.GridFileHead;

import java.io.IOException;


public class HoleFilling {
    public static double[][] getHoleFilling(double Dem[][], int nrows, int nclos, double Nodata) {

        double[][] result=new double[nrows][nclos];
        for (int i=0;i<nrows;i++)
            for (int j=0;j<nclos;j++){

                double min=9999;

                if(Dem[i][j]==Nodata)
                    result[i][j]=Nodata;

                else {
                    for (int m = i - 1; m < i + 2; m++)
                        for (int n = j - 1; n < j + 2; n++) {

                            if (m < 0 || n < 0 || m >= nrows || n >= nclos)
                                min = min;

                            else if (m == i && n == j)
                                min = min;

                            else {
                                if (min > Dem[m][n])
                                    min = Dem[m][n];
                            }
                        }
                    if ((min - Dem[i][j] < 100) && (min - Dem[i][j] > 0))
//                        System.out.println("hole");
                        result[i][j] = min;
                }
            }
        return result;
    }

    public static double [][] getHoleFilling(GridFileHead head){
        int nrows = head.nrows;
        int nclos = head.ncols;
        int Nodata = head.NODATA_value;
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] Dem = reader.readDemFromDB(head);

        double[][] result=new double[nrows][nclos];
        for (int i=0;i<nrows;i++)
            for (int j=0;j<nclos;j++){

                double min=9999;

                if(Dem[i][j]==Nodata)
                    result[i][j]=Nodata;

                else {
                    for (int m = i - 1; m < i + 2; m++)
                        for (int n = j - 1; n < j + 2; n++) {

                            if (m < 0 || n < 0 || m >= nrows || n >= nclos)
                                min = min;

                            else if (m == i && n == j)
                                min = min;

                            else {
                                if (min > Dem[m][n])
                                    min = Dem[m][n];
                            }
                        }
                    if ((min - Dem[i][j] < 100) && (min - Dem[i][j] > 0))
//                        System.out.println("hole");
                        result[i][j] = min;
                }
            }
        return result;
    }
}
