package com.practice.hadoop.mapreduce.FinalProject.AirlinesCodeShare;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Created by khard on 3/16/17.
 */
public class AirlinesCodeShareReducer extends Reducer<Text, Text, Text, Text> {
    private String airlineName, codeShare;
    private int count = 0;
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for(Text text : values){
            String[] parts = text.toString().split(",");
            if(parts.length > 2 && parts[2].equalsIgnoreCase("codeShare")){
                codeShare = parts[3];
                if(codeShare != null && codeShare.equalsIgnoreCase("Y")){
                    count++;
                }

            }else if(parts[0].equalsIgnoreCase("airlineName")){
                airlineName = parts[1];
            }
        }
        if(count > 1){
            context.write(new Text(key), new Text(airlineName));
        }

    }
}
