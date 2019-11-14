package com.xuy.snake;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        //1绘制一个静态窗口 JFrame  对应awt
        JFrame jFrame=new JFrame("xuy贪吃蛇");

        //2.设置界面大小
        jFrame.setBounds(10,10,900,720);
        jFrame.setResizable(false);//窗口大小不可变
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭方式，游戏可以关闭

        //3.面板 jpanel 可以加入jFrame
        jFrame.add(new GamePanel());

        jFrame.setVisible(true);//设置窗口显示，放在最后显示
    }
}
