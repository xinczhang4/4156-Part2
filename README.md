# 4156 Individual Project 3

### Xinchen Zhang (xz3052)

## Building and Running a Local Instance

In order to build and use our service on Mac platform, you must install the following:

1. Maven 3.9.8
2. Java 17
3. IntelliJ IDE
4. PMD for static code analysis
5. When you open IntelliJ you have the option to clone from a GitHub repo, click the green code
   button and copy the code http that is provided there to clone.
6. Run `mvn clean package --file ./IndividualProject/pom.xml` to build the service.
7. Run `mvn clean test` to run the unit test cases.
8. If you wish to run the style checker you can run `mvn checkstyle:check` and
   `pmd check -d src -R rulesets/java/quickstart.xml -f text -r pmd.txt`.

## Running a Cloud based Instance

When running tests in Postman point them to: https://coms4156-03.uc.r.appspot.com/endpoint  
For example: GET https://coms4156-03.uc.r.appspot.com/retrieveCourses?courseCode=1001 should return
course information.

## EndPoints

This section describes soem of the endpoints that our service provides, as well as their inputs and
outputs.

### GET /retrieveDept

Expected Input Parameters: deptCode(String)  
Expected Output: HTTP OK Status along with deptInfo (String)  
Retrieve the department information according to specified dept code.  
Upon Success: HTTP 200 Status Code is returned along with the department information in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Department Not Found" in the
  response body if the specified department does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### PATCH /addMajorToDept

Expected Input Parameters: deptCode(String)  
Expected Output: HTTP OK Status along with a JSON string  
Add student count by one to the specified department.
Upon Success: HTTP 200 Status Code is returned along with "Attribute was updated successfully"
string in the response body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Department Not Found" in the
  response body if the specified department does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

## Test Cases and Coverage

Run `mvn clean test`: all test cases passed  
Tests run: 69, Failures: 0, Errors: 0, Skipped: 0

Run `mvn jacoco:report`: Report generated under IndividualProject/target/site/jacoco/index.html  
Instruction Coverage: 93%, Branch coverage: 82%

## PMD Installation and Check

### Installation command:

`$ cd $HOME`  
`$ curl -OL https://github.com/pmd/pmd/releases/download/pmd_releases%2F7.5.0/pmd-dist-7.5.0-bin.zip`  
`$ unzip pmd-dist-7.5.0-bin.zip`  
`$ alias pmd="$HOME/pmd-bin-7.5.0/bin/pmd"`

### Check command:

`cd IndividualProject`
`pmd check -d src -R rulesets/java/quickstart.xml -f text -r pmd.txt`

### Bug Report Location

`IndividualProject/bugs.txt`

## CodeStyle Check

`mvn checkstyle:check`  
All codestyle and PMD error cleared.

## App Deployment Display
https://youtu.be/fgM0AMgty7U
https://youtu.be/JprSd-qXSDw