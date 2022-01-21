package analysis;

import java.io.IOException;


public class HoleFilling {
    public static double[][] hole_filling(double Dem[][],int nrows,int nclos,double Nodata) {

        //定义结果表
        double[][] result=new double[nrows][nclos];
        for (int i=0;i<nrows;i++)
            for (int j=0;j<nclos;j++){

                //初始化最小值
                double min=9999;

                //判断数据是否有效
                if(Dem[i][j]==Nodata)
                    result[i][j]=Nodata;

                else {
                    for (int m = i - 1; m < i + 2; m++)
                        for (int n = j - 1; n < j + 2; n++) {

                            //判断边界情况
                            if (m < 0 || n < 0 || m >= nrows || n >= nclos)
                                min = min;

                            //判断中心点情况
                            else if (m == i && n == j)
                                min = min;

                            else {
                                if (min > Dem[m][n])
                                    min = Dem[m][n];
                            }
                        }
                    //判断是否为洼点，阈值设置为100
                    if ((min - Dem[i][j] < 100) && (min - Dem[i][j] > 0))
//                        System.out.println("hole");
                        result[i][j] = min;
                }
            }
        return result;
    }
}
