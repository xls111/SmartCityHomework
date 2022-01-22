package analysis.Interpolation;

import Entity.Grid;
import Entity.RainSite;

import static analysis.Interpolation.InterpolationUtils.getDistance;

public class IDW implements Interpolation {

    private final RainSite[] refSites;

    public IDW(RainSite[] sites){
        this.refSites = sites;
    }

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
