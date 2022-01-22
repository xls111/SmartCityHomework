package analysis;

import Entity.GridFileHead;

import java.util.Arrays;

public class FlowLength {
    /**
     * @param head 头文件
     * @param dir 流向数组
     * @return 河流长度
     */
    public static double[][] getFlowLengthDown(GridFileHead head, int[][] dir) {
        int nodataValue = head.NODATA_value;
        int gridRows = head.nrows;
        int gridCols = head.ncols;
        int dirRows = dir.length;
        int dirCols = dir[0].length;

        if (gridRows != dirRows || gridCols != dirCols)
            throw new IllegalArgumentException("栅格格网大小与流向不匹配");

        double[][] flowLength = new double[dirRows][dirCols];
        int[] direction = {1, 2, 4, 8, 16, 32, 64, 128};     // 存放方向数组

        for (int i = 0; i < dirRows; i++)
            for (int j = 0; j < dirCols; j++) {
                if (dir[i][j] == nodataValue) {
                    flowLength[i][j] = nodataValue;  // 若流向不存在则返回无数据值
                    continue;
                }
                if (dir[i][j] == 0) {
                    flowLength[i][j] = 0;           // 若流向为0，则水流长度为0
                    continue;
                }
                int row = i;
                int col = j;

                //流向大于零时，循环遍历
                while (dir[row][col] > 0) {
                    // 垂直方向，距离增加一个单元长度
                    if (dir[row][col] == 1 || dir[row][col] == 4 || dir[row][col] == 16 || dir[row][col] == 64)
                        flowLength[i][j] += head.cellsize;
                    else
                        // 斜角方向，距离增加根号二倍单元长度
                        flowLength[i][j] += head.cellsize * Math.sqrt(2);

                    // 存放更新的坐标索引
                    int[] tempX = {row, row + 1, row + 1, row + 1, row, row - 1, row - 1, row - 1};
                    int[] tempY = {col + 1, col + 1, col, col - 1, col - 1, col - 1, col, col + 1};

                    int index = Arrays.binarySearch(direction, dir[row][col]);  // 寻找该单元格流向值在direction流向数组的索引
                    row = tempX[index];     // 更新横坐标
                    col = tempY[index];     // 更新纵坐标
                }
            }

        return flowLength;
    }
}
