#!/bin/bash

CLASSPATH=:.:test/JUnitJARs/junit-4.10.jar:test/JUnitJARs/hamcrest-core-1.3.jar


echo Compresor compiler utility, please select an option:
echo 1. Compile all
echo 2. Remove all *.class and *.jar
echo I choose:
read n

if [ $n == 1 ]; then
	javac main/domini/*.java
	javac main/presentacio/*.java
	javac main/persistencia/*.java
	javac -cp $CLASSPATH test/domini/*.java

	jar cfm Compresor.jar Manifest.txt main/domini/*.class main/persistencia/*.class main/presentacio/*.class
fi

if [ $n == 2 ]; then
	rm -rf ./main/domini/*.class
	rm -rf ./main/persistencia/*.class
	rm -rf ./main/presentacio/*.class
	rm -rf ./test/domini/*.class
	rm -rf ./*.jar
fi