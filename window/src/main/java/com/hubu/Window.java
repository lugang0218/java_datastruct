package com.hubu;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {



    private boolean show=false;

    //显示数据结构的面板



    private JPanel dataStructPanel;



    //主面板
    private JPanel mainPanel=null;


    private JList<String> dataStructList;
    private void initDataStructList(){
        if(dataStructList==null){
            dataStructList=new JList<String>();
        }
        dataStructList.setListData(
                new String[]{"ArrayList","LinkedList","SingleList","Stack","Queue","BinarySearchTree","AvlTree"}


                );
    }
    public Window(){
        showWindow();
    }

    private void init(){
        //初始化数据结构
        initDataStructList();


        //初始化数据结构的面板
        initDataStructPanel();


        //初始化主面板

        initMainPanel();



        //初始化窗体
        initWindow();



    }


    private void showWindow(){
        if(!show){
            //开始
            init();
            this.setVisible(true);
            show=true;
        }
    }
    private void initWindow(){
        this.setSize(1000,600);
        this.setTitle("数据结构与算法");
        this.setLocation(600,150);
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(dataStructPanel,BorderLayout.WEST);
        this.add(mainPanel,BorderLayout.CENTER);
    }
    private void initDataStructPanel(){
        dataStructPanel = new JPanel();

        dataStructPanel.setSize(200,500);

        dataStructList.addListSelectionListener(new MyListener());
        //将数据结构放入面板
        dataStructPanel.add(dataStructList);
    }

    private void initMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setSize(600,800);
    }
    public static void main(String[] args){
        Window window=new Window();
    }

    class MyListener implements  ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Object source = e.getSource();
            JList<String> list=(JList<String>) source;
            //点击事件
            if(dataStructList.getValueIsAdjusting()){
                String selectValue=list.getSelectedValue();
                DataWindow dataWindow=new DataWindow(Window.this,true,selectValue);
                dataWindow.setVisible(true);
            }
        }
    }
}
