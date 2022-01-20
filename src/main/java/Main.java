import Dao.RainDao;
import Dao.StationDao;
import entity.Dem;
import entity.GridFileHead;
import Dao.FileDao;
import Database.ReadDataFromDB;
import Database.StoreDataToDB;
import Dao.DemDao;
import entity.Rain;
import entity.Station;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("智慧城市大作业");
        Dem dem = new Dem();
        DemDao demDao = new DemDao();
        String path = "src\\main\\resources\\dem.asc";
        demDao.init(dem,path);
        demDao.readDemToList(dem);
        //dem.showDem();

        Rain rain = new Rain();
        RainDao rainDao = new RainDao();
        rainDao.readRainToList(rain);
        //dem.showRain();

        Station station = new Station();
        StationDao stationDao = new StationDao();
        stationDao.readStationToList(station);
        //dem.showStation();

        StoreDataToDB store = new StoreDataToDB();
        store.storeDemToDB(dem);
        store.storeRainToDB(rain);
        store.storeStationToDB(station);

        ReadDataFromDB readData = new ReadDataFromDB();
        GridFileHead head = FileDao.ReadGridFileHead(path);
        double[][] demData = readData.readDemFromDB(head);
        List<List<?>> rainData = readData.readRainFromDB();
        List<List<?>> stationData = readData.readStationFromDB();
//        System.out.println(rainData);
//        System.out.println(stationData);
//        Grid grid = getGridFromFile("src\\main\\resources\\dem.asc");
//        System.out.println(grid.getXllcorner());
//        double[][] map = grid.getMap();
//        for(int i = 0;i<map.length;i++){
//            for(int j=0;j<map[0].length;j++){
//                System.out.print(map[i][j]+"\t");
//            }
//            System.out.println();
//        }
    }
}
