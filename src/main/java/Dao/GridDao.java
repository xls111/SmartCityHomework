package Dao;

import entity.Grid;
import entity.GridFileHead;
import entity.RainSite;

import java.io.IOException;

public class GridDao {
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

    public static void showGridSitesRain(Grid grid){
        RainSite[][] sites = grid.getSites();
        for (RainSite[] siteRow : sites) {
            for (RainSite rainSite : siteRow) {
                System.out.print(rainSite.getRain() + " ");
            }
            System.out.println();
        }
    }
}

