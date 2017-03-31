package com.practice.hadoop.mapreduce.MRUnit;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.*;
import org.junit.Test;


public class MapReduceJunitTest extends TestCase {

    public static class MapTest extends Mapper<LongWritable, Text, Text, IntWritable> {
        Text day = new Text();

        public void map(LongWritable key, Text value, Context ctx) throws IOException, InterruptedException {
            String[] line = value.toString().split(",");
            int val = Integer.parseInt(line[0]);
            day.set(line[1]);
            ctx.write(day, new IntWritable(val));

        }
    }

    MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;

    public void setUp() {
        new MapTest();
        mapDriver = MapDriver.newMapDriver(new MapTest());
    }

    @Test
    public void testMapper() {
        try {
            mapDriver.withInput(new LongWritable(), new Text("1,sunday,abhay,holiday"))
                    .withOutput(new Text("sunday"), new IntWritable(1))
                    .runTest();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}