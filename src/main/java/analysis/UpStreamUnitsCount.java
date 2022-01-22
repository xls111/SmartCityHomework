package analysis;

import Entity.GridFileHead;

import java.io.IOException;

public class UpStreamUnitsCount {
    public static class upValue {
        int one_value[];

        upValue(){
            one_value = new int [8];
            for(int i=0;i<8;i++){
                one_value[i]=0;
            }
        }
    }

    //计算上游单元
    public static int[][] getUpValue(int[][] dir) throws IOException {

        int nrows = dir.length;
        int ncols = dir[0].length;
        int[][] number;
        number = new int[nrows][ncols];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                number[i][j] = 0;
            }
        }
        upValue up_value[][]=new upValue[nrows][ncols];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                up_value[i][j] = new upValue();
            }
        }

        //计算各点是否有上游单元，并将之记录
        int sum = 0;
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                if (dir[i][j] > -9999) {
                    //边界情况①
                    if (i + 1 >= nrows && j - 1 < 0) {
                        if (dir[i - 1][j] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        //边界情况②
                    } else if (i + 1 >= nrows && j + 1 >= ncols) {
                        if (dir[i][j - 1] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        //边界情况③
                    } else if (i - 1 < 0 && j + 1 >= ncols) {
                        if (dir[i][j - 1] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        //边界情况④
                    } else if (i - 1 < 0 && j - 1 < 0) {
                        if (dir[i + 1][j] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        //边界情况⑤
                    } else if (i + 1 >= nrows) {
                        if (dir[i][j - 1] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        //边界情况⑥
                    } else if (j + 1 >= ncols) {
                        if (dir[i - 1][j] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i][j - 1] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        //边界情况⑦
                    } else if (j - 1 < 0) {
                        if (dir[i - 1][j] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        //边界情况⑧
                    } else if (i - 1 < 0) {
                        if (dir[i + 1][j] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        if (dir[i][j - 1] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                            number[i][j]++;
                        }
                        //非边界情况
                    } else {
                        if (dir[i + 1][j] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i][j - 1] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                        }
                    }
                }
            }
        }
        int numbers=1;
        //将上游单元记录值通过int形式保存
        int[][] up_value_sum=new int[nrows][ncols];
        for(int i=0;i<nrows;i++){
            for(int j=0;j<ncols;j++){
                up_value_sum[i][j]=0;
            }
        }
        for(int i=0;i<nrows;i++){
            for(int j=0;j<ncols;j++){
                if(up_value[i][j].one_value[0]!=0){
                    for(int k=0;k<8;k++){
                        if(up_value[i][j].one_value[k]!=0){
                            up_value_sum[i][j]+=numbers*up_value[i][j].one_value[k];
                            numbers++;
                        }
                    }
                }
            }
        }
        for(int i=0;i<nrows;i++){
            for(int j=0;j<ncols;j++){
                if(dir[i][j]==-9999){
                    up_value_sum[i][j]=-9999;}
            }
        }
        return up_value_sum;
    }
}