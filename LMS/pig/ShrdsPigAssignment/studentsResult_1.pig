students = LOAD '/student' using PigStorage('/t') as (name:Chararray, rollNumber:Integer);
results = LOAD '/results' using PigStorage('/t') as (rollNumber:Integer, result:Chararray);
studentResult = JOIN students by rollNumber, results by rollNumber;
passedStudent = FILTER studentResult BY result matches 'pass';
dump passedStudent;
