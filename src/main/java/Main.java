import entity.Dem;
import entity.GridFileHead;
import Dao.FileDao;
import Database.ReadDataFromDB;
import Database.StoreDataToDB;
import Dao.DemDao;

import analysis.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("智慧城市大作业");
        Dem dem = new Dem();
        DemDao demDao = new DemDao();
        String path = "src\\main\\resources\\dem.asc";
//        demDao.init(dem,path);
//        demDao.readDemToList(dem);
//        //dem.showDem();
//
//        demDao.readRainToList(dem);
//        //dem.showRain();
//
//        demDao.readStationToList(dem);
//        //dem.showStation();
//
//        StoreDataToDB store = new StoreDataToDB();
//        store.storeDemToDB(dem);
//        store.storeRainToDB(dem);
//        store.storeStationToDB(dem);
//
//        ReadDataFromDB readData = new ReadDataFromDB();
//        GridFileHead head = FileDao.ReadGridFileHead(path);
//        double[][] demData = readData.readDemFromDB(head);
//        List<List<?>> rainData = readData.readRainFromDB();
//        List<List<?>> stationData = readData.readStationFromDB();
//        System.out.println(rainData);
//        System.out.println(stationData);
//        Grid grid = getGridFromFile("src\\main\\resources\\dem.asc");
//        System.out.println(grid.getXllcorner());
//        double[][] map = grid.getMap();

        int ncols; //列数
        int nrows; //行数

        ncols = 0;
        nrows = 0;

        int cellsize;  //网格大小（米）
        int NODATA_value;  //边界外单元的值
        double xllcorner;   //左下角X坐标
        double yllcorner;   //左下角Y坐标
        int dems[][] = new int[236][218];
        String z;     //读文件时用的中间变量，值不予以保留
        File demFile = new File("src\\main\\resources\\dem.asc");
        try {
            Scanner inDEM = new Scanner(demFile);
            z = inDEM.next();
            ncols = inDEM.nextInt();
            z = inDEM.next();
            nrows = inDEM.nextInt();
            z = inDEM.next();
            xllcorner = inDEM.nextDouble();
            z = inDEM.next();
            yllcorner = inDEM.nextDouble();
            z = inDEM.next();
            cellsize = inDEM.nextInt();
            z = inDEM.next();
            NODATA_value = inDEM.nextInt();
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    dems[i][j] = inDEM.nextInt();
                }
            }
            inDEM.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        Dir_get.direction(dems,"src\\main\\results\\dir.asc");
        Upvalue_get.up_value_get(Dir_get.direction(dems,"src\\main\\results\\dir.asc"),"src\\main\\results\\up_value.asc");
        Accumulation_get.accum_get(Dir_get.direction(dems,"src\\main\\results\\dir.asc"),"src\\main\\results\\acc.asc");
        River_get.river_get(Accumulation_get.accum_get(Dir_get.direction(dems,"src\\main\\results\\dir.asc")
            ,"src\\main\\results\\acc.asc"),3,"src\\main\\results\\river.asc");

    }
}
