package entity;

import java.util.ArrayList;
import java.util.List;

public class Dem {
    private String configPath;

    private GridFileHead head;

    private List<List<Double>> dem = new ArrayList<List<Double>>();

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


}
