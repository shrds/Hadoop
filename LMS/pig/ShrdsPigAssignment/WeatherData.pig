newWeather = LOAD '/CommaSepWeatherInfo/part-m-00000' using PigStorage(',') as (date:chararray, minTemp:double, maxTemp:double);
dump newWeather;
hotDays = filter newWeather by maxTemp > 25;
dump hotDays;
coldDays = filter newWeather by minTemp < 0;
dump coldDays;

grouped = group newWeather all;
maximumTemp = FOREACH grouped GENERATE MAX(newWeather.maxTemp) as max;
hottest = filter newWeather by maxTemp == maximumTemp.max;
dump = hottest;

grouped2 = group newWeather all;
minimumTemp = FOREACH grouped2 GENERATE MIN(newWeather.minTemp) as minimum;
coldest = filter newWeather by minTemp == minimumTemp.minimum;
dump coldest;
