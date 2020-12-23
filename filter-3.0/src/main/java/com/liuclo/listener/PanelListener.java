package com.liuclo.listener;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author liuclo
 * @Desciption //监听器 GUI 关闭窗口
 * @Date  2020/12/22
 **/
public class PanelListener {
    public static void main(String[] args) {
        Frame frame = new Frame();

        frame.setLayout(null);      //设置窗体的布局
        frame.setBounds(200, 200, 500, 500);
        frame.setBackground(Color.LIGHT_GRAY);

        Panel panel = new Panel();

        panel.setBounds(100, 100, 300, 300);
        panel.setBackground(new Color(255, 255, 0));

        frame.add(panel);
        frame.setVisible(true);

        // 监听 关闭
        frame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
                System.out.println("打开");
            }

            public void windowClosing(WindowEvent e) {
                System.out.println("关闭ing");
                System.exit(0);
            }

            public void windowClosed(WindowEvent e) {
                System.out.println("关闭");
            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowActivated(WindowEvent e) {
                System.out.println("活跃");
            }

            public void windowDeactivated(WindowEvent e) {
                System.out.println("最小化");
            }
        });
    }
}
