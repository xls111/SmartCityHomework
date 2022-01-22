package windows;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * 数据表选择窗口
 * 目前只实现了选择dem表后的操作
 */
public class Select {
    public Select(){
        // 创建 JFrame 实例
        JFrame frame = new JFrame("数据表选择");
        // Setting the width and height of frame
        frame.setBounds(600, 200, 350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);

        panel.setLayout(null);

        JLabel textLabel1 = new JLabel("下列为Smart_city数据库中所有数据表条目，");
        textLabel1.setBounds(10,10,300,15);
        panel.add(textLabel1);

        JLabel textLabel2 = new JLabel("请您点击一个数据表进行操作:");
        textLabel2.setBounds(10,30,300,15);
        panel.add(textLabel2);

        JButton Button1 = new JButton("dem表");
        Button1.setBounds(10, 55, 80, 25);
        panel.add(Button1);

        JButton Button2= new JButton("站点表");
        Button2.setBounds(100, 55, 80, 25);
        panel.add(Button2);

        JButton Button3= new JButton("站点雨量表");
        Button3.setBounds(190, 55, 80, 25);
        panel.add(Button3);

        JButton Button4 = new JButton("流向表");
        Button4.setBounds(10, 85, 80, 25);
        panel.add(Button4);

        JButton Button5= new JButton("累计流量表表");
        Button5.setBounds(100, 85, 120, 25);
        panel.add(Button5);

        JButton Button6= new JButton("坡度表");
        Button6.setBounds(230, 85, 80, 25);
        panel.add(Button6);

        JButton Button7= new JButton("坡向表");
        Button7.setBounds(10, 115, 80, 25);
        panel.add(Button7);

        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new DBtable().setVisible(true);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }
    public static void main(String[] args){
        new Select();
    }
}