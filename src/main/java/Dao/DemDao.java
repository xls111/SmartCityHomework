package Dao;

import entity.Dem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Dao.FileDao.ReadGridFileHead;

//TODO:将数据与数据操作分离
public class DemDao {

    public void init(Dem dem, String path) {
        try {
            dem.setHead(ReadGridFileHead(path));
            dem.setConfigPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDemToList(Dem dem) {
        try {
            String filePath = dem.getConfigPath();
            DataInputStream in = new DataInputStream(new FileInputStream(filePath));
            int fileSize = in.available();
            byte[] btData = new byte[fileSize];

            in.read(btData);
            String str = new String(btData);
            String splitTab = "\t";
            String[] strData = str.split("\n");
            int tabIndex = strData[0].indexOf(splitTab);

            if (tabIndex == -1) {
                splitTab = " ";
            }

            String[] tempData = strData[0].split(splitTab);
            int ncols = Integer.parseInt(tempData[tempData.length - 1].trim());
            tempData = strData[1].split(splitTab);
            int nrows = Integer.parseInt(tempData[tempData.length - 1].trim());
            //resultData = new double[nrows][ncols];
            splitTab = "\t";
            tabIndex = strData[6].indexOf(splitTab);

            if (tabIndex == -1) {
                splitTab = " ";
            }

            for (int i = 0; i < nrows; i++) {
                tempData = strData[i + 6].split(splitTab);
                List<Double> tempLine = new ArrayList<Double>();
                for (int j = 0; j < ncols; j++) {
                    tempLine.add(Double.parseDouble(tempData[j]));
                    //resultData[i][j] = Double.parseDouble(tempData[j]);
                }
                dem.getDem().add(tempLine);
            }
            in.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void readRainToList(Dem dem) {
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
                    if(i == 1){
                        tempList.add(Double.parseDouble(newStr[i]));
                        continue;
                    }
                    tempList.add(Integer.parseInt(newStr[i]));
                }
                dem.getRain().add(tempList);
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

    public void readStationToList(Dem dem) {
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
                dem.getStation().add(tempLine);
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

    public void showDem(Dem dem) {
        for (List<Double> doubles : dem.getDem()) {
            for (int q = 0; q < doubles.size(); q++) {
                double cellDem = Double.parseDouble(doubles.get(q).toString());    //
                System.out.print(cellDem + " ");
            }
            System.out.println();
        }
    }

    //TODO:添加字段头信息
    public void showRain(Dem dem) {
        for (List<?> objects : dem.getRain()) {
            for (int q = 0; q < objects.size(); q++) {
                System.out.print(objects.get(q) + " ");
            }
            System.out.println();
        }
    }

    public void showStation(Dem dem) {
        for (List<?> objects : dem.getStation()) {
            for (int q = 0; q < objects.size(); q++) {
                System.out.print(objects.get(q) + " ");
            }
            System.out.println();
        }
    }

}
