package Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 雨量测站
 */
public class Station {

    private List<List<?>> station = new ArrayList<List<?>>();   //存放雨量测站数据的列表

    public List<List<?>> getStation() {
        return station;
    }

    public void setStation(List<List<?>> station) {
        this.station = station;
    }
}
