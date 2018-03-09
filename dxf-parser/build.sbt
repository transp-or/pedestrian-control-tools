/** This program is from http://kabeja.sourceforge.net/
  * Small modifications have been made to the original code to make it more usable.
  * In order to compile this code and use it as a locla package, the java folder inside
  * src has been moved into a folder called main, hence the structure is src/main/java/...
  *
  * To use this project, one must simply run sbt in the home directory (where the "build.sbt" file is located)
  * and then run "publishLocal". This will publish the program on your computer and then it can be imported
  * in other programs.
  */

// Organisation name
organization := "transpor.tools"

// Name of the project
name := "dxf-parser"

// Version of the program
version := "1.0"

// This program is written in Java, hence to force sbt to compile java code
autoScalaLibrary := false

// Do not append Scala versions to the generated artifacts
crossPaths := false


