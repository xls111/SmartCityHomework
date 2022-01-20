import Dao.*;
import analysis.Interpolation.IDW;
import analysis.Interpolation.InterpolationUtils;
import analysis.Interpolation.Thiessen;
import entity.*;
import Database.ReadDataFromDB;
import Database.StoreDataToDB;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
       //testIDw();
       testThiessen();
    }

    public static void testIDw() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        Grid grid = new Grid();
        GridDao.initGridFromFile(grid,path);
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB readData = new ReadDataFromDB();
        RainSite[][] sitesData = readData.initSitesFromDB(head);
        grid.setSites(sitesData);

        List<List<?>> rainData = readData.readRainFromDB();
        List<List<?>> stationData = readData.readStationFromDB();

        InterpolationUtils utils = new InterpolationUtils();
        RainSite[] refSite = utils.setRefRainSites(rainData,stationData,2);
        RainSite[] sites = new RainSite[refSite.length];
        for(int i = 0;i< refSite.length;i++){
            sites[i] = utils.findSite(grid,refSite[i]);
            sites[i].setRain(refSite[i].getRain());
            sites[i].setElevation(refSite[i].getElevation());
        }
        IDW IDWInterpolation = new IDW(sites);
        IDWInterpolation.getAllRainByInterpolation(grid);

        GridDao.showGridSitesRain(grid);
        System.out.println(".......");
    }

    public static void testThiessen() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        Grid grid = new Grid();
        GridDao.initGridFromFile(grid,path);
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB readData = new ReadDataFromDB();
        RainSite[][] sitesData = readData.initSitesFromDB(head);
        grid.setSites(sitesData);

        List<List<?>> rainData = readData.readRainFromDB();
        List<List<?>> stationData = readData.readStationFromDB();

        InterpolationUtils utils = new InterpolationUtils();
        RainSite[] refSite = utils.setRefRainSites(rainData,stationData,2);
        RainSite[] sites = new RainSite[refSite.length];
        for(int i = 0;i< refSite.length;i++){
            sites[i] = utils.findSite(grid,refSite[i]);
            sites[i].setRain(refSite[i].getRain());
            sites[i].setElevation(refSite[i].getElevation());
        }
        Thiessen ThiessenInterpolation = new Thiessen(sites);
        ThiessenInterpolation.getAllRainByInterpolation(grid);

        GridDao.showGridSitesRain(grid);
        System.out.println(".......");
    }

    public static void testStoreDataToDB(){
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
    }


    public static void testSetRefRainSites() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB readData = new ReadDataFromDB();
        RainSite[][] sitesData = readData.initSitesFromDB(head);
        List<List<?>> rainData = readData.readRainFromDB();
        List<List<?>> stationData = readData.readStationFromDB();

        InterpolationUtils myutils = new InterpolationUtils();
        RainSite[] refSite = myutils.setRefRainSites(rainData,stationData,2);
    }
}
