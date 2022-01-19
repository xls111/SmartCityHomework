package entity;

import java.util.ArrayList;
import java.util.List;

public class Dem {
    private String configPath;

    private GridFileHead head;

    private List<List<Double>> dem = new ArrayList<List<Double>>();

    private List<List<?>> station = new ArrayList<List<?>>();

    private List<List<?>> rain = new ArrayList<List<?>>();

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public GridFileHead getHead() {
        return head;
    }

    public void setHead(GridFileHead head) {
        this.head = head;
    }

    public List<List<Double>> getDem() {
        return dem;
    }

    public void setDem(List<List<Double>> dem) {
        this.dem = dem;
    }

    public List<List<?>> getStation() {
        return station;
    }

    public void setStation(List<List<?>> station) {
        this.station = station;
    }

    public List<List<?>> getRain() {
        return rain;
    }

    public void setRain(List<List<?>> rain) {
        this.rain = rain;
    }
}
