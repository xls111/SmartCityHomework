package analysis.Interpolation;

import Entity.Grid;
import Entity.RainSite;

import static analysis.Interpolation.InterpolationUtils.getDistance;

public class Thiessen implements Interpolation {

    private final RainSite[] refSites;

    public Thiessen(RainSite[] sites){
        this.refSites = sites;
    }
    @Override
    public double getCellRainByInterpolation(RainSite s) {
        double rain = 0;
        double min = getDistance(s, refSites[0]);

        if (s.getElevation() == -9999){
            return -9999;
        }

        for (RainSite site : refSites) {
            if (getDistance(s, site) < min) {
                min = getDistance(s, site);
                rain = site.getRain();
            }
        }
        return rain;
    }

    @Override
    public void getAllRainByInterpolation(Grid grid) {
        int rows = grid.getRows();
        int cols = grid.getCols();
        RainSite[][] rainSites = grid.getSites();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double rain = getCellRainByInterpolation(rainSites[i][j]);
                rainSites[i][j].setRain(rain);
            }
        }
    }
}

