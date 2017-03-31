package com.practice.hadoop.pig;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.util.WrappedIOException;

/**
 * Created by khard on 3/7/17.
 */
public class ValidValue extends EvalFunc<String> {
    @Override
    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;
        try {
            String str = (String) input.get(0);

            if (str.equals("-9999.0")) {
                return "0";
            } else {
                return str;
            }
        } catch (Exception e) {
            throw WrappedIOException.wrap(
                    "Caught exception processing input row ", e);
        }
    }
}
