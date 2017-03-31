package com.practice.hadoop.mapreduce.FinalProject.IndianAirlines;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Created by khard on 3/8/17.
 */
public class IndiaAirportMap extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable mapKey, Text mapValue, Context context) throws IOException, InterruptedException {
        String line = mapValue.toString();
        String[] strings = line.split(",");
        String airportID = strings[0];
        String airportName = strings[1];
        String countryName = strings[3];

        if(countryName.equalsIgnoreCase("India")){
            context.write(new Text(countryName), new Text(airportID + "\t" + airportName));
        }
    }
}
