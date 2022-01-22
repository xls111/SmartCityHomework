package analysis;

import Entity.GridFileHead;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FlowAccumulation {
    /**
     * @param nrows 行数
     * @param ncols 列数
     * @param dir 流向表
     * @return
     * @throws IOException
     */
    //计算河流源头点
    public static int[][] Origin(int nrows,int ncols,int[][] dir) throws IOException {

        int[][] result =new int[nrows][ncols];
        int i;int j;
        for(i=0;i<nrows;i++){
            for(j=0;j<ncols;j++){
                int count=0;
                if(dir[i][j]!=-9999){
                    //搜寻像元周围八方向
                    if(i+1<nrows&&j-1>=0){
                        if(dir[i+1][j-1]==128){
                            count++;
                        }
                    }
                    if(j-1>=0){
                        if(dir[i][j-1]==1){
                            count++;
                        }
                    }
                    if(j-1>=0&&i-1>=0){
                        if(dir[i-1][j-1]==2){
                            count++;
                        }
                    }
                    if(i-1>=0){
                        if(dir[i-1][j]==4){
                            count++;
                        }
                    }
                    if(i-1>=0&&j+1<ncols){
                        if(dir[i-1][j+1]==8){
                            count++;
                        }
                    }
                    if(j+1<ncols){
                        if(dir[i][j+1]==16){
                            count++;
                        }
                    }
                    if(j+1<ncols&&i+1<nrows){
                        if(dir[i+1][j+1]==32){
                            count++;
                        }
                    }
                    if(i+1<nrows){
                        if(dir[i+1][j]==64){
                            count++;
                        }
                    }
                }
                if(dir[i][j]==-9999){
                    result[i][j]=-9999;
                }
                else if(dir[i][j]==0){
                    result[i][j]=0;//最终流入点不算为流出值
                }
                else if(count>0){
                    result[i][j]=0;
                }else{
                    result[i][j]=1;//周围八方向无流入，因此为源头点
                }
            }
        }
        return result;
    }

    /**
     * @param nrows 行数
     * @param ncols 列数
     * @param dir 流向表
     * @param Origin
     * @param acc 累计
     * @param i
     * @param j
     * @return
     */
    //回溯法求解累计流量
    public static int Add(int nrows,int ncols,int[][] dir,int[][] Origin,int[][] acc,int i,int j){

        //回溯到源头点时，停止递归
        if(Origin[i][j]==1){
            acc[i][j]=0;
            return 0;
        }
        //不为源头点时，朝八个方向寻找上游单元
        if(j-1>=0){
            if(dir[i][j-1]==1){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i,j-1);
                acc[i][j]++;
            }
        }
        if(j-1>=0&&i-1>=0){
            if(dir[i-1][j-1]==2){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i-1,j-1);
                acc[i][j]++;
            }
        }
        if(i-1>=0){
            if(dir[i-1][j]==4){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i-1,j);
                acc[i][j]++;
            }
        }
        if(j+1<ncols&&i-1>=0){
            if(dir[i-1][j+1]==8){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i-1,j+1);
                acc[i][j]++;
            }
        }
        if(j+1<ncols){
            if(dir[i][j+1]==16){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i,j+1);
                acc[i][j]++;
            }
        }
        if(j+1<ncols&&i+1<nrows){
            if(dir[i+1][j+1]==32){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i+1,j+1);
                acc[i][j]++;
            }
        }
        if(i+1<nrows){
            if(dir[i+1][j]==64){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i+1,j);
                acc[i][j]++;
            }
        }
        if(i+1<nrows&&j-1>=0){
            if(dir[i+1][j-1]==128){
                acc[i][j]+=Add(nrows,ncols,dir,Origin,acc,i+1,j-1);
                acc[i][j]++;
            }
        }
        return acc[i][j];
    }

    /**
     * @param head 头文件
     * @param dir 流向表
     * @return 累计流表
     * @throws IOException
     */
    //计算累计流
    public static int[][] GetAccumulation(GridFileHead head, int[][] dir) throws IOException {
        int nrows = head.nrows;
        int ncols = head.ncols;
        int i, j;
        int[][] acc=new int[nrows][ncols];
        for(i=0;i<nrows;i++){
            for(j=0;j<ncols;j++){
                acc[i][j]=0;
            }
        }
        int[][] Origin=new int[nrows][ncols];
        for(i=0;i<nrows;i++){
            for(j=0;j<ncols;j++){
                Origin[i][j]=0;
            }
        }
        Origin=Origin(nrows,ncols,dir);
        //便利整个格网，对非no_data单元进行回溯法递归求解
        for (i = 0; i < nrows; i++) {
            for (j = 0; j < ncols; j++) {
                if(dir[i][j]==-9999){
                    acc[i][j]=-9999;
                }
                else {
                    if (dir[i][j] == 0) {
                        Add(nrows,ncols,dir, Origin, acc, i, j);
                    }
                }
            }
        }
        return acc;
    }
}
