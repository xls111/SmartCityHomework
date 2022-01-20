package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FlowDirection {
    //计算流向
    public static int[][] direction(int dem_value[][]) throws IOException {
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

        return dir;
    }
}
