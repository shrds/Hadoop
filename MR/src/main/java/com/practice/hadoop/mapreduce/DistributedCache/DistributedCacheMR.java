package com.practice.hadoop.mapreduce.DistributedCache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;

public class DistributedCacheMR {
	
	
	public static class MyMapper extends Mapper<LongWritable,Text, Text, Text> {
        
		
		private Map<String, String> abbrMap = new HashMap<String, String>();
				private Text outputKey = new Text();
				private Text outputValue = new Text();

		protected void setup(Context context) throws java.io.IOException, InterruptedException {
			if (context.getCacheFiles() != null && context.getCacheFiles().length > 0) {
				File abcFile = new File("abc.dat");
				BufferedReader reader = new BufferedReader(new FileReader(abcFile.getName()));
				String line = reader.readLine();
				while (line != null) {
					String[] tokens = line.split("\t");
					String ab = tokens[0];
					String state = tokens[1];
					abbrMap.put(ab, state);
					line = reader.readLine();
				}
			}
			if (abbrMap.isEmpty()) {
				throw new IOException("Unable to load Abbreviation data.");
			}
		}

		
        protected void map(LongWritable key, Text value, Context context)
            throws java.io.IOException, InterruptedException {
        	
        	
        	String row = value.toString();
        	String[] tokens = row.split("\t");
        	String inab = tokens[0];
        	String state = abbrMap.get(inab);
        	outputKey.set(state);
        	outputValue.set(row);
      	  	context.write(outputKey,outputValue);
        }  
}
	
	
  public static void main(String[] args) 
                  throws IOException, ClassNotFoundException, InterruptedException {
    
    Job job = new Job();
    job.setJarByClass(DistributedCacheMR.class);
    job.setJobName("DistributedCacheExample");
    job.setNumReduceTasks(0);
    
    try{
		job.addCacheFile(new URI("/abc.dat"));
    }catch(Exception e){
    	System.out.println(e);
    }
    
    job.setMapperClass(MyMapper.class);
    
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.waitForCompletion(true);
    
    
  }
}