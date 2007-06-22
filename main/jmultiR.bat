ECHO OFF
SET CP=.
SET CP=%CP%;lib/JRI.jar
SET CP=%CP%;lib/concurrent.jar
SET CP=%CP%;lib/f2jutil.jar
SET CP=%CP%;lib/filedrop.jar
SET CP=%CP%;lib/gnujaxp.jar
SET CP=%CP%;lib/iplots.jar
SET CP=%CP%;lib/itex.jar
SET CP=%CP%;lib/jama.jar
SET CP=%CP%;lib/javaGD.jar
SET CP=%CP%;lib/javolution.jar
SET CP=%CP%;lib/jcommon.jar
SET CP=%CP%;lib/jfreechart.jar
SET CP=%CP%;lib/jdnc.jar
SET CP=%CP%;lib/jhall.jar
SET CP=%CP%;lib/jlfgr.jar
SET CP=%CP%;lib/jscience.jar
SET CP=%CP%;lib/jstatcom.jar
SET CP=%CP%;lib/junit.jar
SET CP=%CP%;lib/jxl.jar
SET CP=%CP%;lib/log4j.jar

SET CP=%CP%;jmulti.jar


SET R_HOME=jrstat/R/R-2.5.0
SET PATH=%PATH%;jrstat/R/R-2.5.0/bin

REM Uncomment the following line and add the application jar (or the folder with the classes) to the classpath.

SET CP=bin;%CP%     
ECHO ON
java -Djava.library.path=jrstat/R/R-2.5.0/library/rJava/jri -cp %CP% de.jmulti.JMultiR
