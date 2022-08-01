package com.hubu.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
public class WordCountReducer extends Reducer<Text, LongWritable,Text,LongWritable> {
    //<hadoop [1,1,1,1,1]>
    //<hello [1,1,1,1]>
    //<world,[1,1,1,1,1]>
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Reducer<Text,LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException{
        long sum = 0l;
        for(LongWritable item : values){
            sum += item.get();
        }
        context.write(key,new LongWritable(sum));
    }
}
