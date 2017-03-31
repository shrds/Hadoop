register '/usr/lib/pig-0.12.0/contrib/piggybank/java/piggybank.jar'
pigdata = load '/xml1.xml' using org.apache.pig.piggybank.storage.XMLLoader('name') as (doc:chararray);

values = foreach pigdata GENERATE FLATTEN(REGEX_EXTRACT_ALL(doc,'<name>(.*)</name>')) AS (name:chararray);

dump values;
