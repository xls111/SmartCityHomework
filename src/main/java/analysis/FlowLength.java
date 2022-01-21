package analysis;

import entity.Grid;
import entity.RainSite;

import java.util.Arrays;

public class FlowLength {
    public static double[][] getFlowLengthDown(Grid grid, int[][] dir) {
        RainSite[][] rainSites = grid.getSites();
        int nodataValue = grid.getNODATA_value();
        int gridRows = rainSites.length;
        int gridCols = rainSites[0].length;
        int dirRows = dir.length;
        int dirCols = dir[0].length;

        if (gridRows != dirRows || gridCols != dirCols)
            throw new IllegalArgumentException("栅格格网大小与流向不匹配");

        double[][] flowLength = new double[dirRows][dirCols];
        int[] direction = {1, 2, 4, 8, 16, 32, 64, 128};
        for (int i = 0; i < dirRows; i++)
            for (int j = 0; j < dirCols; j++) {
                if (dir[i][j] == nodataValue) {
                    flowLength[i][j] = nodataValue;
                    continue;
                }
                if (dir[i][j] == 0) {
                    flowLength[i][j] = 0;
                    continue;
                }
                int row = i;
                int col = j;
                while (dir[row][col] > 0) {
                    if (dir[row][col] == 1 || dir[row][col] == 4 || dir[row][col] == 16 || dir[row][col] == 64)
                        flowLength[i][j] += grid.getCellsize();
                    else
                        flowLength[i][j] += grid.getCellsize() * Math.sqrt(2);

                    int[] tempX = {row, row + 1, row + 1, row + 1, row, row - 1, row - 1, row - 1};
                    int[] tempY = {col + 1, col + 1, col, col - 1, col - 1, col - 1, col, col + 1};

                    int index = Arrays.binarySearch(direction, dir[row][col]);
                    row = tempX[index];
                    col = tempY[index];
                }
            }

        return flowLength;
    }
}
