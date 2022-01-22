package Entity;

/**
 * 雨的网站
 *
 * @author PC
 * @date 2022/01/22
 */
public class RainSite {

    private double x;
    private double y;
    private double rain;
    private double elevation;

    /**
     * 雨的网站
     */
    public RainSite() {
        this.x = 0;
        this.y = 0;
        this.rain = 0;
        this.elevation = 0;
    }

    /**
     * 雨的网站
     *
     * @param x         x
     * @param y         y
     * @param rain      雨
     * @param elevation 海拔高度
     */
    public RainSite(double x, double y, double rain,double elevation) {
        this.x = x;
        this.y = y;
        this.rain = rain;
        this.elevation = elevation;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
}
