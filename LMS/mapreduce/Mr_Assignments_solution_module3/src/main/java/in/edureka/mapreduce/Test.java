package in.edureka.mapreduce;

import java.util.*;

/**
 * Created by edureka on 2/28/17.
 */
public class Test {
    public static void main(String[] args) {
        Set<Map<Integer, Float>> set= new HashSet<Map<Integer, Float>>();

        String input = "1 1.232\n" +
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
        }
    }
}
