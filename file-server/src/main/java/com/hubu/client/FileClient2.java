package com.hubu.client;

import com.alibaba.fastjson.JSON;
import com.hubu.server.core.command.MkdirCommand;
import com.hubu.server.core.command.Param;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class FileClient2 {
    public static void main(String[] args) {
        Socket socket=new Socket();
        OutputStreamWriter writer=null;
        BufferedWriter bufferedWriter=null;
        OutputStream outputStream=null;
        try {
            socket.connect(new InetSocketAddress("localhost",8989));
            outputStream=socket.getOutputStream();
            writer=new OutputStreamWriter(outputStream);
            bufferedWriter=new BufferedWriter(writer);
            Param<String,String> param=new Param<>("-p","j1");
            Param<String,String> param2=new Param<>("-p","j2");
            Param<String,String> param3=new Param<>("-p","c3");

            List<Param<String, String>> params = Arrays.asList(param,param2,param3);
            MkdirCommand command=new MkdirCommand("mkdir",params);
            String line= JSON.toJSONString(command);
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
