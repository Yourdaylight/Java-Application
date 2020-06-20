#!/usr/bin/env bash
rm -rf out/*
javac -cp libs/junit-platform-console-standalone-1.5.0-RC2.jar -sourcepath src  -d out src/a2/*.java
java -cp out a2.Game
