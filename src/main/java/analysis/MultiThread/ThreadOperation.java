package analysis.MultiThread;

import Dao.FileDao;
import analysis.Aspect;
import analysis.FlowDirection;
import analysis.HoleFilling;
import analysis.Slope;
import Entity.GridFileHead;

import java.io.IOException;

public class ThreadOperation implements Runnable{

    private String method;

    private double[][] doubleResults;
    private int[][] intResults;

    public ThreadOperation(String method) throws IOException {
        this.method = method;
    }

    public void selectMethod(GridFileHead head, String method){

        switch(method){
            case "Aspect":
                doubleResults = Aspect.getAspect(head);
                break;
            case "FlowDirection":
                try {
                    intResults = FlowDirection.getFlowDirection(head);
                    FileDao.showArray2D(intResults);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "HoleFilling":
                doubleResults = HoleFilling.getHoleFilling(head);
                FileDao.showArray2D(doubleResults);
                break;
            case "Slope":
                try {
                    doubleResults = Slope.getSlope(head);
                    FileDao.showArray2D(doubleResults);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("请输入正确的方法名！");
        }
    }

	//通过switch语句实现多个线程中不同功能的运行
    @Override
    public void run() {
        String path = "src\\main\\resources\\dem.asc";
        GridFileHead head = null;
        try {
            head = FileDao.ReadGridFileHead(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectMethod(head,this.method);
    }

//	public double[][][] MultiThread() throws IOException {
//		double[][][] re = new double[4][][];
//		ThreadOperation[] thr = null;
//		thr[0] = new ThreadOperation("Aspect");
//		thr[1] = new ThreadOperation("FlowDirection");
//		thr[2] = new ThreadOperation("HoleFilling");
//		thr[3] = new ThreadOperation("Slope");
//		for (int i = 0; i < 3; i++) {
//			thr[i].run();
//			re[i] = thr[i].result;
//		}
//		return re;
//	}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double[][] getDoubleResults() {
        return doubleResults;
    }

    public void setDoubleResults(double[][] doubleResults) {
        this.doubleResults = doubleResults;
    }

    public int[][] getIntResults() {
        return intResults;
    }

    public void setIntResults(int[][] intResults) {
        this.intResults = intResults;
    }
}
