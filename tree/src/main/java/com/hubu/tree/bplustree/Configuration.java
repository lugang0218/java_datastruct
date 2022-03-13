package com.hubu.tree.bplustree;
public class Configuration {
    private String logFilePath;
    private Integer order;
    public void setLogFilePath(String  logFilePath){
        this.logFilePath = logFilePath;
    }
    public void setOrder(int order){
        this.order = order;
    }
    public Integer getOrder() {
        return order;
    }
    public String getLogFilePath() {
        return logFilePath;
    }
}
