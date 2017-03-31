package com.practice.hadoop.mapreduce;

import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.*;

/**
 * Created by edureka on 2/28/17.
 */
public class Test {
    public static void main(String[] args) {
        Set<Map<Integer, Float>> set= new HashSet<Map<Integer, Float>>();

       /* String input = "1 1.232\n" +
                "1 1.45\n" +
                "1 1.153\n" +
                "1 1.100\n" +
                "1 1.77\n" +
                "1 1.170\n" +
                "1 1.111\n" +
                "1 1.11\n" +
                "1 1.220\n" +
                "1 1.3\n" +
                "1 1.169\n" +
                "1 1.189\n" +
                "1 1.19\n" +
                "1 1.236\n" +
                "1 1.67";

        StringTokenizer tokenizer =
                new StringTokenizer(input, " ");
        while(tokenizer.hasMoreTokens()){
            System.out.print(tokenizer.nextToken() + ",");
        }*/


        UUID uuid = UUID.fromString("599A12E5B7964556BE69408F28A93C3F");

        System.out.println(uuid);


        String text = "Hello. This is a text \n that will be split "
                + "into tokens. 1 + 1 = 2";

        Reader reader = new StringReader(text);
        StreamTokenizer st = new StreamTokenizer(reader);

        st.wordChars('a','z');
        st.wordChars('A','Z');



    }
}
