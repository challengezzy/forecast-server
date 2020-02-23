@echo off
title sanquan_app
set JAVA_HOME=D:\java\jdk1.8.0_25

set PATH=.;%JAVA_HOME%\bin;
set CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;
set lib=.;%JAVA_HOME%\lib\tools.jar;


@echo on
@echo TomcatÕý³£Æô¶¯.......................
%JAVA_HOME%\bin\java -cp $lib -Xms224m -Xmx524m -jar forecast-app-1.0-SNAPSHOT.jar
goto END

goto END


:END

pause