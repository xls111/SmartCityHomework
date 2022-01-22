package Entity;

import java.util.ArrayList;
import java.util.List;

public class Dem {

    // Dem文件路径
    private String configPath;

    // GridFileHead类型 文件头
    private GridFileHead head;

    // 存放dem的ArrayList列表
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
