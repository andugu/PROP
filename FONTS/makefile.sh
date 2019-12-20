#!/bin/bash

CLASSPATH=:.:test/JUnitJARs/junit-4.10.jar:test/JUnitJARs/hamcrest-core-1.3.jar

echo ""
echo "##############################"
echo "# Compressor Compiler Utility #"
echo "##############################"
echo ""
echo "Please select an option:"
echo ""
echo "1. Compile all & make .jar exec"
echo "2. Remove all *.class and *.jar"
echo ""
echo "I choose:"
echo ""
read n
echo ""

if [ $n == 1 ]; then
	javac -source 8 -target 8 -nowarn main/domini/*.java
	javac -source 8 -target 8 -nowarn main/presentacio/*.java
	javac -source 8 -target 8 -nowarn main/persistencia/*.java
	javac -cp $CLASSPATH -source 8 -target 8 -nowarn test/domini/*.java

	jar cfm Compressor.jar Manifest.txt main/domini/*.class main/persistencia/*.class main/presentacio/*.class
fi

if [ $n == 2 ]; then
	rm -rf ./main/domini/*.class
	rm -rf ./main/persistencia/*.class
	rm -rf ./main/presentacio/*.class
	rm -rf ./test/domini/*.class
	rm -rf ./*.jar
fi

echo ""
echo "Done, enjoy!"
echo ""