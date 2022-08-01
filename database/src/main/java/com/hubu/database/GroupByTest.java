package com.hubu.database;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
class TableBuilder{
    public static List<Record> buildTableList(){
        return Arrays.asList(new Record(1l,"lugang","贵州毕节","学生",1000),
                new Record(1l,"lugang","贵州毕节","学生",1000),
                new Record(2l,"lugang","贵州毕节","教师",1000),
                new Record(3l,"lugang","贵州毕节","员工",1000),
                new Record(4l,"lugang","贵州毕节","员工",1000),
                new Record(5l,"lugang","贵州毕节","学生",1000),
                new Record(6l,"lugang","贵州毕节","学生",1000),
                new Record(7l,"lugang","贵州毕节","工人",1000),
                new Record(8l,"lugang","贵州毕节","工人",1000),
                new Record(9l,"lugang","贵州毕节","工人",1000),
                new Record(10l,"lugang","贵州毕节","学生",1000),
                new Record(11l,"lugang","贵州毕节","学生",1000),
                new Record(12l,"lugang","贵州毕节","学生",1000),
                new Record(13l,"lugang","贵州毕节","教师",1000),
                new Record(14l,"lugang","贵州毕节","教师",1000),
                new Record(15l,"lugang","贵州毕节","员工",1000),
                new Record(16l,"lugang","贵州毕节","学生",1000),
                new Record(17l,"lugang","贵州毕节","教授",1000));
    }
    public static Table buildTable(String tableName){
        return new Table(tableName,buildTableList());
    }
}
public class GroupByTest {
    public static void main(String[] args) {
    }
}
