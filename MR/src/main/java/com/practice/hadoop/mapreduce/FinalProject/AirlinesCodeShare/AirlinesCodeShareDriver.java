package com.practice.hadoop.mapreduce.FinalProject.AirlinesCodeShare;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.practice.hadoop.mapreduce.FinalProject.AirlineRouteMapper;
import com.practice.hadoop.mapreduce.FinalProject.AirlinesAirlineMapper;
import com.practice.hadoop.mapreduce.FinalProject.AirlinesZeroStop.AirlineZeroStopReducer;

/**
 * Created by khard on 3/16/17.
 */
public class AirlinesCodeShareDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "AirlinesWithCodeShare");

        job.setJarByClass(AirlinesCodeShareDriver.class);
        job.setReducerClass(AirlinesCodeShareReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, AirlinesAirlineMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]),TextInputFormat.class, AirlineRouteMapper.class);
        Path outputPath = new Path(args[2]);

        FileOutputFormat.setOutputPath(job, outputPath);
        outputPath.getFileSystem(conf).delete(outputPath);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
