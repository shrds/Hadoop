students = LOAD '/student' as (name:chararray, rollNumber:int);
results = LOAD '/results' as (rollNumber:int, result:chararray);
studentResult = JOIN students by rollNumber, results by rollNumber;
passedStudent = FILTER studentResult BY result matches 'pass';
finalResult = foreach passedStudent generate name;
dump finalResult;
