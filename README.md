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

You'll be able to reach our service by cloud computing here is what you need to do:
When running tests in Postman point them to: https://coms4156-03.uc.r.appspot.com/endpoint  
For example: GET https://coms4156-03.uc.r.appspot.com/retrieveCourses?courseCode=1001 should return
course information.

## Running Tests

Our unit tests are located under the directory 'src/test'. To run our project's tests in
IntelliJ using Java 17, you must first build the project.

From there, you can right-click any of the classes present in the src/test directory and click run
to see the results.

## EndPoints

This section describes some of the endpoints that our service provides, as well as their inputs and
outputs.

### GET /retrieveDept

Expected Input Parameters: deptCode(String)  
Expected Output: HTTP OK Status along with detailed department information (String)  
Retrieve the department information according to specified dept code.  
Upon Success: HTTP 200 Status Code is returned along with the department information in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Department Not Found" in the
  response body if the specified department does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /retrieveCourse

Expected Input Parameters: deptCode(String) courseCode(int)  
Expected Output: HTTP OK Status along with course information (String)  
Retrieve the course information according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with the course information in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /retrieveCourses

Expected Input Parameters: courseCode(int)  
Expected Output: HTTP OK Status along with course information (String)  
Retrieve courses information according to course code, multiple courses may be returned.  
Upon Success: HTTP 200 Status Code is returned along with courses information in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "No courses found with the given course code" in the
  response body if the specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /isCourseFull

Expected Input Parameters: deptCode(String) courseCode(int)  
Expected Output: HTTP OK Status along with true/false string indicating the course enrollment info  
Retrieve the course enrollment information, return whether it is full or not according to specified
department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with the course information in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /getMajorCountFromDept

Expected Input Parameters: deptCode(String)  
Expected Output: HTTP OK Status along with major count information (String)  
Retrieve department's major count information according to specified dept code.  
Upon Success: HTTP 200 Status Code is returned along with the department major count information
in the response body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Department Not Found" in the
  response body if the specified department does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /idDeptChair

Expected Input Parameters: deptCode(String)  
Expected Output: HTTP OK Status along with department chair information (String)  
Retrieve department chair information according to specified dept code.  
Upon Success: HTTP 200 Status Code is returned along with the department chair information
in the response body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Department Not Found" in the
  response body if the specified department does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /findCourseLocation

Expected Input Parameters: deptCode(String) courseCode(int)  
Expected Output: HTTP OK Status along with course location (String)
Retrieve the course location information according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with the course location in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /findCourseInstructor

Expected Input Parameters: deptCode(String) courseCode(int)  
Expected Output: HTTP OK Status along with course instructor (String)
Retrieve the course instructor information according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with the course instructor in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### GET /findCourseTime

Expected Input Parameters: deptCode(String) courseCode(int)  
Expected Output: HTTP OK Status along with course time (String)
Retrieve the course time information according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with the course time in the response
body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
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

### PATCH /removeMajorFromDept

Expected Input Parameters: deptCode(String)  
Expected Output: HTTP OK Status along with a JSON string  
Deduct student count by one to the specified department unless it is already zero.
Upon Success: HTTP 200 Status Code is returned along with "Attribute was updated or is at minimum"
string in the response body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Department Not Found" in the
  response body if the specified department does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### PATCH /dropStudentFromCourse

Expected Input Parameters: deptCode(String) courseCode(int)  
Expected Output: HTTP OK Status along with JSON String
Deduct the enrolled student count of a course according to specified department code and course
code.  
Upon Success: HTTP 200 Status Code is returned along with "Student has been dropped." in the
response body.  
Upon Failure:

- HTTP 400 Status Code is returned along with "Student has not been dropped." in the response
  body if the enrollment count of the course is already zero.
- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### PATCH /setEnrollmentCount

Expected Input Parameters: deptCode(String) courseCode(int) count(int)  
Expected Output: HTTP OK Status along with JSON String
Set the enrollment count of a course according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along string "Attributed was updated successfully."
in the response body.  
Upon Failure:

- HTTP 400 Status Code is returned along with "Cannot set negative value as enrollment number." in
  the response body if the count value to be set is negative.
- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### PATCH /enrollStudentInCourse

Expected Input Parameters: deptCode(String) courseCode(int)  
Expected Output: HTTP OK Status along with JSON String
Enroll a student in the course according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with "Attributed was updated successfully." in
the response body.  
Upon Failure:

- HTTP 400 Status Code is returned along with "Course is full and student cannot be added." in the
  response body if the course is already full.
- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### PATCH /changeCourseTime

Expected Input Parameters: deptCode(String) courseCode(int) time(String)  
Expected Output: HTTP OK Status along with JSON String
Change the time of a course according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with "Attributed was updated successfully." in
the response body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### PATCH /changeCourseTeacher

Expected Input Parameters: deptCode(String) courseCode(int) teacher(String)  
Expected Output: HTTP OK Status along with JSON String
Change the instructor of a course according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with "Attributed was updated successfully." in
the response body.  
Upon Failure

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

### PATCH /changeCourseLocation

Expected Input Parameters: deptCode(String) courseCode(int) location(String)  
Expected Output: HTTP OK Status along with JSON String
Change the location of a course according to specified department code and course code.  
Upon Success: HTTP 200 Status Code is returned along with "Attributed was updated successfully." in
the response body.  
Upon Failure:

- HTTP 404 Status Code is returned along with "Course Not Found" in the response body if the  
  specified course does not exist.
- HTTP 500 Status Code with "An error has occurred" in the response body.

## Style Checking Report

We used the tool "checkstyle" to check the style of our code and generate style checking reports.
Run `mvn checkstyle:check` generates no warning or error.

## Branch Coverage Report

We used JaCoCo to perform branch analysis in order to see the branch coverage of the relevant code
within the code base.
Run `mvn clean test`: all test cases passed  
Tests run: 69, Failures: 0, Errors: 0, Skipped: 0

Run `mvn jacoco:report`: Report generated under IndividualProject/target/site/jacoco/index.html  
Instruction Coverage: 93%, Branch coverage: 82%

## Static Code Analysis

### PMD Installation command:

`$ cd $HOME`  
`$ curl -OL https://github.com/pmd/pmd/releases/download/pmd_releases%2F7.5.0/pmd-dist-7.5.0-bin.zip`  
`$ unzip pmd-dist-7.5.0-bin.zip`  
`$ alias pmd="$HOME/pmd-bin-7.5.0/bin/pmd"`

### Check command:

`cd IndividualProject`
`pmd check -d src -R rulesets/java/quickstart.xml -f text -r pmd.txt`
PMD found no problems in my code.

## Continuous Integration Report

This repository using GitHub Actions to perform continous integration, to view the latest results go
to the following
link: https://github.com/xinczhang4/4156-Part2/blob/main/.github/workflows/maven.yml

Click on the latest job on the top under "X workflow runs" then Click 'build' under jobs finally
click the drop down next to all the action items to read the logs made during their execution.

## Tools Used

This section includes notes on tools and technologies used in building this project, as well as any
additional details if applicable.
- Maven Package Manager
- GitHub Actions CI
- Checkstyle
- PMD
- Jacoco
- Postman

## Bug Report Location

`IndividualProject/bugs.txt`

## App Deployment Display

https://youtu.be/fgM0AMgty7U
https://youtu.be/JprSd-qXSDw