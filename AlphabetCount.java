package in.edureka.mapreduce;

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
 * Created by khard on 2/27/17.
 */
public class AlphabetCount {


    public static class Map extends Mapper<LongWritable, Text, IntWritable, Text>{

        private static IntWritable outKey;

        private static Text outValue = new Text();
        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while(tokenizer.hasMoreTokens()){
                String word = tokenizer.nextToken();
                outValue.set(word);
                outKey = new IntWritable(word.length());
                context.write(outKey,outValue);
            }

        }
    }

    public static class Reduce extends Reducer<IntWritable, Text, IntWritable, IntWritable>{

        @Override
        public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            for(Text text : values){
                count++;
            }
            context.write(key,new IntWritable(count));
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //reads the default configuration of cluster from the configuration xml files

        Configuration conf = new Configuration();

        //job name
        Job job = new Job(conf, "Wordsize");

        //Assigning the driver class name
        job.setJarByClass(AlphabetCount.class);

        //Defining the mapper class name
        job.setMapperClass(AlphabetCount.Map.class);

        //Defining the reducer class name
        job.setReducerClass(AlphabetCount.Reduce.class);

        //Defining the output key class for the mapper
        job.setMapOutputKeyClass(IntWritable.class);

        //Defining the output value class for the mapper
        job.setMapOutputValueClass(Text.class);

        //Defining the output key class for the final output i.e. from reducer
        job.setOutputKeyClass(IntWritable.class);

        //Defining the output value class for the final output i.e. from reduce
        job.setOutputValueClass(IntWritable.class);

        //Defining input Format class which is responsible to parse the dataset into a key value pair
        job.setInputFormatClass(TextInputFormat.class);

        //Defining output Format class which is responsible to parse the final key-value output from MR framework to a text file into the hard disk
        job.setOutputFormatClass(TextOutputFormat.class);

        //setting the second argument as a path in a path variable
        Path outputPath = new Path(args[1]);

        //Configuring the input/output path from the filesystem into the job
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //deleting the output path automatically from hdfs so that we don't have delete it explicitly
        outputPath.getFileSystem(conf).delete(outputPath);

        //exiting the job only if the flag value becomes false
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
