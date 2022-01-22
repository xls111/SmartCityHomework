package Dao;

import Entity.Dem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Dao.FileDao.ReadGridFileHead;


public class DemDao {

    public void init(Dem dem, String path) {
        try {
            dem.setHead(ReadGridFileHead(path));
            dem.setConfigPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDemToList(Dem dem) {
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



    public void showDem(Dem dem) {
        for (List<Double> doubles : dem.getDem()) {
            for (int q = 0; q < doubles.size(); q++) {
                double cellDem = Double.parseDouble(doubles.get(q).toString());    //
                System.out.print(cellDem + " ");
            }
            System.out.println();
        }
    }


}
