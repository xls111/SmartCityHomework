package entity;

import java.util.ArrayList;
import java.util.List;

public class Station {

    private List<List<?>> station = new ArrayList<List<?>>();

    public List<List<?>> getStation() {
        return station;
    }

    public void setStation(List<List<?>> station) {
        this.station = station;
    }
}
