package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class River_get {
    public static int[][] river_get(int[][] acc,int thresold) throws IOException {
        int nrows=236;
        int ncols=218;
        int river[][] = new int[nrows][ncols];
        int i=0;
        int j=0;
        for(i=0;i<nrows;i++){
            for(j=0;j<ncols;j++){
                river[i][j]=0;
            }
        }
        //进行阈值判断
        for(i=0;i<nrows;i++){
            for(j=0;j<ncols;j++){
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
        return river;
    }
}
