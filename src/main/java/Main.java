import Dao.*;
import analysis.*;
import analysis.Interpolation.IDW;
import analysis.Interpolation.InterpolationUtils;
import analysis.Interpolation.Thiessen;
import Entity.*;
import Database.ReadDataFromDB;
import Database.StoreDataToDB;
import windows.LoginV;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("智慧城市水文模拟系统");
        System.out.println("------------------");
        System.out.println("测试数据存库");
        testStoreDataToDB();
        System.out.println("------------------");
        System.out.println("测试反距离权重插值");
        testIDw();
        System.out.println("------------------");
        System.out.println("测试泰森多边形插值");
        testThiessen();
        System.out.println("------------------");
        System.out.println("测试流向功能");
        testStoreFlowLengthToDB();
        testReadFlowDirectionFromDB();
        System.out.println("------------------");
        System.out.println("测试累积流量功能");
        testStoreFlowAccumulationToDB();
        testReadFlowAccumulationFromDB();
        System.out.println("------------------");
        System.out.println("测试坡度功能");
        testStoreSlopeToDB();
        testReadSlopeFromDB();
        System.out.println("------------------");
        System.out.println("测试坡向功能");
        testStoreAspectToDB();
        testReadAspectFromDB();
        System.out.println("------------------");
        System.out.println("测试水流长度功能");
        testStoreFlowLengthToDB();
        testReadFlowLengthFromDB();
        System.out.println("------------------");
        System.out.println("测试洼地填充");
        testStoreHoleFillingToDB();
        testReadHoleFillingFromDN();
        System.out.println("------------------");
        System.out.println("测试河网提取");
        testStoreStreamExtractionToDB();
        testReadStreamExtractionFromDB();
        System.out.println("------------------");
        System.out.println("测试上游汇入单元");
        testStoreUpStreamUnitsCountsToDB();
        testReadUpStreamUnitsCountsFromDB();
        System.out.println("------------------");
        System.out.println("测试可视域分析");
        testViewshed();
        System.out.println("------------------");
        System.out.println("测试可视化界面(只实现了dem表)");
        new LoginV();

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
        demDao.writeDemToList(dem);
        //dem.showDem();

        Rain rain = new Rain();
        RainDao rainDao = new RainDao();
        rainDao.writeRainToList(rain);
        //dem.showRain();

        Station station = new Station();
        StationDao stationDao = new StationDao();
        stationDao.writeStationToList(station);
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
        int[][] accumulation= FlowAccumulation.GetAccumulation(head,direction);
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
    public static void testStoreHoleFillingToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead gridFileHead = FileDao.ReadGridFileHead(path);
        double[][] holeFilling = HoleFilling.getHoleFilling(gridFileHead);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeHoleFillingToDB(holeFilling,gridFileHead);
    }

    public static void testReadHoleFillingFromDN() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        double[][] holefilling = reader.readHoleFillingFromDB(head);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/HoleFilling.asc",holefilling,head);
    }


    public static void testStoreStreamExtractionToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead gridFileHead = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        int[][] accumulation = reader.readFlowAccumulationFromDB(gridFileHead);
        int[][] river = StreamExtraction.getRiver(accumulation, 5);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeStreamExtractionToDB(river,gridFileHead);
    }

    public static void testReadStreamExtractionFromDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        int[][] river = reader.readStreamExtrcationFromDB(head);
        FileDao.writeIntegerArray2DtoGridFile("src/main/results/StreamExtraction.asc",river,head);
    }

    public static void testStoreUpStreamUnitsCountsToDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead gridFileHead = FileDao.ReadGridFileHead(path);
        int[][] direction= FlowDirection.getFlowDirection(gridFileHead);
        int[][] upValue = UpStreamUnitsCount.getUpValue(direction);
        StoreDataToDB storer = new StoreDataToDB();
        storer.storeUpStreamUnitsCountToDB(upValue,gridFileHead);
    }

    public static void testReadUpStreamUnitsCountsFromDB() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        ReadDataFromDB reader = new ReadDataFromDB();
        int[][] counts = reader.readUpStreamUnitsCountFromDB(head);
        FileDao.writeIntegerArray2DtoGridFile("src/main/results/UpStreamUnitsCounts.asc",counts,head);
    }

    public static void testViewshed() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        double[][] views = Viewshed.View(118, 50, head);
        FileDao.writeDoubleArray2DtoGridFile("src/main/results/Viewshed.asc",views,head);

    }

    public static void testStreamExtraction2() throws IOException {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = FileDao.ReadGridFileHead(path);
        int[][] flowDirection = FlowDirection.getFlowDirection(head);
        int[][] accumulation = FlowAccumulation.GetAccumulation(head, flowDirection);
        int[][] river = StreamExtraction.getRiver(accumulation, 10);
        FileDao.writeIntegerArray2DtoGridFile("src/main/results/StreamExtraction2.asc",river,head);
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
