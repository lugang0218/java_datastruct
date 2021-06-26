package com.hubu;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
/**
 * 这是一个显示数据的面板
 */
public class ShowPanel extends JPanel {
    public static int X;//用于记录矩形的横坐标
    public static int Y;//用于记录举行的纵坐标
    private LinkedList<Integer> list=null;
    public void setList(LinkedList<Integer> list) {
        this.list = list;
    }
    //系统调用函数
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,0,400,0);
        for(int i=1;i<=10;i++){
            g.drawLine(i*40,0,40*i,80);
        }
        g.drawLine(0,80,400,80);
    }
    public void drawNumber(Graphics g){
        super.paint(g);
        g.drawLine(0,0,400,0);
        for(int i=1;i<=10;i++){
            g.drawLine(i*40,0,40*i,80);
        }
        g.drawLine(0,80,400,80);
    }
}
