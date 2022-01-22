package analysis.Interpolation;

import Entity.Grid;
import Entity.RainSite;

public interface Interpolation {
    public double getCellRainByInterpolation(RainSite s);

    public void getAllRainByInterpolation(Grid grid);
}
