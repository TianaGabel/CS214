# Log4j 2

## Setup

To use log4j in your program, you first need to download the [Log4j `.jar` files](https://dlcdn.apache.org/logging/log4j/2.12.4/apache-log4j-2.12.4-bin.tar.gz). Over the commandline this can be accomplished using

```sh
wget https://dlcdn.apache.org/logging/log4j/2.12.4/apache-log4j-2.12.4-bin.tar.gz
```

The files come as a `.tar.gz` file (essentially the linux equivalent of a `.zip`) which needs to be extracted. Since we only need two specific `.jar` files we can extract them using

```sh
tar -zxvf apache-log4j-2.12.4-bin.tar.gz apache-log4j-2.12.4-bin/log4j-core-2.12.4.jar apache-log4j-2.12.4-bin/log4j-api-2.12.4.jar
```

You should now have two `.jar` files in a `apache-log4j-2.12.4-bin/` folder. In order to use log4j you need to include these when you compile and run your program. It is recommended to copy the `.jar` files to the root of your repository

As an alternative option to the above commands, the `.jar` files can also be obtained by downloading the [`.zip` version](https://dlcdn.apache.org/logging/log4j/2.12.4/apache-log4j-2.12.4-bin.zip) of the archive and using a file explorer to extract the `log4j-api-2.12.4.jar` and `log4j-core-2.12.4.jar` files

## Including Log4j in program compilation and execution

The Log4j `.jar` files that were downloaded during the previous step need to be added to the class path when compiling and running your program so the Log4j classes are available in your code.

> **Side Note**<br>
> Conceptually, the Java class path is named `class path` because it is a list of folders that contain `.class` files. By default it is set to `.` (the current folder), so any `.class` files in the current directory are loaded when you execute `java Main`.
> We can add `.jar` files to the class path even though they are not folders since they are archives that contain `.class` files. You can confirm this by renaming a `.jar` to `.zip` and opening it using a file explorer.

To compile your program with log4j, run

```sh
javac -d . --class-path ../log4j-api-2.12.4.jar:../log4j-core-2.12.4.jar <all the .java files except the JUnit tests>
```

In contrast to JUnit where the jar file is directly executed, the `.jar` files still need to be included during execution

```sh
java --class-path ../log4j-api-2.12.4.jar:../log4j-core-2.12.4.jar:. Main
```

### Testing successful compilation

`LoggerAvailableTest.java` is a JUnit test that checks whether the log4j classes are available at runtime. To get it to pass, you need to compile and run it with log4j

#### Failing

```sh
javac --class-path ../junit-platform-console-standalone-1.9.3.jar LoggerAvailableTest.java 
java -jar ../junit-platform-console-standalone-1.9.3.jar --class-path . --select-class LoggerAvailableTest
```

#### Passing

```sh
javac --class-path ../junit-platform-console-standalone-1.9.3.jar:../log4j-core-2.12.4.jar:../log4j-api-2.12.4.jar LoggerAvailableTest.java 
java -jar ../junit-platform-console-standalone-1.9.3.jar --class-path ../log4j-core-2.12.4.jar:../log4j-api-2.12.4.jar:. --select-class LoggerAvailableTest
```

### Log4j Example

The provided `Log4jExample.java` and `log4j2.xml` can be run using

```sh
javac --class-path ../log4j-core-2.12.4.jar:../log4j-api-2.12.4.jar Log4jExample.java 
java --class-path ../log4j-core-2.12.4.jar:../log4j-api-2.12.4.jar:. Log4jExample
```

In `log4j2.xml`, log4j is configured to write logs to the console and a file named `game.log` which will be created when you run the program. Feel free to modify `Log4jExample.java` and experiment a bit

## The `Makefile`

The Makefile is supposed to simplify compilation and replace manually running the `javac` and `java` commands

### Configuration

At the top of the Makefile there are a few variables declared which may have to be adjusted for your specific program.

`MAIN_CLASS_NAME` should be the name of your Main class. If you declared, for example, `package PA5;` at the top of `Main.java`, this variable should be `PA5.Main`. *There should be no Java package in use at this time, we have other plans for using Java packages*

`JUNIT4_TEST_CLASS_NAME` and `JUNIT5_TEST_CLASS_NAME` work the same way

`COMMON_JAR_DIR` is the directory containing the JUnit and Log4j `.jar` files. By default it is the parent directory (`..`). If you are having issues determining the correct value: In the terminal navigate to the folder where the `.jar` files are located, run `pwd`, and set the variable to the output you get

### Targets

To use the Makefile, place it into your assignment folder and run one of the commands in the terminal

```sh
# Compile and run both JUnit tests and if they pass, compile and run Main
make

# Compile only the main program
make compile

# Run the main program
make run

# Compile the JUnit4 and 5 tests
make compile-junit4
make compile-junit5

# Run the JUnit 4 and 5 tests
make run-junit4
make run-junit5

# Remove every .class file in the current directory or its subdirectories
make clean
```
