#!/usr/bin/env bash
rm -rf out/*
javac -cp libs/junit-platform-console-standalone-1.5.0-RC2.jar -sourcepath src  -d out src/tests/*.java
java -jar libs/junit-platform-console-standalone-1.5.0-RC2.jar -cp out --scan-classpath --details=tree
