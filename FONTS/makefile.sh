#!/bin/bash

CLASSPATH=:.:test/JUnitJARs/junit-4.10.jar:test/JUnitJARs/hamcrest-core-1.3.jar

javac main/domini/*.java
javac main/presentacio/*.java
javac main/persistencia/*.java

javac -cp $CLASSPATH test/domini/*.java

jar cfm Compresor.jar Manifest.txt main/domini/*.class main/persistencia/*.class main/presentacio/*.class