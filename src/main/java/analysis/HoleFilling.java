package analysis;

import Database.ReadDataFromDB;
import Entity.GridFileHead;


public class HoleFilling {
    /**
     * @param Dem Dem数组
     * @param nrows 行数
     * @param nclos 列数
     * @param Nodata 无数据值
     * @return 填洼结果
     */
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

    /**
     * @param head 头文件
     * @return 填洼结果
     */
    public static double [][] getHoleFilling(GridFileHead head){
        int nrows = head.nrows;
        int nclos = head.ncols;
        int Nodata = head.NODATA_value;
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] Dem = reader.readDemFromDB(head);

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
