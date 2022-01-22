package analysis.Interpolation;

import Entity.Grid;
import Entity.RainSite;

import java.util.List;

public class InterpolationUtils {

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

    public RainSite findSite(Grid grid, double x, double y) {
        int x_ = (int) Math.ceil((x - grid.getXllcorner()) / (grid.getCellsize()));
        int y_ = (int) Math.ceil((y - grid.getYllcorner()) / (grid.getCellsize()));

        return grid.getSites()[x_][y_];
    }

    public RainSite findSite(Grid grid,RainSite refSite){
        double x = refSite.getX();
        double y = refSite.getY();
        return findSite(grid,x,y);
    }
    public static double getDistance(RainSite s1, RainSite s2) {
        return Math.sqrt(Math.pow((s1.getX() - s2.getX()), 2) + Math.pow((s1.getY() - s2.getY()), 2));
    }


}
