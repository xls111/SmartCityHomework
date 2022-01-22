package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StreamExtraction {
    public static int[][] getRiver(int[][] acc, int thresold) throws IOException {
        int nrows=acc.length;
        int ncols=acc[0].length;
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
