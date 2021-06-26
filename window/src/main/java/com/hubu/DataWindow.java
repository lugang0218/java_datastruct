package com.hubu;
import com.hubu.list.util.Printer;
import javax.swing.*;
public class DataWindow extends JDialog {
    private ButtonPanel buttonPanel=null;
    private ShowPanel showPanel=null;
    private String title;
    public DataWindow(JFrame jframe,boolean model,String title){
        super(jframe, title, model);
        buttonPanel=new ButtonPanel();
        buttonPanel.initButton();
        buttonPanel.initButtonPanel();
        initShowPanel();
        initDataWindow();
    }
    private void initDataWindow(){
        this.title=title;
        this.setSize(800,600);
        this.setLocation(600,150);
        this.setResizable(true);
        this.setTitle(this.title);
        Box windowBox=Box.createVerticalBox();
        windowBox.add(buttonPanel);
        windowBox.add(showPanel);
        this.add(windowBox);
    }
    private void initShowPanel(){
        showPanel =new ShowPanel();
        buttonPanel.setShowPanel(showPanel);
    }
    static class MyPrinter implements Printer<Integer>{

        @Override
        public void print(Integer value) {
            System.out.println(value);
        }
    }
}
