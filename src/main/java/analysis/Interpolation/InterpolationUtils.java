package analysis.Interpolation;

import Entity.Grid;
import Entity.RainSite;

import java.util.List;

/**
 * 插值工具类
 */
public class InterpolationUtils {

    /**
     * 设置参考雨量站
     *
     * @param rainData    降雨数据
     * @param stationData 测站数据
     * @param index       索引
     * @return {@link RainSite[]}
     */
    public RainSite[] setRefRainSites(List<List<?>> rainData, List<List<?>> stationData, int index) {
        RainSite[] refSites = new RainSite[stationData.size()];
        for (int i = 0; i < stationData.size(); i++) {
            refSites[i] = new RainSite();
            refSites[i].setX((double) stationData.get(i).get(1));
            refSites[i].setY((double) stationData.get(i).get(2));
            refSites[i].setRain((int) rainData.get(index).get(i + 2));
        }
        return refSites;
    }

    /**
     * 根据给定坐标寻找格网雨量站
     *
     * @param grid 网格
     * @param x    x
     * @param y    y
     * @return {@link RainSite}  寻找的雨量站点
     */
    public RainSite findSite(Grid grid, double x, double y) {
        int x_ = (int) Math.ceil((x - grid.getXllcorner()) / (grid.getCellsize()));
        int y_ = (int) Math.ceil((y - grid.getYllcorner()) / (grid.getCellsize()));

        return grid.getSites()[x_][y_];
    }

    /**
     * 在给定的栅格格网中寻找参考测站在格网中对应的雨量站
     *
     * @param grid    网格
     * @param refSite 参考网站
     * @return {@link RainSite}  参考测站在格网中对应的雨量站
     */
    public RainSite findSite(Grid grid,RainSite refSite){
        double x = refSite.getX();
        double y = refSite.getY();
        return findSite(grid,x,y);
    }

    /**
     * 求两个雨量站的距离
     *
     * @param s1 s1雨量站
     * @param s2 s2雨量站
     * @return double  雨量站间距离
     */
    public static double getDistance(RainSite s1, RainSite s2) {
        return Math.sqrt(Math.pow((s1.getX() - s2.getX()), 2) + Math.pow((s1.getY() - s2.getY()), 2));
    }


}
