package analysis.Interpolation;

import entity.Grid;
import entity.RainSite;

public interface Interpolation {
    public double getCellRainByInterpolation(RainSite s);

    public void getAllRainByInterpolation(Grid grid);
}
