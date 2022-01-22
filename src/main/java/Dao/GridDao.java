package Dao;

import Entity.Grid;
import Entity.GridFileHead;
import Entity.RainSite;

import java.io.IOException;

/**
 * 栅格格网数据处理类
 */
public class GridDao {
    /**
     * 从文件中初始化化网格
     *
     * @param grid 网格
     * @param path 文件路径
     * @throws IOException ioexception
     */
    public static void initGridFromFile(Grid grid, String path) throws IOException {
        GridFileHead head = FileDao.ReadGridFileHead(path);
        grid.setRows(head.nrows);
        grid.setCols(head.ncols);
        grid.setXllcorner(head.xllcorner);
        grid.setYllcorner(head.yllcorner);
        grid.setCellsize(head.cellsize);
        grid.setNODATA_value(head.NODATA_value);
        grid.setSites(new RainSite[head.nrows][head.ncols]);
    }

    /**
     * 显示网格站点雨量
     *
     * @param grid 网格
     */
    public static void showGridSitesRain(Grid grid) {
        RainSite[][] sites = grid.getSites();
        for (RainSite[] siteRow : sites) {
            for (RainSite rainSite : siteRow) {
                System.out.print(rainSite.getRain() + " ");
            }
            System.out.println();
        }
    }

    /**
     * 从格网中获取雨量数组
     *
     * @param grid 网格
     * @return {@link double[][]}  格网的雨量二维数组
     */
    public static double[][] getRainArrayFromGrid(Grid grid) {
        RainSite[][] sites = grid.getSites();
        double[][] rain = new double[grid.getRows()][grid.getCols()];

        for (int i = 0; i < sites.length; i++)
            for (int j = 0; j < sites[i].length; j++) {
                rain[i][j] = sites[i][j].getRain();
            }
        return rain;
    }

}

