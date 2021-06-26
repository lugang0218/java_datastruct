package com.hubu;

import com.hubu.list.ArrayList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.LinkedList;


/**
 * 这是一个修改数据的面板
 */

public class ButtonPanel extends JPanel {



    private LinkedList<Integer> list=null;



    private ShowPanel showPanel;
    public void setShowPanel(ShowPanel showPanel){
        this.showPanel=showPanel;
    }
    private JTextField valueText;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    public  void initButton(){
        addButton=new JButton("添加");
        addButton.addActionListener(new MyActionListener());
        deleteButton=new JButton("删除");
        deleteButton.addActionListener(new MyActionListener());
        updateButton=new JButton("修改");
        updateButton.addActionListener(new MyActionListener());
        searchButton=new JButton("查询");
        searchButton.addActionListener(new MyActionListener());
    }
    public void initButtonPanel(){
        Box buttonBox=Box.createHorizontalBox();
        Box box=Box.createVerticalBox();
        valueText=new JTextField(20);
        buttonBox.add(addButton);
        buttonBox.add(deleteButton);
        buttonBox.add(searchButton);
        buttonBox.add(updateButton);
        box.add(valueText);
        box.add(buttonBox);
        this.add(box);
    }
    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            String value=valueText.getText();
            if("添加".equals(actionCommand)){
                System.out.println("添加发生");
                if(list==null){
                    list=new LinkedList<Integer>();
                }
                list.add(Integer.parseInt(value));
                showPanel.setList(list);
                showPanel.repaint();
            }
        }
    }
}
