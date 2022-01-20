package Dao;

import entity.Dem;
import entity.Rain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RainDao {

    public void readRainToList(Rain rain) {
        int k = 1;//计数
        File statFile = new File("src/main/resources/20050623.txt");
        FileReader fis = null;
        try {
            fis = new FileReader(statFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fis != null;
        BufferedReader br = new BufferedReader(fis);
        String line = null;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (k > 2) {
                List<Object> tempList = new ArrayList<Object>();
                String[] newStr = line.split("\t");
                for (int i = 0; i < newStr.length; i++) {
                    if (i == 1) {
                        tempList.add(Double.parseDouble(newStr[i]));
                        continue;
                    }
                    tempList.add(Integer.parseInt(newStr[i]));
                }
                rain.getRain().add(tempList);
            }
            k++;
        }
        //System.out.println(rain);

        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO:添加字段头信息
    public void showRain(Rain rain) {
        for (List<?> objects : rain.getRain()) {
            for (int q = 0; q < objects.size(); q++) {
                System.out.print(objects.get(q) + " ");
            }
            System.out.println();
        }
    }
}
