package com.practice.hadoop.mapreduce.FinalProject.ActiveAirlinesCountries;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Created by khard on 3/16/17.
 */
public class ActiveAirlinesCountriesMapper extends Mapper<LongWritable, Text, Text, Text>{
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String country, airlineName, active = null;
        String airlineData = value.toString();
        String[] airlineDataDetails = airlineData.split(",");
        airlineName = airlineDataDetails[1];
        country = airlineDataDetails[6];
        active = airlineDataDetails[7];
        if(country.equalsIgnoreCase("United States") && active.equalsIgnoreCase("Y")){
            context.write(new Text("Active Airline Name"), new Text(airlineName));
        }
    }
}
