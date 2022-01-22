package Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 雨量
 */
public class Rain {

    private List<List<?>> rain = new ArrayList<List<?>>();  //存放不同时段测站雨量的列表

    public List<List<?>> getRain() {
        return rain;
    }

    public void setRain(List<List<?>> rain) {
        this.rain = rain;
    }
}
