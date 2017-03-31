package com.practice.hadoop.mapreduce.FinalProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.practice.hadoop.mapreduce.FinalProject.AirlinesCodeShare.AirlinesCodeShareReducer;
import com.practice.hadoop.mapreduce.FinalProject.AirlinesZeroStop.AirlineZeroStopReducer;
import com.practice.hadoop.mapreduce.FinalProject.MaxNoAirports.MaxNoAirportReducer;

import junit.framework.TestCase;

/**
 * Created by khard on 3/16/17.
 */
public class AirlineMRUnit extends TestCase {
    @Test
    public void testReducerZeroStop_AllStops() throws IOException {

        ReduceDriver<Text, Text, Text, Text> reduceDriver  = new ReduceDriver<>();
        reduceDriver.setReducer(new AirlineZeroStopReducer());
        List<Text> texts = new ArrayList<>();
        texts.add(new Text("airlineName,ABC"));
        texts.add(new Text("stops," + 0));
        texts.add(new Text("stops," + 0));
        texts.add(new Text("stops," + 0));
        texts.add(new Text("stops," + 0));


        reduceDriver.withInput(new Text("410"), texts);
        reduceDriver.withOutput(new Text("410"), new Text("ABC"));
        reduceDriver.runTest();
        Assert.assertEquals(reduceDriver.getExpectedOutputs().size(), 1);
    }

    @Test
    public void testReducerZeroStop_StopsWithNoStops() throws IOException {

        ReduceDriver<Text, Text, Text, Text> reduceDriver  = new ReduceDriver<>();
        reduceDriver.setReducer(new AirlineZeroStopReducer());
        List<Text> texts = new ArrayList<>();
        texts.add(new Text("airlineName,ABC"));
        texts.add(new Text("stops," + 0));
        texts.add(new Text("stops," + 0));
        texts.add(new Text("stops," + 0));
        texts.add(new Text("stops," + 1));


        reduceDriver.withInput(new Text("410"), texts);
        reduceDriver.runTest();
        Assert.assertEquals(0, reduceDriver.getExpectedOutputs().size());
    }

    @Test
    public void testReducerCodeShares() throws IOException {

        ReduceDriver<Text, Text, Text, Text> reduceDriver  = new ReduceDriver<>();
        reduceDriver.setReducer(new AirlinesCodeShareReducer());
        List<Text> texts = new ArrayList<>();
        texts.add(new Text("airlineName,ABC"));
        texts.add(new Text("stops," + 0 + ",codeShare,Y"));
        texts.add(new Text("stops," + 0 + ",codeShare,N"));
        texts.add(new Text("stops," + 0 + ",codeShare,Y"));
        texts.add(new Text("stops," + 0 + ",codeShare,Y"));


        reduceDriver.withInput(new Text("410"), texts);
        reduceDriver.withOutput(new Text("410"), new Text("ABC"));
        reduceDriver.runTest();
        Assert.assertEquals(reduceDriver.getExpectedOutputs().size(), 1);
    }

    @Test
    public void testReducerCodeShares_noCodeShare() throws IOException {

        ReduceDriver<Text, Text, Text, Text> reduceDriver  = new ReduceDriver<>();
        reduceDriver.setReducer(new AirlinesCodeShareReducer());
        List<Text> texts = new ArrayList<>();
        texts.add(new Text("airlineName,ABC"));
        texts.add(new Text("stops," + 0 + ",codeShare, "));
        texts.add(new Text("stops," + 0 + ",codeShare, "));
        texts.add(new Text("stops," + 0 + ",codeShare,N"));
        texts.add(new Text("stops," + 0 + ",codeShare,N"));


        reduceDriver.withInput(new Text("410"), texts);
        reduceDriver.runTest();
        Assert.assertEquals(reduceDriver.getExpectedOutputs().size(), 0);
    }

    @Test
    public void testReducerMaxAirport() throws IOException {

        ReduceDriver<Text, Text, Text, IntWritable> reduceDriver  = new ReduceDriver<>();
        reduceDriver.setReducer(new MaxNoAirportReducer());
        List<Text> texts1 = new ArrayList<>();
        texts1.add(new Text("a"));
        texts1.add(new Text("b"));
        texts1.add(new Text("c"));
        texts1.add(new Text("d"));
        texts1.add(new Text("e"));
        List<Text> texts2 = new ArrayList<>();
        texts2.add(new Text("1"));
        texts2.add(new Text("2"));
        texts2.add(new Text("3"));
        texts2.add(new Text("4"));
        texts2.add(new Text("5"));
        texts2.add(new Text("6"));
        texts2.add(new Text("7"));
        texts2.add(new Text("8"));
        texts2.add(new Text("9"));
        texts2.add(new Text("10"));

        reduceDriver.withInput(new Text("USA"), texts2);
        reduceDriver.withInput(new Text("India"), texts1);
        reduceDriver.withOutput(new Text("Country with Max airport : USA"), new IntWritable(10));
        reduceDriver.runTest();

        Assert.assertEquals(reduceDriver.getExpectedOutputs().size(), 1);
    }
}
