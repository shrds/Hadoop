register '/usr/lib/pig-0.12.0/contrib/piggybank/java/piggybank.jar'
pigdata = load '/xml2.xml' USING org.apache.pig.piggybank.storage.XMLLoader('Property') as (doc:chararray);

values = foreach pigdata GENERATE FLATTEN(REGEX_EXTRACT_ALL(doc,'<Property>\\s*<fname>(.*)</fname>\\s*<lname>(.*)</lname>\\s*<landmark>(.*)</landmark>\\s*<city>(.*)</city>\\s*<state>(.*)</state>\\s*<contact>(.*)</contact>\\s*<email>(.*)</email>\\s*<PAN_Card>(.*)</PAN_Card>\\s*<URL>(.*)</URL>\\s*</Property>')) AS (fname:chararray, lname:chararray, landmark:chararray, city:chararray, state:chararray, contact:int, email:chararray, PAN_Card:long, URL:chararray);

dump values;
