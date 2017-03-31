package com.practice.hadoop.mapreduce.FinalProject.AirlinesZeroStop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Created by khard on 3/16/17.
 */
public class AirlineZeroStopReducer extends Reducer<Text, Text, Text, Text> {

    public Map<String, String> airlineMap = new HashMap<>();
    private Integer stops;
    private String airlineName;
    private int count = 0;
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for(Text text : values){
            String[] parts = text.toString().split(",");
            if(parts[0].equalsIgnoreCase("stops")){
                stops = Integer.valueOf(parts[1]);
                if(stops.equals(1)){
                    count++;
                }
            }else if(parts[0].equalsIgnoreCase("airlineName")){
                airlineName = parts[1];
            }
        }
        if(count == 0){
            context.write(new Text(key), new Text(airlineName));
        }
    }
}
