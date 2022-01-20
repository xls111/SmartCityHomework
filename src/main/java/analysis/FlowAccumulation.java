package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FlowAccumulation {
    //计算河流源头点
    public static int[][] Origin(int[][] dir) throws IOException {
        int nrows=236;
        int ncols=218;
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

    //回溯法求解累计流量
    public static int Add(int[][] dir,int[][] Origin,int[][] acc,int i,int j){
        int nrows=236;
        int ncols=218;
        //回溯到源头点时，停止递归
        if(Origin[i][j]==1){
            acc[i][j]=0;
            return 0;
        }
        //不为源头点时，朝八个方向寻找上游单元
        if(j-1>=0){
            if(dir[i][j-1]==1){
                acc[i][j]+=Add(dir,Origin,acc,i,j-1);
                acc[i][j]++;
            }
        }
        if(j-1>=0&&i-1>=0){
            if(dir[i-1][j-1]==2){
                acc[i][j]+=Add(dir,Origin,acc,i-1,j-1);
                acc[i][j]++;
            }
        }
        if(i-1>=0){
            if(dir[i-1][j]==4){
                acc[i][j]+=Add(dir,Origin,acc,i-1,j);
                acc[i][j]++;
            }
        }
        if(j+1<ncols&&i-1>=0){
            if(dir[i-1][j+1]==8){
                acc[i][j]+=Add(dir,Origin,acc,i-1,j+1);
                acc[i][j]++;
            }
        }
        if(j+1<ncols){
            if(dir[i][j+1]==16){
                acc[i][j]+=Add(dir,Origin,acc,i,j+1);
                acc[i][j]++;
            }
        }
        if(j+1<ncols&&i+1<nrows){
            if(dir[i+1][j+1]==32){
                acc[i][j]+=Add(dir,Origin,acc,i+1,j+1);
                acc[i][j]++;
            }
        }
        if(i+1<nrows){
            if(dir[i+1][j]==64){
                acc[i][j]+=Add(dir,Origin,acc,i+1,j);
                acc[i][j]++;
            }
        }
        if(i+1<nrows&&j-1>=0){
            if(dir[i+1][j-1]==128){
                acc[i][j]+=Add(dir,Origin,acc,i+1,j-1);
                acc[i][j]++;
            }
        }
        return acc[i][j];
    }

    //计算累计流
    public static int[][] accum_get(int[][] dir) throws IOException {
        int nrows=236;
        int ncols=218;
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
        Origin=Origin(dir);
        //便利整个格网，对非no_data单元进行回溯法递归求解
        for (i = 0; i < nrows; i++) {
            for (j = 0; j < ncols; j++) {
                if(dir[i][j]==-9999){
                    acc[i][j]=-9999;
                }
                else {
                    if (dir[i][j] == 0) {
                        Add(dir, Origin, acc, i, j);
                    }
                }
            }
        }
        return acc;
    }
}
