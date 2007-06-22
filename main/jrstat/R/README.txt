R and all packages should go in this directory, 
for example

"R-2.5.0"  could be copied from the R installation directory,
the relative R_HOME would then be:

./jrstat/R/R-2.5.0


in the PATH would be required

./jrstat/R/R-2.5.0/bin

and for the library path:


-Djava.library.path=jrstat/R/R-2.5.0/library/rJava/jri

