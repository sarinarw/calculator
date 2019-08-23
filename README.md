# Calculator

## Setup
This project uses the SBT build tool.
If you do not already have SBT installed, refer to here: https://www.scala-sbt.org/download.html

## Running
The main class is CalculatorGUI, which can be run from your IDE or also via commandline `sbt run`.

The test cases can be run with `sbt test`.

## Usage
### Entering Equations
1. press the number/operator buttons, then press the `=` key to submit
1. directly type text into the equation field, then press the `enter` key to submit

NOTE: the supported operators are -, +, *, and /

### Examples of Supported Equations
```
1+1
1-1
1*1
1/1
1/0.5
1--1
1+-1
1+1*1/1-1+1
1---1 (equivalent to 1+-1)
```
### Historic Results
The History drop down list contains up to the last 10 results processed.
