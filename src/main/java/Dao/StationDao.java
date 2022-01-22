package Dao;

import Entity.Station;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 站点数据处理类
 */
public class StationDao {

    /**
     * 将测站点数据写入列表
     *
     * @param station 测站点类
     */
    public void writeStationToList(Station station) {
        int statnum = 0;
        int k = 1;//计数
        File statFile = new File("src/main/resources/StationProperty.txt");
        FileReader fis = null;
        try {
            fis = new FileReader(statFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fis);
        String line = null;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (k > 2) {
                List<Object> tempLine = new ArrayList<Object>();
                String[] newStr = line.split("\t");
                for (int i = 0; i < newStr.length; i++) {
                    if(i == 3 || i == 4){
                        tempLine.add(Double.parseDouble(newStr[i]));
                        continue;
                    }
                    tempLine.add(newStr[i]);
                }
                //System.out.println(aline1);
                station.getStation().add(tempLine);
            }
            //System.out.println(line);
            k++;
        }
        //System.out.println(station);
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示测站点数
     *
     * @param station 测站
     */
    public void showStation(Station station) {
        for (List<?> objects : station.getStation()) {
            for (int q = 0; q < objects.size(); q++) {
                System.out.print(objects.get(q) + " ");
            }
            System.out.println();
        }
    }

}
