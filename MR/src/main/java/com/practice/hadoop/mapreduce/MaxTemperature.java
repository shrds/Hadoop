package com.practice.hadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Created by khard on 3/2/17.
 */
public class MaxTemperature {
    public static class Map extends Mapper<LongWritable, Text, IntWritable, IntWritable>{
        IntWritable intWritableKey = new IntWritable();
        IntWritable intWritableValue = new IntWritable();
        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String input = value.toString();
            StringTokenizer stringTokenizer = new StringTokenizer(input, " ");
            while(stringTokenizer.hasMoreTokens()){
                intWritableKey.set(Integer.valueOf(stringTokenizer.nextToken()));
                intWritableValue.set(Integer.valueOf(stringTokenizer.nextToken()));
                context.write(intWritableKey, intWritableValue);
            }
        }
    }

    public static class Reduce extends Reducer<IntWritable, IntWritable, Text, IntWritable> {

        @Override
        public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int maxTemp = 0;
            int temp;
            for(IntWritable value : values){
                temp = value.get();
                if(temp > maxTemp){
                    maxTemp = temp;
                }
            }
            context.write(new Text(key.toString()),new IntWritable(maxTemp));
        }

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "MaxTemperature");

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setJarByClass(MaxTemperature.class);


        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        Path outputPath = new Path(args[1]);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        outputPath.getFileSystem(conf).delete(outputPath);

        //exiting the job only if the flag value becomes false
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
