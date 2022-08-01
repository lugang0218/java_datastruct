package com.hubu.database;
import java.util.*;
enum Type {
    IntType,LongType,StringType
}
interface Field {
    Type getType();
}
class IntField implements Field{
    private int value;
    public IntField(int value){
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.IntType;
    }
}
class StringField implements Field {

    private int maxLength;
    private int length;
    private String value;
    public StringField(String value){
        if(value == null) throw new RuntimeException("value can not be null");
        if(value.length() <= maxLength){
            this.value = value;
        }else{
            this.value = value.substring(0,maxLength);
        }
        this.length = this.value.length();
    }
    @Override
    public Type getType() {
        return Type.StringType;
    }
}
class LongField implements Field{
    private Long value;
    public LongField(long value){
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.LongType;
    }
}
class Record2{
    private List<Field> fields;
    public Field getFieldByIndex(int index){
        return fields.get(index);
    }
}
class Table2{
    private List<String> headerNameList;
    private List<Record2> recordList;
    private String tableName;
    public Table2(String tableName,List<Record2> recordList,List<String> headerNameList){
        this.tableName = tableName;
        this.headerNameList = headerNameList;
        this.recordList =recordList;
    }
    public int getFieldByHeaderName(String headerName){
        int index = headerName.indexOf(headerName);
        return index;
    }

    public List<Record2> getRecordList(){
        return recordList;
    }

}
public class Table {
    List<String> headerNameList;

    public Table(List<String> headerNameList,String tableName,List<Record> recordList){
        this.headerNameList = headerNameList;
        this.recordList = recordList;
        this.tableName = tableName;
    }
    private String tableName;
    private List<Record> recordList = new ArrayList<>();
    public Table(String tableName,List<Record> recordList){
        this.tableName = tableName;
        this.recordList = recordList;
    }
    public void addRecord(Record record){
        recordList.add(record);
    }
    public Iterator<Record> iterator (){
        return recordList.iterator();
    }
    public void removeRecord(Record record){
        recordList.remove(record);
    }
    public boolean contains(Record record){
        return recordList.contains(record);
    }

    public List<Record> getRecordList(){
        return recordList;
    }


}
