This directory contains resources needed to 
use R (www.r-project.org) with jstatcom.

All native libraries that are needed to use the jrstat engine are 
contained in the rJava R-package (JRI). There are no jstatcom specific
dlls for this engine. You may use a separate R installation or you 
may ship R with your (GPL licensed) software. This may be convenient
for users if the necessary environment variables (R_HOME, PATH, 
java.library.path) are automatically 
set during the installation or, simpler, if you provide a start script
which uses the constant relative path entries.

To run the R communication, the following R modules must be installed

rJava
JGR
JavaGD
iplots

The following variables must be set, either permanently or
via a start script:

R_HOME - must point to home directory of R

java.library.path - must be set with the D argument and point to the directory with "jri", for example
	            "-Djava.library.path=./jrstat/R/R-2.4.1/library/rJava/JRI"

PATH - must contain the "bin" directory of R (Windows specific)

Please check www.rosuda.org/JRI for more documentation on how to set 
variables on linux/unix/Mac.


The following is copied from the FAQ on www.rosuda.org/JRI:
-----------------------------------------------------------
Q:	

I get the following error, what's wrong?
java.lang.UnsatisfiedLinkError: no jri in java.library.path

A:	

Usually it means that you didn't setup the necessary environment 
variables properly or the JRI library is not where it is expected to be. The recommended way to start 
JRI programs is to use the run script which is generated along with the library. 
It sets everything up and is tested to work. If you want to write your own script or launcher, 
you must observe at least the following points:

    * R_HOME must be set correctly
    * (Windows): The directory containing R.dll must be in your PATH
    * (Mac): Well, it's a Mac, so it just works ;).
    * (unix): R must be compiled using --enable-R-shlib and the directory containing libR.so must be in LD_LIBRARY_PATH. 
	Also libjvm.so and other dependent Java libraries must be on LD_LIBRARY_PATH.
    * JRI library must be in the current directory or any directory listed in java.library.path. 
	Alternatively you can specify its path with
      -Djava.library.path= when starting the JVM. When you use the latter, make sure you check java.library.path property 
	first such that you won't break your Java.
    * Depending on your system, the R verison and other features you want to use, you may have to set 
	additional settings such as R_SHARE_DIR, R_INCLUDE_DIR and R_DOC_DIR. 

Again, I don't think you want to worry about all of the above - just use the start script! 

-----------------------------------------------------------------------------------------


