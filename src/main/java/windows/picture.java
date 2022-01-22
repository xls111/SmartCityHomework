package windows;

import Dao.FileDao;
import Database.ReadDataFromDB;
import Entity.GridFileHead;

import java.awt.*;
import java.io.IOException;

/**
 * 二维数组图像展示窗口
 */
public class picture extends Canvas {

    public void paint(Graphics g) {
        //从数据库中读取dem数据
        String path = "src\\main\\resources\\dem.asc";
        ReadDataFromDB readData = new ReadDataFromDB();
        GridFileHead head = null;
        try {
            head = FileDao.ReadGridFileHead(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[][] demData = readData.readDemFromDB(head);
        double[][] map=demData;

        setBackground(Color.WHITE);
        //求最大dem，以实现后续颜色的离散表示
        double maxdem=0;
        for(int i=0;i<236;i++){
            for (int j=0;j<218;j++){
                if(maxdem<=map[i][j]){
                    maxdem=map[i][j];
                }
            }
        }
        System.out.println();
        //遍历区域，对区域内非nodata值单元进行颜色填充
        for(int i=0;i<236;i++){
            for(int j=0;j<218;j++){
                if(map[i][j]>=0) {
                    g.setColor(new Color(0,0,0,(int)(100*(1-map[i][j]/maxdem))));//dem越小，透明度越高
                    g.fillRect(j*2, (i - 18)*2, 2, 2);
                }
            }
        }
    }

}