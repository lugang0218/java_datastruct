package com.hubu.map;
import org.junit.Test;
import java.util.UUID;
public class SimpleHashMapTest {
    public static void main(String[] args) {
        SimpleHashMap<String,String> map=new SimpleHashMap<>();
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        map.put("hello world","world hello");
        String hello_world = map.get("hello world");
        map.remove("hello world");

    }
    /**
     * 测试右移 右移n位，就出一2的n次方
     */
    @Test
    public void test(){
        byte a=12;
        System.out.println(a>>1);
    }
}
