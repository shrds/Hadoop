package com.practice.hadoop.mapreduce.FinalProject.MaxNoAirports;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Created by khard on 3/16/17.
 */
public class MaxNoAirportReducer extends Reducer<Text, Text, Text, IntWritable> {
    int maxCount = 0;
    String Country = null;

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for(Text text : values){
            count ++;
        }
        if(maxCount < count){
            Country = key.toString();
            maxCount = count;
        }

        //context.write(new Text("Country with Max airport : " + Country), new IntWritable(maxCount));
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text("Country with Max airport : " + Country),  new IntWritable(maxCount));
    }
}
