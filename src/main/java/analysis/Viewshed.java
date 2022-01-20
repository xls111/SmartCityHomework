package analysis;

import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
//import java.util.HashSet;

public class Viewshed {
    public static double[][] View(double pointCoordinateX,double pointCoordinateY, double Dem[][],int nrows,int ncols,double Nodata) throws IOException {
        //可视域分析
        double[][] view=new double[nrows][ncols];

        class Point{
            double X;
            double Y;

            public Point(double x,double y){
                X=x;
                Y=y;
            }

            public Point(){
                X=0;
                Y=0;
            }
        }

        //以每个像元中心作为起点
        Point startPoint=new Point(pointCoordinateX+0.5,pointCoordinateY+0.5);

        if(Dem[(int) pointCoordinateX][(int) pointCoordinateY]==Nodata)
            System.out.println("The point is not exist");
        else {

            for (int i = 0; i < nrows; i++)
                for (int j = 0; j < ncols; j++) {

                    if (Dem[i][j] == Nodata)
                        view[i][j] = Nodata;
                    else {

                        // 设置数据列表空间
                        ArrayList<Point> temList = new ArrayList<Point>();
                        ArrayList<Point> gridList = new ArrayList<Point>();
//                        HashSet<Point> hs=new HashSet<Point>();
                        // 以目标像元中心作为终点
                        Point endPoint = new Point(i+0.5, j+0.5);

                        double dx = endPoint.X - startPoint.X;
                        double dy = endPoint.Y- startPoint.Y;

                        // 设置增量
                        double increX = dx / Math.abs(dx);
                        double increY = dy / Math.abs(dy);
                        // 直线方程：斜截式
                        double k = dy / dx;
                        double b = -k * startPoint.X + startPoint.Y;
                        // 开始计算交点
                        for (int m = 0; m <= Math.abs(dx)-1; m++) {
                            // 沿横轴方向遍历，既查询与网格轴轴的交点
                            Point temPoint1 = new Point();
                            
                            //根据dx正负确定向上或向下取整
                            if(dx>0)
                                temPoint1.X = Math.ceil(startPoint.X) + increX * m;
                            else if (dx<0)
                                temPoint1.X = Math.floor(startPoint.X) + increX * m;
                            else
                                temPoint1.X = startPoint.X + increX * m;
                            temPoint1.Y = k * temPoint1.X + b;
                            temList.add(temPoint1);
                        }
                        // 沿纵轴方向遍历，既查询与网格横轴的交点
                        for (var n = 0; n <= Math.abs(dy)-1; n++) {
                            Point temPoint2 = new Point();
                            
                            //根据dy正负确定向上或向下取整
                            if (dy>0)
                                temPoint2.Y = Math.ceil(startPoint.Y) + increY * n;
                            else if(dy<0)
                                temPoint2.Y = Math.floor(startPoint.Y) + increY * n;
                            else
                                temPoint2.Y = startPoint.Y + increY * n;
                            temPoint2.X = (temPoint2.Y - startPoint.Y) / k + startPoint.X;
                            temList.add(temPoint2);

                        }

                        for (int kk=0; kk<(temList.size());kk++) {
                            Point temGridUp = new Point();
                            Point temGridDown = new Point();
                            Point temGridLeft = new Point();
                            Point temGridRight = new Point();
                            Point temGridCenter = new Point();
                            // 横轴交点
                            if((temList.get(kk).X !=Math.ceil(temList.get(kk).X)) &&
                                    (temList.get(kk).Y ==Math.ceil(temList.get(kk).Y))){
                                temGridUp.X = Math.ceil(temList.get(kk).X);
                                temGridUp.Y = Math.ceil(temList.get(kk).Y)+1;
                                temGridDown.X = Math.ceil(temList.get(kk).X);
                                temGridDown.Y = Math.ceil(temList.get(kk).Y);
                                gridList.add(temGridDown);
                                gridList.add(temGridUp);
                            }
                            // 纵轴交点
                            else if((temList.get(kk).Y !=Math.ceil(temList.get(kk).Y)) &&
                                    (temList.get(kk).X ==Math.ceil(temList.get(kk).X))) {
                                temGridLeft.X = Math.ceil(temList.get(kk).X);
                                temGridLeft.Y = Math.ceil(temList.get(kk).Y);
                                temGridRight.X = Math.ceil(temList.get(kk).X)+1;
                                temGridRight.Y = Math.ceil(temList.get(kk).Y);
                                gridList.add(temGridLeft);
                                gridList.add(temGridRight);
                            }
                            // 斜率为+-1交点
                            else if((temList.get(kk).Y ==Math.ceil(temList.get(kk).Y)) &&
                                    (temList.get(kk).X ==Math.ceil(temList.get(kk).X))) {
                                temGridCenter.X = Math.ceil(temList.get(kk).X+0.5*increX);
                                temGridCenter.Y = Math.ceil(temList.get(kk).Y+0.5*increY);
                                gridList.add(temGridCenter);
                            }
                        }

//                         hs.addAll(gridList);
//                         gridList.clear();
//                         gridList.addAll(hs);
                        double max=Dem[i][j];
                        for(int kk=0;kk<gridList.size();kk++){
                            if((gridList.get(kk).X-1==x1&& gridList.get(kk).Y-1==y1)||(gridList.get(kk).X-1==i&& gridList.get(kk).Y-1==j))
                                continue;
                            else if(Dem[(int) gridList.get(kk).X-1][(int) gridList.get(kk).Y-1]>max)
                                max=Dem[(int) gridList.get(kk).X-1][(int) gridList.get(kk).Y-1];
                        }
                        if(max>Dem[i][j])
                            view[i][j]=0;
                        else {
                            view[i][j] = 1;
                        }
                        view[(int) pointCoordinateX][(int) pointCoordinateY]=2;
                    }
                }
        }
        return view;
    }
}
