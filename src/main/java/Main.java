import Dao.*;
import analysis.*;
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
       //testThiessen();
        testStoreDataToDB();
        Grid grid = configGrid();
        testStoreFlowDirectionToDB();
        testReadFlowDirectionFromDB();
        testStoreFlowAccumulationToDB();
        testStoreSlopeToDB();
        testStoreFlowLengthToDB();
        testStoreAspectToDB();
        testReadAspectFromDB();
        testReadFlowLengthFromDB();
        testReadSlopeFromDB();
        testReadFlowAccumulationFromDB();
        testStoreViewshed();
    }

    public static Grid configGrid() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        Grid grid = new Grid();
        GridDao.initGridFromFile(grid,path);
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB readData = new ReadDataFromDB();
        RainSite[][] sitesData = readData.initSitesFromDB(head);
        grid.setSites(sitesData);
        return grid;
    }

    public static RainSite[] configRefSites(Grid grid) throws IOException {

        ReadDataFromDB readData = new ReadDataFromDB();

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
        return sites;
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
        double[][] rain = GridDao.getRainArrayFromGrid(grid);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/IDWRain.asc",rain,head);
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
        double[][] rain = GridDao.getRainArrayFromGrid(grid);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/ThiessenRain.asc",rain,head);
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

    public static void testStoreFlowDirectionToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        int[][] direction= FlowDirection.getFlowDirection(head);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeFlowDirectionToDB(direction,head);
    }

    public static void testReadFlowDirectionFromDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        int[][] directions = reader.readFlowDirectionFromDB(head);
        FileDao.writeIntegerArray2DtoGridFile("src/main/results/FlowDirection.asc",directions,head);
        FileDao.showArray2D(directions);
    }

    public static void testStoreFlowAccumulationToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        int[][] direction= FlowDirection.getFlowDirection(head);
        int[][] accumulation= FlowAccumulation.GetAccumulation(direction);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeFlowAccumulationToDB(accumulation,head);
    }

    public static void testReadFlowAccumulationFromDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        int[][] accumulation = reader.readFlowAccumulationFromDB(head);
        FileDao.writeIntegerArray2DtoGridFile("src/main/results/FlowAccumulation.asc",accumulation,head);
        FileDao.showArray2D(accumulation);
    }

    public static void testStoreSlopeToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        double[][] slope = Slope.getSlope(head);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeSlopeToDB(slope,head);
    }

    public static void testReadSlopeFromDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] slope = reader.readSlopeFromDB(head);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/Slope.asc",slope,head);
    }

    public static void testStoreAspectToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        double[][] aspect = Aspect.getAspect(head);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeAspectToDB(aspect,head);
    }

    public static void testReadAspectFromDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] aspect = reader.readAspectFromDB(head);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/Aspect.asc",aspect,head);
    }

    public static void testStoreFlowLengthToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead gridFileHead = FileDao.ReadGridFileHead(path);
        int[][] direction= FlowDirection.getFlowDirection(gridFileHead);
        double[][] length = FlowLength.getFlowLengthDown(gridFileHead,direction);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeFlowLengthToDB(length,gridFileHead);
    }

    public static void testReadFlowLengthFromDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] length = reader.readFlowLengthFromDB(head);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/FlowLength.asc",length,head);
    }

    public static void testStoreViewshed() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead gridFileHead = FileDao.ReadGridFileHead(path);
        double[][] view = Viewshed.getView(118,50,gridFileHead);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/viewshed.asc",view,gridFileHead);
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
