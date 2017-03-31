package com.practice.hadoop.pig;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
/**
 * Created by khard on 3/7/17.
 */


public class Sample_Eval extends EvalFunc<String> {

    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;
        String str = (String) input.get(0);
        return str.toUpperCase();
    }
}
