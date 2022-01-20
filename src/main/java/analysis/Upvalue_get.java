package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Upvalue_get {
    public static class Up_value {
        int one_value[];

        Up_value(){
            one_value = new int [8];
            for(int i=0;i<8;i++){
                one_value[i]=0;
            }
        }
    }

    //计算上游单元
    public static void up_value_get(int[][] dir,String filepath) throws IOException {

        int[][] number;
        number = new int[236][218];
        for (int i = 0; i < 236; i++) {
            for (int j = 0; j < 218; j++) {
                number[i][j] = 0;
            }
        }
        Up_value up_value[][]=new Up_value[236][218];
        for (int i = 0; i < 236; i++) {
            for (int j = 0; j < 218; j++) {
                up_value[i][j] = new Up_value();
            }
        }

        //计算各点是否有上游单元，并将之记录
        int sum = 0;
        for (int i = 0; i < 236; i++) {
            for (int j = 0; j < 218; j++) {
                if (dir[i][j] > -9999) {
                    //边界情况①
                    if (i + 1 >= 236 && j - 1 < 0) {
                        if (dir[i - 1][j] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        //边界情况②
                    } else if (i + 1 >= 236 && j + 1 >= 218) {
                        if (dir[i][j - 1] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        //边界情况③
                    } else if (i - 1 < 0 && j + 1 >= 218) {
                        if (dir[i][j - 1] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        //边界情况④
                    } else if (i - 1 < 0 && j - 1 < 0) {
                        if (dir[i + 1][j] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        //边界情况⑤
                    } else if (i + 1 >= 236) {
                        if (dir[i][j - 1] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        //边界情况⑥
                    } else if (j + 1 >= 218) {
                        if (dir[i - 1][j] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i][j - 1] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        //边界情况⑦
                    } else if (j - 1 < 0) {
                        if (dir[i - 1][j] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        //边界情况⑧
                    } else if (i - 1 < 0) {
                        if (dir[i + 1][j] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        if (dir[i][j - 1] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                            number[i][j]++;
                        }
                        //非边界情况
                    } else {
                        if (dir[i + 1][j] == 16) {
                            up_value[i][j].one_value[number[i][j]] = 1;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j + 1] == 32) {
                            up_value[i][j].one_value[number[i][j]] = 2;
                            number[i][j]++;
                        }
                        if (dir[i][j + 1] == 64) {
                            up_value[i][j].one_value[number[i][j]] = 3;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j + 1] == 128) {
                            up_value[i][j].one_value[number[i][j]] = 4;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j] == 1) {
                            up_value[i][j].one_value[number[i][j]] = 5;
                            number[i][j]++;
                        }
                        if (dir[i - 1][j - 1] == 2) {
                            up_value[i][j].one_value[number[i][j]] = 6;
                            number[i][j]++;
                        }
                        if (dir[i][j - 1] == 4) {
                            up_value[i][j].one_value[number[i][j]] = 7;
                            number[i][j]++;
                        }
                        if (dir[i + 1][j - 1] == 8) {
                            up_value[i][j].one_value[number[i][j]] = 8;
                        }
                    }
                }
            }
        }
        int numbers=1;
        //将上游单元记录值通过int形式保存
        int[][] up_value_sum=new int[236][218];
        for(int i=0;i<236;i++){
            for(int j=0;j<218;j++){
                up_value_sum[i][j]=0;
            }
        }
        for(int i=0;i<236;i++){
            for(int j=0;j<218;j++){
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
        for(int i=0;i<236;i++){
            for(int j=0;j<218;j++){
                if(dir[i][j]==-9999){
                    up_value_sum[i][j]=-9999;}
            }
        }
        //保存文件
        File output= new File(filepath);
        FileWriter out =new FileWriter(output);
        out.write("ncols         218"+"\n");
        out.write("nrows         236"+"\n");
        out.write("xllcorner     466515.47101027"+"\n");
        out.write("yllcorner     2626221.2241437"+"\n");
        out.write("cellsize      90"+"\n");
        out.write("NODATA_value  -9999"+"\n");
        for(int i=0;i<236;i++){
            for(int j=0;j<218;j++){
                out.write(up_value_sum[i][j]+" ");
            }
            out.write("\n");
        }
        out.close();

    }
}
