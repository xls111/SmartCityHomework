package Dao;

import entity.Dem;
import entity.Station;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StationDao {

    public void readStationToList(Station station) {
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

    public void showStation(Station station) {
        for (List<?> objects : station.getStation()) {
            for (int q = 0; q < objects.size(); q++) {
                System.out.print(objects.get(q) + " ");
            }
            System.out.println();
        }
    }

}
