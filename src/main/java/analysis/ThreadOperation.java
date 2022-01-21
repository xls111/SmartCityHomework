package analysis;

import Dao.FileDao;
import Database.ReadDataFromDB;
import entity.GridFileHead;
import java.io.IOException;

public class ThreadOperation implements Runnable{
	String path = "src\\main\\resources\\dem.asc";
	GridFileHead head = FileDao.ReadGridFileHead(path);
	int nrows = head.nrows;
	int ncols = head.ncols;
	double cellsize = head.cellsize;
	int NODATA_value = head.NODATA_value;
	double xllcorner = head.xllcorner;
	double yllcorner = head.yllcorner;
	double[][] demarray = ReadDataFromDB.readDemFromDB(head);
//	double[][] stationarray=new double[3][3];
//	double[][] rainarray=new double[55][5];

	//result用来存放计算结果
	double[][] result = demarray;
    public String method;
    public ThreadOperation(String method) throws IOException {
        this.method = method;
    }
	//通过switch语句实现多个线程中不同功能的运行
    @Override
    public void run() {
		switch(method){
			case "Aspect":
				result = Aspect.aspect(demarray,nrows,ncols,NODATA_value,cellsize);
				break;
			case "FlowDirection":
				try {
					result = FlowDirection.getFlowDirection(demarray,nrows,ncols,NODATA_value);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "HoleFilling":
				result = HoleFilling.hole_filling(demarray,nrows,ncols,NODATA_value);
				break;
			case "Slope":
				try {
					result = Slope.slope(demarray,nrows,ncols,NODATA_value,cellsize,path);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
    }

	public static double[][][] MultiThread() throws IOException {
		double[][][] re = new double[4][][];
		ThreadOperation[] thr = new ThreadOperation[4];
		thr[0] = new ThreadOperation("Aspect");
		thr[1] = new ThreadOperation("FlowDirection");
		thr[2] = new ThreadOperation("HoleFilling");
		thr[3] = new ThreadOperation("Slope");
		for (int i = 0; i <= 3; i++) {
			thr[i].run();
			re[i] = thr[i].result;
		}
		return re;
	}

	public static void main(String[] args) throws IOException {
		double[][][] re = MultiThread();
		int i=0;
	}
}
