package com.practice.hadoop.mapreduce.FinalProject;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Created by khard on 3/15/17.
 */
public class AirportsMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable mapKey, Text mapValue, Context context) throws IOException, InterruptedException {
        String line = mapValue.toString();
        String[] strings = line.split(",");
        String airportID = strings[0];
        String countryName = strings[3];
        context.write(new Text(countryName), new Text(airportID));

    }
}