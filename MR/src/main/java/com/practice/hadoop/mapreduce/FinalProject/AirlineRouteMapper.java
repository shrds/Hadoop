package com.practice.hadoop.mapreduce.FinalProject;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Created by khard on 3/15/17.
 */
public class AirlineRouteMapper extends Mapper<LongWritable, Text, Text, Text>{
    @Override
    public void map(LongWritable inKey, Text inValue, Context context) throws IOException, InterruptedException {
        Integer stops = null;
        String routes = inValue.toString();
        String[] routeDetails = routes.split(",");
        String airlineId = routeDetails[1];
        String codeShare = null;
        if(routeDetails[7] != null){
            stops = Integer.parseInt(routeDetails[7]);
        }
        if(StringUtils.isNotBlank(routeDetails[6])){
            codeShare = routeDetails[6];
        }
        context.write(new Text(airlineId), new Text("stops," + stops + ",codeShare," + codeShare));
    }
}
