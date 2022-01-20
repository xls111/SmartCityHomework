package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Hydrology {

    //计算流向
    public static int[][] direction(double dem_value[][],String filepath) throws IOException {
        int[][] dir = new int[236][218];
        //先将流向均赋予为null值
        for (int i = 0; i < 236; i++) {
            for (int j = 0; j < 218; j++) {
                dir[i][j] = -9999;
            }
        }
        //遍历每个栅格，计算流量
        for (int i = 0; i < 236; i++) {
            for (int j = 0; j < 218; j++) {
                //非no_data值时，才进入判断
                if (dem_value[i][j] > -9999) {
                    //边界点情况①
                    if (i + 1 >= 236 && j - 1 < 0) {
                        double[] temp = new double[]{dem_value[i - 1][j], dem_value[i][j + 1], dem_value[i - 1][j + 1]};
                        for (int k = 0; k < 3; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i - 1][j]) {
                            dir[i][j] = 16;
                        } else if (minNum == dem_value[i][j + 1]) {
                            dir[i][j] = 4;
                        } else {
                            dir[i - 1][j + 1] = 8;
                        }
                        //边界点情况②
                    } else if (i + 1 >= 236 && j + 1 >= 218) {
                        double[] temp = new double[]{dem_value[i - 1][j - 1], dem_value[i][j - 1], dem_value[i - 1][j]};
                        for (int k = 0; k < 3; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i - 1][j - 1]) {
                            dir[i][j] = 32;
                        } else if (minNum == dem_value[i][j - 1]) {
                            dir[i][j] = 64;
                        } else {
                            dir[i - 1][j + 1] = 16;
                        }
                        //边界点情况③
                    } else if (i - 1 < 0 && j + 1 >= 218) {
                        double[] temp = new double[]{dem_value[i][j - 1], dem_value[i + 1][j - 1], dem_value[i + 1][j]};
                        for (int k = 0; k < 3; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i][j - 1]) {
                            dir[i][j] = 64;
                        } else if (minNum == dem_value[i + 1][j - 1]) {
                            dir[i][j] = 128;
                        } else {
                            dir[i + 1][j] = 1;
                        }
                        //边界点情况④
                    } else if (i - 1 < 0 && j - 1 < 0) {
                        double[] temp = new double[]{dem_value[i + 1][j], dem_value[i + 1][j + 1], dem_value[i][j + 1]};
                        for (int k = 0; k < 3; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i + 1][j]) {
                            dir[i][j] = 1;
                        } else if (minNum == dem_value[i + 1][j + 1]) {
                            dir[i][j] = 2;
                        } else {
                            dir[i][j + 1] = 4;
                        }
                        //边界点情况⑤
                    } else if (i + 1 >= 236) {
                        double[] temp = new double[]{dem_value[i - 1][j - 1], dem_value[i][j + 1], dem_value[i][j - 1],
                                dem_value[i - 1][j], dem_value[i - 1][j + 1]};
                        for (int k = 0; k < 5; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i - 1][j - 1]) {
                            dir[i][j] = 32;
                        } else if (minNum == dem_value[i][j + 1]) {
                            dir[i][j] = 4;
                        } else if (minNum == dem_value[i][j - 1]) {
                            dir[i][j] = 64;
                        } else if (minNum == dem_value[i - 1][j]) {
                            dir[i][j] = 16;
                        } else {
                            dir[i][j] = 8;
                        }
                        //边界点情况⑥
                    } else if (j + 1 >= 218) {
                        double[] temp = new double[]{dem_value[i - 1][j - 1], dem_value[i - 1][j], dem_value[i][j - 1],
                                dem_value[i + 1][j - 1], dem_value[i + 1][j]};
                        for (int k = 0; k < 5; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i - 1][j - 1]) {
                            dir[i][j] = 32;
                        } else if (minNum == dem_value[i - 1][j]) {
                            dir[i][j] = 16;
                        } else if (minNum == dem_value[i][j - 1]) {
                            dir[i][j] = 64;
                        } else if (minNum == dem_value[i + 1][j - 1]) {
                            dir[i][j] = 128;
                        } else {
                            dir[i][j] = 1;
                        }
                        //边界点情况⑦
                    } else if (j - 1 < 0) {
                        double[] temp = new double[]{dem_value[i - 1][j], dem_value[i - 1][j + 1], dem_value[i][j + 1],
                                dem_value[i + 1][j + 1], dem_value[i + 1][j]};
                        for (int k = 0; k < 5; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i - 1][j]) {
                            dir[i][j] = 16;
                        } else if (minNum == dem_value[i - 1][j + 1]) {
                            dir[i][j] = 8;
                        } else if (minNum == dem_value[i][j + 1]) {
                            dir[i][j] = 4;
                        } else if (minNum == dem_value[i + 1][j + 1]) {
                            dir[i][j] = 2;
                        } else {
                            dir[i][j] = 1;
                        }
                        //边界点情况⑧
                    } else if (i - 1 < 0) {
                        double[] temp = new double[]{dem_value[i][j - 1], dem_value[i + 1][j - 1], dem_value[i + 1][j],
                                dem_value[i + 1][j + 1], dem_value[i][j + 1]};
                        for (int k = 0; k < 5; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i][j - 1]) {
                            dir[i][j] = 64;
                        } else if (minNum == dem_value[i + 1][j - 1]) {
                            dir[i][j] = 128;
                        } else if (minNum == dem_value[i + 1][j]) {
                            dir[i][j] = 1;
                        } else if (minNum == dem_value[i + 1][j + 1]) {
                            dir[i][j] = 2;
                        } else {
                            dir[i][j] = 4;
                        }
                        //非边界点情况
                    } else {
                        double[] temp = new double[]{dem_value[i][j - 1], dem_value[i + 1][j - 1], dem_value[i + 1][j],
                                dem_value[i + 1][j + 1], dem_value[i][j + 1], dem_value[i - 1][j + 1], dem_value[i - 1][j], dem_value[i - 1][j - 1]};
                        for (int k = 0; k < 8; k++) {
                            if (temp[k] < 0) {
                                temp[k] = 9999.0;
                            }
                        }
                        double minNum = Arrays.stream(temp).min().getAsDouble();
                        if(minNum>=dem_value[i][j]){
                            dir[i][j]=0;
                        } else if (minNum == dem_value[i + 1][j]) {
                            dir[i][j] = 1;
                        } else if (minNum == dem_value[i + 1][j + 1]) {
                            dir[i][j] = 2;
                        } else if (minNum == dem_value[i][j + 1]) {
                            dir[i][j] = 4;
                        } else if (minNum == dem_value[i - 1][j + 1]) {
                            dir[i][j] = 8;
                        } else if (minNum == dem_value[i - 1][j]) {
                            dir[i][j] = 16;
                        } else if (minNum == dem_value[i - 1][j - 1]) {
                            dir[i][j] = 32;
                        } else if (minNum == dem_value[i][j - 1]) {
                            dir[i][j] = 64;
                        } else if (minNum == dem_value[i + 1][j - 1]) {
                            dir[i][j] = 128;
                        }
                    }
                }
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
        for(int i=0;i<236;i++){
            for(int j=0;j<218;j++){
                out.write(dir[i][j]+" ");
            }
            out.write("\n");
        }
        out.close();
        return dir;
    }

    //上游单元存储值类
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

    //计算河流源头点
    public static int[][] Origin(int[][] dir) throws IOException {
        int[][] result =new int[236][218];
        int i;int j;
        for(i=0;i<236;i++){
            for(j=0;j<218;j++){
                int count=0;
                if(dir[i][j]!=-9999){
                    //搜寻像元周围八方向
                    if(i+1<236&&j-1>=0){
                        if(dir[i+1][j-1]==8){
                            count++;
                        }
                    }
                    if(j-1>=0){
                        if(dir[i][j-1]==4){
                            count++;
                        }
                    }
                    if(j-1>=0&&i-1>=0){
                        if(dir[i-1][j-1]==2){
                            count++;
                        }
                    }
                    if(i-1>=0){
                        if(dir[i-1][j]==1){
                            count++;
                        }
                    }
                    if(i-1>=0&&j+1<218){
                        if(dir[i-1][j+1]==128){
                            count++;
                        }
                    }
                    if(j+1<218){
                        if(dir[i][j+1]==64){
                            count++;
                        }
                    }
                    if(j+1<218&&i+1<236){
                        if(dir[i+1][j+1]==32){
                            count++;
                        }
                    }
                    if(i+1<236){
                        if(dir[i+1][j]==16){
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
        //回溯到源头点时，停止递归
        if(Origin[i][j]==1){
            acc[i][j]=0;
            return 0;
        }
        //不为源头点时，朝八个方向寻找上游单元
        if(j-1>=0){
            if(dir[i][j-1]==4){
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
            if(dir[i-1][j]==1){
                acc[i][j]+=Add(dir,Origin,acc,i-1,j);
                acc[i][j]++;
            }
        }
        if(j+1<218&&i-1>=0){
            if(dir[i-1][j+1]==128){
                acc[i][j]+=Add(dir,Origin,acc,i-1,j+1);
                acc[i][j]++;
            }
        }
        if(j+1<218){
            if(dir[i][j+1]==64){
                acc[i][j]+=Add(dir,Origin,acc,i,j+1);
                acc[i][j]++;
            }
        }
        if(j+1<218&&i+1<236){
            if(dir[i+1][j+1]==32){
                acc[i][j]+=Add(dir,Origin,acc,i+1,j+1);
                acc[i][j]++;
            }
        }
        if(i+1<236){
            if(dir[i+1][j]==16){
                acc[i][j]+=Add(dir,Origin,acc,i+1,j);
                acc[i][j]++;
            }
        }
        if(i+1<236&&j-1>=0){
            if(dir[i+1][j-1]==8){
                acc[i][j]+=Add(dir,Origin,acc,i+1,j-1);
                acc[i][j]++;
            }
        }
        return acc[i][j];
    }

    //计算累计流
    public static int[][] accum_get(int[][] dir,String filepath) throws IOException {
        int i, j;
        int[][] acc=new int[236][218];
        for(i=0;i<236;i++){
            for(j=0;j<218;j++){
                acc[i][j]=0;
            }
        }
        int[][] Origin=new int[236][218];
        for(i=0;i<236;i++){
            for(j=0;j<218;j++){
                Origin[i][j]=0;
            }
        }
        Origin=Origin(dir);
        //便利整个格网，对非no_data单元进行回溯法递归求解
        for (i = 0; i < 236; i++) {
            for (j = 0; j < 218; j++) {
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
        //保存结果
        File output= new File(filepath);
        FileWriter out =new FileWriter(output);
        out.write("ncols         218"+"\n");
        out.write("nrows         236"+"\n");
        out.write("xllcorner     466515.47101027"+"\n");
        out.write("yllcorner     2626221.2241437"+"\n");
        out.write("cellsize      90"+"\n");
        out.write("NODATA_value  -9999"+"\n");
        for(i=0;i<236;i++){
            for(j=0;j<218;j++){
                out.write(acc[i][j]+" ");
            }
            out.write("\n");
        }
        out.close();
        return acc;
    }

    //按阈值提取河网
    public static void river_get(int[][] acc,int thresold,String filepath) throws IOException {
        int river[][] = new int[236][218];
        int i=0;
        int j=0;
        for(i=0;i<236;i++){
            for(j=0;j<218;j++){
                river[i][j]=0;
            }
        }
        //进行阈值判断
        for(i=0;i<236;i++){
            for(j=0;j<218;j++){
                if(acc[i][j]==-9999){
                    river[i][j]=-9999;
                }
                else if(acc[i][j]>=thresold){
                        river[i][j]=1;
                }
                else{
                    river[i][j]=0;
                }
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
        out.write("NODATA_value  -9999"+"\n");
        for(i=0;i<236;i++){
            for(j=0;j<218;j++){
                out.write(river[i][j]+" ");
            }
            out.write("\n");
        }
        out.close();
    }
}
