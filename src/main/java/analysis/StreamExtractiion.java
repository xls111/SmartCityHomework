package analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StreamExtractiion {
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
