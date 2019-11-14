package com.xuy.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Period;
import java.util.Random;

/**
 * 面板
 * 需要继承JPanel
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int leath;//蛇的长度
    int speed;
    int cc;//次数
    int[] snakeX=new int[600];//蛇的坐标x
    int[] snakeY=new int[500];//蛇的坐标y
    String fx;//R: 右， L:左， U：上 D：下
    boolean isStart=false;//游戏是否开始
    Timer timer=new Timer(200, this);//定时器

    //定义一个食物
    int foodX;
    int foodY;
    Random random=new Random();

    //判断死亡
    boolean isFail=false;

    //积分系统
    int score;

    /**
     * 构造器
     */
    public GamePanel(){
        init();

        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();//让时间动起来
    }

    /**
     * 初始化
     */
    public void init(){
        leath=3;
        snakeX[0]=100;snakeY[0]=100;//头部坐标
        snakeX[1]=75;snakeY[1]=100;//身体一
        snakeX[2]=50;snakeY[2]=100;//身体二
        fx="R";
        speed=200;
        cc=1;
        foodX=25+25*random.nextInt(34);
        foodY=75+25*random.nextInt(24);
        score=0;
    }


    //画板：画界面，画蛇
    //Graphics是一个画笔
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);//清屏
        this.setBackground(Color.white);//设置背景颜色
        Data.header.paintIcon(this, g,25,11); //绘制头部广告栏
        g.fillRect(25, 75, 850, 600); //绘制游戏的区域

        //画一条静态小蛇
       if(fx.equals("R")){
           Data.right.paintIcon(this, g, snakeX[0],snakeY[0]);
       }else if(fx.equals("L")){
           Data.left.paintIcon(this, g, snakeX[0],snakeY[0]);
       }else if(fx.equals("U")){
           Data.up.paintIcon(this, g, snakeX[0],snakeY[0]);
       }else if(fx.equals("D")){
           Data.down.paintIcon(this, g, snakeX[0],snakeY[0]);
       }

        //循环设置蛇的身体
        for(int i=1;i<leath;i++){
            //控制蛇身
            Data.body.paintIcon(this, g, snakeX[i],snakeY[i]);
        }

        //画积分
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度:"+leath, 750, 35);
        g.drawString("分数:"+score, 750, 55);

        //画食物
        Data.food.paintIcon(this, g, foodX, foodY);



        //游戏开始提示
        if(isStart==false){
            //画一个文字，String
            g.setColor(Color.white);//设置画笔颜色
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));//字体设置
            g.drawString("按下空格开始游戏",300,300);
        }

        //失败提醒
        if(isFail){
            //画一个文字，String
            g.setColor(Color.red);//设置画笔颜色
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));//字体设置
            g.drawString("游戏失败，按下空格重新开始！",200,300);
        }

    }

    //监听键盘的输入
    /**
     * 键盘按下：未释放
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //获取按下的键盘是什么
        int keyCode = e.getKeyCode();

        if(keyCode==KeyEvent.VK_SPACE){//空格
            if(isFail){//失败，重新再来一遍
                isFail=false;
                init();//重新初始化游戏
            }else {//暂停游戏
                isStart=!isStart;//取反
            }
            repaint();//界面刷新
        }

        if(e.getKeyCode()==KeyEvent.VK_LEFT&&!fx.equals("R")){//按左,如果本来是右边不反应
            fx="L";
        }else if(e.getKeyCode()==KeyEvent.VK_UP&&!fx.equals("D")){//按上,如果本来是下边不反应
           fx="U";
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT&&!fx.equals("L")){//按右,如果本来是左边不反应
            fx="R";
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN&&!fx.equals("U")){//按下,如果本来是上边不反应
            fx="D";
        }
    }

    //定时器，监听时间，帧：执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart&&isFail==false){
            //右移
            for(int i=leath-1;i>0;i--){//除了脑袋身体向前移动
                snakeX[i]=snakeX[i-1];
                snakeY[i]=snakeY[i-1];
            }

            //通过控制方向让头部移动
            if(fx.equals("R")){
                snakeX[0]=snakeX[0]+25;//头部移动
                //边界判断
                if(snakeX[0]>850){
//                    snakeX[0]=25;
                    isFail=true;
                }
            }else if(fx.equals("L")){
                snakeX[0]=snakeX[0]-25;//头部移动
                //边界判断
                if(snakeX[0]<25){
//                    snakeX[0]=850;
                    isFail=true;
                }
            }else if(fx.equals("U")){
                snakeY[0]=snakeY[0]-25;//头部移动
                //边界判断
                if(snakeY[0]<75){
//                    snakeY[0]=650;
                    isFail=true;
                }
            }else if(fx.equals("D")){
                snakeY[0]=snakeY[0]+25;//头部移动
                //边界判断
                if(snakeY[0]>650){
//                    snakeY[0]=75;
                    isFail=true;
                }
            }

            //如果小蛇的头部坐标和食物的坐标一样
            if(snakeX[0]==foodX&&snakeY[0]==foodY){
                leath++;
                score+=10;
                foodX=25+25*random.nextInt(34);
                foodY=75+25*random.nextInt(24);
            }

            //当分数越高的话定时器刷新越快
            if(score>(30*cc)&&speed>=50){
                cc++;
                speed-=10;
                timer.setDelay(speed);
            }
            repaint();//界面刷新
        }

        //结束判断
        for(int i=1;i<leath;i++){
            if(snakeX[0]==snakeX[i]&&snakeY[0]==snakeY[i]){
                isFail=true;
            }
        }


        timer.start();
    }







    /**
     * 键盘按下，弹起：敲击
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 释放摸个键
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }


}
