package com.hubu.database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class GroupBy{
    private Map<Field, List<Record2>> resultMap = new HashMap<>();
    public void groupBy(Table2 table,String headerValue){
        int index  = table.getFieldByHeaderName(headerValue);
        if(index == -1) return ;
        List<Record2> recordList = table.getRecordList();
        for(Record2 record:recordList){
            Field field =  record.getFieldByIndex(index);
            if(resultMap.containsKey(field)) {
                List<Record2> itemRecords = resultMap.get(field);
                itemRecords.add(record);
            }else{
                List<Record2> records = new ArrayList<>();
                records.add(record);
                resultMap.put(field,recordList);
            }
        }
    }
    public Map<Field,List<Record2>> getResultMap(){
        return resultMap;
    }


}
