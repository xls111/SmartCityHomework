package Dao;

import Entity.Rain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 雨量数据处理类
 */
public class RainDao {

    /**
     * 将雨量数据写入列表
     *
     * @param rain 雨
     */
    public void writeRainToList(Rain rain) {
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

    /**
     * 显示测站的雨量数据
     *
     * @param rain 雨量
     */
    public void showRain(Rain rain) {
        for (List<?> objects : rain.getRain()) {
            for (int q = 0; q < objects.size(); q++) {
                System.out.print(objects.get(q) + " ");
            }
            System.out.println();
        }
    }
}
