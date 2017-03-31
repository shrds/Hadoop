
/*******************************************************************************
   Copyright (c) 2014 edureka! [www.edureka.in]
 ******************************************************************************/


package com.practice.hadoop.mapreduce;

import java.io.IOException;
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

/*
 * All org.apache.hadoop packages can be imported using the jar present in lib
 * directory of this java project.
 */

 

/*
 * The Patent program finds the number of sub-patents associated with each id in the provided
 * input file. We write a map reduce code to achieve this, where mapper makes key value pair from 
 * the input file and reducer does aggregation on this key value pair.
 */

public class Patent { 
	
	/*
	 * Map class is static and extends MapReduceBase and implements Mapper 
	 * interface having four hadoop generics type LongWritable, Text, Text, Text.
	 */
 
	public static class Map extends
	Mapper<LongWritable, Text, Text, Text> {

    	Text k= new Text();
    	Text v= new Text(); 

  
    	
        @Override 
        public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {

        	String line = value.toString();
        	StringTokenizer tokenizer = new StringTokenizer(line," ");

            while (tokenizer.hasMoreTokens()) { 

            	String jiten= tokenizer.nextToken();
            	k.set(jiten);
            	String jiten1= tokenizer.nextToken();
            	v.set(jiten1);

            	//Sending to output collector which inturn passes the same to reducer
                context.write(k,v); 
            } 
        } 
    } 

	public static class Reduce extends Reducer<Text, Text, Text, IntWritable> {
    	
        @Override 
        public void reduce(Text key, Iterable<Text> values, Context context)
		throws IOException, InterruptedException {
            int sum = 0; 

            for(Text x : values)
            {
            	sum++;
            }

            context.write(key, new IntWritable(sum)); 
        } 
 
    } 

 
    public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		Job job = new Job(conf, "patent");
		
		job.setJarByClass(Patent.class);
		
		job.setMapperClass(Map.class);

		job.setReducerClass(Reduce.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		
		job.setOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		
		job.setOutputFormatClass(TextOutputFormat.class);
		
        Path outputPath = new Path(args[1]);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//deleting the output path automatically from hdfs so that we don't have delete it explicitly
		
		outputPath.getFileSystem(conf).delete(outputPath);
		
		//exiting the job only if the flag value becomes false
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
 
    } 
}

