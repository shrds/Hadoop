patient = LOAD '/patient.txt' using PigStorage(' ') as (patientId:int, subPatientId:chararray);
grouped = GROUP patient BY patientId;
total = FOREACH grouped GENERATE group as patKey, COUNT(patient); 
dump total;
