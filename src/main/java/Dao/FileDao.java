package Dao;

import entity.GridFileHead;

import java.io.*;

public class FileDao {
    /**
     * 读取asc文件的数字数据
     *
     * @param filePath 文件路径
     * @return {@link double[][] 网格数据}
     */
    public double[][] readGridFileToDoubleArray2D(String filePath) {

        double[][] resultData = null;

        try {
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
            resultData = new double[nrows][ncols];
            splitTab = "\t";
            tabIndex = strData[6].indexOf(splitTab);

            if (tabIndex == -1) {
                splitTab = " ";
            }

            for (int i = 0; i < nrows; i++) {
                tempData = strData[i + 6].split(splitTab);
                for (int j = 0; j < ncols; j++) {
                    resultData[i][j] = Double.parseDouble(tempData[j]);
                }
            }
            in.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultData;
    }

    /**
     * 读取asc文件头信息
     *
     * @param filePath 文件路径
     * @return {@link GridFileHead 文件头}
     * @throws IOException
     */
    public static GridFileHead ReadGridFileHead(String filePath) throws IOException {

        GridFileHead gridUnit = new GridFileHead();

        try {

            DataInputStream in = new DataInputStream(new FileInputStream(filePath));
            int fileSize = 512;
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
            gridUnit.ncols = Integer.parseInt(tempData[tempData.length - 1].trim());
            tempData = strData[1].split(splitTab);
            gridUnit.nrows = Integer.parseInt(tempData[tempData.length - 1].trim());
            tempData = strData[2].split(splitTab);
            gridUnit.xllcorner = Double.parseDouble(tempData[tempData.length - 1].trim());
            tempData = strData[3].split(splitTab);
            gridUnit.yllcorner = Double.parseDouble(tempData[tempData.length - 1].trim());
            tempData = strData[4].split(splitTab);
            gridUnit.cellsize = Double.parseDouble(tempData[tempData.length - 1].trim());
            tempData = strData[5].split(splitTab);
            gridUnit.NODATA_value = Integer.parseInt(tempData[tempData.length - 1].trim());

            in.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return gridUnit;

    }

    /**
     * 将二维数据数组写入文件
     *
     * @param filePath     文件路径
     * @param arrDouble2D  二维数组，存放数据
     * @param gridFileHead 文件头信息
     */
    public static void writeDoubleArray2DtoGridFile(
            String filePath,
            double[][] arrDouble2D,
            GridFileHead gridFileHead
    ) {
        try {
            File hFileCreate = new File(filePath);
            hFileCreate.createNewFile();
            RandomAccessFile hFileWrite = new RandomAccessFile(filePath, "rw");

            //写文件头
            String strHead = "ncols    " + gridFileHead.ncols + "\r\n" +
                    "nrows    " + gridFileHead.nrows + "\r\n" +
                    "xllcorner    " + gridFileHead.xllcorner + "\r\n" +
                    "yllcorner    " + gridFileHead.yllcorner + "\r\n" +
                    "cellsize    " + gridFileHead.cellsize + "\r\n" +
                    "NODATA_value    " + gridFileHead.NODATA_value + "\r\n";
            hFileWrite.write((strHead).getBytes());

            //写数据内容
            for (int i = 0; i < arrDouble2D.length; i++) {
                String line = "";
                for (int j = 0; j < arrDouble2D[i].length; j++) {
                    line += arrDouble2D[i][j] + " ";
                }
                line += "\r\n";
                hFileWrite.write((line).getBytes());
            }
            hFileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeIntegerArray2DtoGridFile(
            String filePath,
            int[][] arrInt2D,
            GridFileHead gridFileHead
    ) {
        try {
            File hFileCreate = new File(filePath);
            hFileCreate.createNewFile();
            RandomAccessFile hFileWrite = new RandomAccessFile(filePath, "rw");

            //写文件头
            String strHead = "ncols    " + gridFileHead.ncols + "\r\n" +
                    "nrows    " + gridFileHead.nrows + "\r\n" +
                    "xllcorner    " + gridFileHead.xllcorner + "\r\n" +
                    "yllcorner    " + gridFileHead.yllcorner + "\r\n" +
                    "cellsize    " + gridFileHead.cellsize + "\r\n" +
                    "NODATA_value    " + gridFileHead.NODATA_value + "\r\n";
            hFileWrite.write((strHead).getBytes());

            //写数据内容
            for (int i = 0; i < arrInt2D.length; i++) {
                String line = "";
                for (int j = 0; j < arrInt2D[i].length; j++) {
                    line += arrInt2D[i][j] + " ";
                }
                line += "\r\n";
                hFileWrite.write((line).getBytes());
            }
            hFileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void showArray2D(double[][] array){
        for(int i = 0;i< array.length;i++)
            for(int j=0;j<array[0].length;j++){
                System.out.print(array[i][j] + " ");
            }
        System.out.println();
    }

    public static void showArray2D(int[][] array){
        for(int i = 0;i< array.length;i++)
            for(int j=0;j<array[0].length;j++){
                System.out.print(array[i][j] + " ");
            }
        System.out.println();
    }
}
