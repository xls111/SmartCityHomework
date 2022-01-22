package analysis.Interpolation;

import Entity.Grid;
import Entity.RainSite;

import static analysis.Interpolation.InterpolationUtils.getDistance;

/**
 * 反距离权重插值
 */
public class IDW implements Interpolation {

    private final RainSite[] refSites;

    public IDW(RainSite[] sites){
        this.refSites = sites;
    }

    /**
     * 按照IDW插值原理，根据参考站点数据，求出单个格网的雨量插值数据
     *
     * @param s 站点
     * @return double
     */
    @Override
    public double getCellRainByInterpolation(RainSite s) {
        double sum = 0;
        double rain = 0;

        if (s.getElevation() == -9999){
            return -9999;
        }
        for (RainSite site : refSites) {
            sum += (1 / getDistance(s, site));
        }

        for (RainSite site : refSites) {
            if (s != site) {
                double distance = getDistance(s, site);
                double lambda = (1 / distance) / sum;
                rain += lambda * site.getRain();
            } else {
                rain = site.getRain();
            }

        }
        return rain;
    }

    /**
     * 按照泰森多边形插值原理，根据参考站点数据，求出所有格网的雨量插值数据
     *
     * @param grid 网格
     */
    @Override
    public void getAllRainByInterpolation(Grid grid){
        int rows = grid.getRows();
        int cols = grid.getCols();
        RainSite[][] rainSites = grid.getSites();

        for(int i = 0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                double rain = getCellRainByInterpolation(rainSites[i][j]);
                rainSites[i][j].setRain(rain);
            }
        }
    }

}
