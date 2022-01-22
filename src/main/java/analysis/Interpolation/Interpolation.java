package analysis.Interpolation;

import Entity.Grid;
import Entity.RainSite;

/**
 * 插值接口
 */
public interface Interpolation {

    /**
     * 根据参考站点数据，求出单个格网的雨量插值数据
     * @param s 某雨量站点
     * @return double
     */
    public double getCellRainByInterpolation(RainSite s);

    /**
     * 根据参考站点数据，求出所有格网的雨量插值数据
     *
     * @param grid 网格
     */
    public void getAllRainByInterpolation(Grid grid);
}
