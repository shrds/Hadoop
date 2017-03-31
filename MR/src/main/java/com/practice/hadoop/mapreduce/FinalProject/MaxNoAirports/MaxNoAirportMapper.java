package com.practice.hadoop.mapreduce.FinalProject.MaxNoAirports;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Created by khard on 3/16/17.
 */
public class MaxNoAirportMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable mapKey, Text mapValue ,Context context) throws IOException, InterruptedException {
        String line = mapValue.toString();
        String[] countryData = line.split(",");
        String city = countryData[2];
        String country = countryData[3];
        context.write(new Text(country), new Text(city));
    }
}
