package com.hubu.wordcount;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

/**
 *
 *
 *
 * in key 偏移量 value 一行数据
 * out key 一个单词 value 1
 *
 * 调用 TextInputFormat读取一行调用一次
 */
public class WordCountMapper extends  Mapper<LongWritable, Text, Text, LongWritable>{
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context) throws IOException, InterruptedException {
        String lineValue = value.toString();
        String[] words = lineValue.split("\\s+");
        for(String word:words){
            context.write(new Text(word),new LongWritable(1l));
        }
    }
}
