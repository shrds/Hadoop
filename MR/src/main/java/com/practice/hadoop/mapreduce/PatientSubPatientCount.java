package com.practice.hadoop.mapreduce;

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

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by khard on 2/28/17.
 */
public class PatientSubPatientCount {
    public static class Map extends Mapper<LongWritable, Text, Text, Text>{
        Text outkey = new Text();
        Text outvalue = new Text();
        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String input = value.toString();
            StringTokenizer tokenizer =
                    new StringTokenizer(input," " );
            while(tokenizer.hasMoreTokens()){
                outkey.set(tokenizer.nextToken());
                outvalue.set(tokenizer.nextToken());
                context.write(outkey,outvalue);
            }
        }

    }

    public static class Reduce extends Reducer<Text, Text, Text, IntWritable> {
        private static int finalOutValue = 0;
        @Override
        public void reduce(Text interKey, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for(Object obj : values){
                finalOutValue ++;
            }
            context.write(interKey,new IntWritable(finalOutValue));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();

        //job name
        Job job = new Job(conf, "PatSubPatCount");
        job.setJarByClass(PatientSubPatientCount.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(Text.class);

        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(IntWritable.class);

        job.setOutputFormatClass(TextOutputFormat.class);

        job.setInputFormatClass(TextInputFormat.class);

        Path outputPath = new Path(args[1]);


        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        outputPath.getFileSystem(conf).delete(outputPath);

        //exiting the job only if the flag value becomes false
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
