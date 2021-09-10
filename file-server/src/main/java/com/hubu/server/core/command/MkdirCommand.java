package com.hubu.server.core.command;

import java.util.List;

public class MkdirCommand implements AbstractCommand{
    private String type;

    private List<Param<String,String>> params;

    public MkdirCommand(String type,List<Param<String,String>> params){
        this.type=type;
        this.params=params;
    }
    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<Param<String, String>> getParams() {
        return params;
    }
}
