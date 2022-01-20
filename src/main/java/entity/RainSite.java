package entity;

public class RainSite {

    private double x;
    private double y;
    private double rain;
    private double elevation;

    public RainSite() {
        this.x = 0;
        this.y = 0;
        this.rain = 0;
        this.elevation = 0;
    }

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
