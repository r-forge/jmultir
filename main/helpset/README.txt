This directory contains helpsets for JMulTi. 

All helpsets should be put in the file "jmulti.jar" in the build
process. This is done with the ant script "build.xml".  Then the
helpset directory is not needed in the release anymore.

Helpsets can easily be loaded with the file "modules.xml". Typically a
helpset corresponds to a module. By adding the line

"<Module class="de.jmulti.initanal.InitAnalFrame"
helpset="helpset/initanal/InitAnal.hs"/>"
 
the module "Initial Analysis" would be loaded together with the
corresponding helpset.  It is also possible to load helpsets alone,
like in:

"<Module helpset="helpset/jmulti/JMulTi.hs"/>"

Please make sure that the case of the helpset names is correct.  It
makes no difference whether a helpset is loaded with a module or as a
single helpset.  All helpsets are merged at program start in the order
that is set in the "modules.xml" file.


Note to Helpset Developers
--------------------------

Typically, helpsets for econometric modules contain many formulas
which are not easily written in HTML. Therefore the helpsets for
JMulTi modules have been written in LaTeX and were transformed to HTML
via the latex2HTML tool.

The tool JHelpDev, see jhelpdev.sourceforge.net, makes it easy to
create a JavaHelp helpset from the generated HTML files.

The CVS tree for JMulTi does not contain the helpsets for the analysis
modules, because those are generated from LaTeX sources in the
aforementioned way. Therefore any changes made in these helpsets
should be done in the original LaTeX code instead of the HTML files.
Afterwards the helpsets should be regenerated.

Currently, the LaTeX sources for the helpfiles corresponding to
analysis modules are not Open Source.  Only the general helpset
"jmulti" is Open Source. This helpset is written directly in HTML.

Markus Kraetzig, 4 May 2005