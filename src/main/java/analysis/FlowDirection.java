package analysis;

import java.io.IOException;

public class FlowDirection {
    public static double[][] flowdirection(double Dem[][],int nrows,int nclos,double Nodata) throws IOException{

        double[][] Dir=new double[nrows][nclos];

        //定义流向列表，该列表按照一个以目标位置为中心的3*3矩阵，从左上角开始按顺序定义流向
        int[] list={32,64,128,16,0,1,8,4,2};
        for (int i=0;i<nrows;i++)
            for (int j=0;j<nclos;j++) {

                //mm为以目标位置为中心的3*3矩阵的检索，方便其和高程差矩阵进行对应
                int mm = 0;

                //定义向各个方向的高程差
                double[] gap = new double[9];

                if (Dem[i][j] == Nodata)
                    Dir[i][j] = Nodata;

                else {
                    //计算距离
                    for (int m = i - 1; m < i + 2; m++)
                        for (int n = j - 1; n < j + 2; n++) {
                            //如果索引范围超出边界，高程差定义为0
                            if (m < 0 || n < 0 || m >= nrows || n >= nclos) {
                                gap[mm] = 0;
                                mm++;
                            }
                            //如果检索到中心位置，高程差定义为0
                            else if (m == i && n == j) {
                                gap[mm] = 0;
                                mm++;
                            }
                            //正常情况下返回两个位置之间高程差
                            else {
                                gap[mm] = Dem[i][j] - Dem[m][n];
                                mm++;
                            }
                        }

                    //通过索引找出中心位置向各个方向DEM差距最大的方向，该方向即为流向
                    int index = 0;
                    double maxgap = gap[0];
                    for (int m = 0; m < gap.length; m++) {
                        if (gap[m] > maxgap) {
                            index = m;
                            maxgap = gap[m];
                        }
                    }
                    //如果最大高程差大于0，符合认知，将该方向流向赋予该点
                    if (maxgap > 0)
                        Dir[i][j] = list[index];
                        //如果最大高程差小于等于0，则赋予该点流向为0，表示不向其他方向流动
                    else
                        Dir[i][j] = 0;
                }
            }

        return Dir;
    }
}