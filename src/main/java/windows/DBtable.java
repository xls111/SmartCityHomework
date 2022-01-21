package windows;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//dem数据表的管理界面
public class DBtable extends JFrame {
    private JTable table;
    private JPanel panel;
    private JScrollPane scrollpane;
    private JButton button1, button2, button3,button4;
    private JTextArea text1, text2, text3,text4;
    private List<Dem> dem;

    public DBtable() throws BadLocationException, SQLException {
        super("dem数据表信息管理");
        this.setSize(500, 340);
        this.add(getJScrollPane(dem), BorderLayout.CENTER);
        this.add(getJPanel(), BorderLayout.SOUTH);
        this.setResizable(true);
        this.setLocation(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 设置JScrollPane方法
    private JScrollPane getJScrollPane(List<Dem> dem) throws SQLException {
        if (scrollpane == null) {
            scrollpane = new JScrollPane();
            scrollpane.setViewportView(getJTable(dem));
        }
        return scrollpane;
    }

    // 设置JPanel方法
    private JPanel getJPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.setLayout(new GridLayout(2, 3));
            text1 = new JTextArea();
            text2 = new JTextArea();
            text3 = new JTextArea();
            text4 = new JTextArea();
            button1 = new JButton("添加");
            button2 = new JButton("删除");
            button3 = new JButton("更新");
            button4 = new JButton("图像展示");
            text1.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            text2.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            text3.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            text4.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            text1.setFont(new Font("宋体", Font.BOLD, 16));
            text2.setFont(new Font("宋体", Font.BOLD, 16));
            text3.setFont(new Font("宋体", Font.BOLD, 16));
            text4.setFont(new Font("宋体", Font.BOLD, 16));
            button1.addActionListener(new insert());
            button2.addActionListener(new delete());
            button3.addActionListener(new update());
            text1.setText("id");
            text2.setText("x");
            text3.setText("y");
            text4.setText("dem");
            panel.add(text1);
            panel.add(text2);
            panel.add(text3);
            panel.add(text4);
            panel.add(button1);
            panel.add(button2);
            panel.add(button3);
            panel.add(button4);
        }
        button4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                picture m=new picture();
                JFrame f=new JFrame();
                f.setTitle("Dem文件图像");
                f.add(m);
                f.setSize(218*2,236*2);
                f.setLocation(800, 300);
                //f.setLayout(null);
                f.setVisible(true);
            }
        });
        return panel;

    }


    // 设置Jtable方法
    private void setJTable(JTable table) {
        table.setFont(new Font("宋体", Font.BOLD, 18));
        table.setRowHeight(30);
    }

     //获取Jtable对象方法（该方法具体就是获得jtable对象的时候 一并从数据取出dem信息并放入Jtable表格中）
    private JTable getJTable(List<Dem> dem) throws SQLException {
        if (table == null) {
            JDBCDaoImpl jdbc = new JDBCDaoImpl();
            ResultSet rs = jdbc.search();
            dem = select(rs);
            jdbc.closeConnection();
            table = new JTable(new Table(dem));
            setJTable(table);
        }
        return table;
    }

    // 设置dem信息方法（该方法是用户增加 删除 更新用户操作的具体实现方法 包含了完整性检查）
    private Dem setStu() {
        if (text1.getText().equals("") || text2.getText().equals("") || text3.getText().equals("") ||text4.getText().equals("")) {
            return null;
        } else {
            Dem sd = new Dem();
            sd.setId(text1.getText());
            sd.setx(text2.getText());
            sd.sety(text3.getText());
            sd.setdem(text4.getText());
            return sd;

        }

    }

    // 重置输入框为空
    private void resetText() {
        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
    }

    // 刷新dem信息方法（该方法是重新读取数据库dem的信息 然后返回一个dem的集合 用于刷新Jtable表格对象中的数据）
    private List<Dem> select(ResultSet rs) throws SQLException {
        List<Dem> st = new ArrayList<Dem>();
        while (rs.next()) {
            Dem s = new Dem();
            s.setId(rs.getString(1));
            s.setx(rs.getString(2));
            s.sety(rs.getString(3));
            s.setdem(rs.getString(4));
            st.add(s);
        }
        return st;

    }

     //添加按钮-监听器（该方法是对添加按钮实现的具体方法 ）
    class insert implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dem = new ArrayList<Dem>();
            Dem sd = new Dem();
            JDBCDaoImpl jdbc = new JDBCDaoImpl();
            sd = setStu();
            if (sd != null) {
                jdbc.insert(sd);
                ResultSet rs = jdbc.search();
                try {
                    dem = select(rs);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                jdbc.closeConnection();
                JTable table = new JTable(new Table(dem));//新建一个Jtable 对象 用来盛放增加后的学生信息
                setJTable(table);//设置Jtable信息
                DBtable.this.scrollpane.setViewportView(table);//把Jtable设置到Panel
                resetText();
            } else {
                JOptionPane.showMessageDialog(DBtable.this, "输入数据不完整");
            }
        }
    }

    // 删除按钮-监听器（该方法是对删除按钮实现的具体方法）
    class delete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dem = new ArrayList<Dem>();
            Dem sd = new Dem();
            JDBCDaoImpl jdbc = new JDBCDaoImpl();
            sd = setStu();
            if (sd != null) {
                jdbc.delete(sd);
                ResultSet rs = jdbc.search();
                try {
                    dem = select(rs);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                jdbc.closeConnection();
                JTable table = new JTable(new Table(dem));//新建一个Jtable 对象 用来盛放增加后的学生信息
                setJTable(table);//设置Jtable信息
                DBtable.this.scrollpane.setViewportView(table);//把Jtable设置到Panel
                resetText();
            } else {
                JOptionPane.showMessageDialog(DBtable.this, "输入数据不完整");

            }

        }

    }

    // 更新按钮-监听器（该方法是对更新按钮实现的具体方法）
    class update implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dem = new ArrayList<Dem>();
            Dem sd = new Dem();
            JDBCDaoImpl jdbc = new JDBCDaoImpl();
            sd = setStu();
            if (sd != null) {
                jdbc.update(sd);
                ResultSet rs = jdbc.search();
                try {
                    dem = select(rs);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                jdbc.closeConnection();
                JTable table = new JTable(new Table(dem));//新建一个Jtable 对象 用来盛放增加后的学生信息
                setJTable(table);//设置Jtable信息
                DBtable.this.scrollpane.setViewportView(table);//把Jtable设置到Panel
                resetText();
            } else {
                JOptionPane.showMessageDialog(DBtable.this, "输入数据不完整");
            }
        }
    }

        // Student类 （用于封装数据信息和数据库表进行映射）
    public class Dem {
        // dem的四列信息
        private String id;
        private String x;
        private String y;
        private String dem;

        // get&set方法
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getx() {
            return x;
        }

        public void setx(String x) {
            this.x = x;
        }

        public String gety() {
            return y;
        }

        public void sety(String y) {
            this.y = y;
        }

        public String getdem() {
                return dem;
        }

        public void setdem(String dem) {
                this.dem = dem;
        }
    }

    // JTable 表模式类 （JTable对象 初始化的时候通过 这个Table获取表格的行数、列数、列标题、以及每个单元格存放的数据 ）
    public class Table extends AbstractTableModel {
        List<Dem> dem = new ArrayList<Dem>();

        public Table(List s) {
            this.dem = s;

        }
        public List<Dem> getStu() {
            return dem;
        }

        public void setStu(List<Dem> stu) {
            this.dem = stu;
        }

        @Override
        // 获取行数
        public int getRowCount() {
            return dem.size();
        }

        @Override
        // 获取列数
        public int getColumnCount() {
            // TODO Auto-generated method stub
            return 4;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        // 获取列名字
        public String getColumnName(int col) {
            String res = "";
            switch (col) {
                case 0:
                    res = "ID";
                    break;
                case 1:
                    res = "x";
                    break;
                case 2:
                    res = "y";
                    break;
                case 3:
                    res = "dem";
                    break;
                default:
                    break;
            }
            return res;
        }

        @Override
        // 获取具体值
        public Object getValueAt(int rowIndex, int columnIndex) {
            // TODO Auto-generated method stub
            Object res = "";
            Dem temp = dem.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = temp.getId();
                    break;
                case 1:
                    res = temp.getx();
                    break;
                case 2:
                    res = temp.gety();
                    break;
                case 3:
                    res = temp.getdem();
                    break;
                default:
                    break;
            }
            return res;
        }

    }

    // JDBCDAO类 配置连接数据的信息，链接释放操作和基本增删改查操作
    public class JDBCDaoImpl {
        String driver = "com.mysql.jdbc.Driver";
        //此处输入自己的数据库名称、用户、密码
        String url = "jdbc:mysql://localhost:3306/smart_city";
        String user = "root";
        String passwd = "admin";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // 数据库连接开始
        public Connection getConnection() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, passwd);
                stmt = conn.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }

        // 数据库连接释放
        public void closeConnection() {
            if (rs != null) {
                try {
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (rs == null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 查找操作
        public ResultSet search() {
            getConnection();
            try {
                String sql = "SELECT * FROM dem";
                rs = stmt.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rs;
        }

        // 添加操作
        public void insert(Dem dem) {
            // TODO Auto-generated method stub
            getConnection();
            try {
                String sql = "INSERT INTO dem(id,x,y,dem)" + "VALUES('" + dem.getId() + "','" + dem.getx() + "','"
                        + dem.gety() + "','" +dem.getdem()+ "')";
                int count = stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 删除操作
        public void delete(Dem dem) {
            // TODO Auto-generated method stub
            getConnection();
            try {
                String sql = "DELETE FROM dem WHERE id = '" + dem.getId() + "'";
                int count = stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 更新操作
        public void update(Dem dem) {
            // TODO Auto-generated method stub
            getConnection();
            try {
                String sql = "UPDATE dem SET x='" + dem.getx() + "',y= '" + dem.gety() + "',dem= '" + dem.getdem()+"'WHERE id = '"
                        + dem.getId() ;
                int count = stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) throws BadLocationException, SQLException {
        new DBtable().setVisible(true);
    }
}

