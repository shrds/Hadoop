package com.practice.hadoop.mapreduce.FinalProject;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Created by khard on 3/15/17.
 */
public class AirlinesAirlineMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable inKey, Text inValue, Context context) throws IOException, InterruptedException {

        String airlines = inValue.toString();
        String[] airlineDetails = airlines.split(",");
        String airlineId = airlineDetails[0];
        String airlineName = airlineDetails[1];

        context.write(new Text(airlineId), new Text("airlineName," + airlineName));
    }
}
