four-in-line game

Tasks:
Implement the missing functions (functions that throw the runtime exception) in FourInLine.
Improve AI player.

Folders:

src contains the Java source files.
testdata contains the board data files needed for testing.
libs has the JUnit jar for running tests on the command line.
\tests.GameTest contains the JUnit tests for the four-in-line game.
Note that tests are not exhaustive and you should thoroughly test/play
the implemented game.

Running the program:

Your main method is located in Game.java (right click -> run As -> Java Application )

Tests are located under (src/tests/)
To run the tests: Right click on GameTest.java, run As -> JUnit tests)


Running the application from commandline/terminal:

To compile and run the program from commandline/terminal: 
javac -cp libs/junit-platform-console-standalone-1.5.0-RC2.jar -sourcepath src  -d out src/a2/*.java
java -cp out a2.Game

Or run ./run.sh from terminal


Running the tests from commandline/terminal:

javac -cp libs/junit-platform-console-standalone-1.5.0-RC2.jar -sourcepath src  -d out src/tests/*.java
java -jar libs/junit-platform-console-standalone-1.5.0-RC2.jar -cp out --scan-classpath --details=tree

Or run ./test.sh from terminal