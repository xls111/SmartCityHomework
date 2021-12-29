import domain.Grid;
import domain.GridFileHead;
import methods.Utils.Utils;

import java.io.IOException;

import static methods.File.Dem.*;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("智慧城市大作业");

        Grid grid = getGridFromFile("src\\main\\resources\\dem.asc");
        System.out.println(grid.getBx());
        double[][] map = grid.getMap();
        for(int i = 0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                System.out.print(map[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
