package dev.coms4156.project.individualproject;

import java.util.Locale;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class contains all the API routes for the system.
 */
@RestController
public class RouteController {

  /**
   * Redirects to the homepage.
   *
   * @return A String containing the name of the html file to be loaded.
   */
  @GetMapping({"/", "/index", "/home"})
  public String index() {
    return "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";
  }

  /**
   * Returns the details of the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes to retrieve.
   * @return A {@code ResponseEntity} object containing either the details of the Department and an
   *     HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveDepartment(@RequestParam("deptCode") String deptCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

      if (!departmentMapping.containsKey(deptCode.toUpperCase(Locale.ROOT))) {
        return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(
            departmentMapping.get(deptCode.toUpperCase(Locale.ROOT)).toString(),
            HttpStatus.OK);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the details of the requested course to the user or displays the proper error message
   * in response to the request.
   *
   * @param deptCode   A {@code String} representing the department the user wishes to find the
   *                   course in.
   * @param courseCode A {@code int} representing the course the user wishes to retrieve.
   * @return A {@code ResponseEntity} object containing either the details of the course and an HTTP
   *     200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourse(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        if (!coursesMapping.containsKey(Integer.toString(courseCode))) {
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        } else {
          return new ResponseEntity<>(coursesMapping.get(Integer.toString(courseCode)).toString(),
              HttpStatus.OK);
        }

      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the details of all courses matching specified course code accross all departments to
   * the user or displays the proper error message in response to the request.
   *
   * @param courseCode A {@code int} representing the course the user wishes to retrieve.
   * @return A {@code ResponseEntity} object containing either the details of the course and an HTTP
   *     200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveCourses", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourses(@RequestParam("courseCode") int courseCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
      StringBuilder coursesInfo = new StringBuilder();

      for (Map.Entry<String, Department> deptEntry : departmentMapping.entrySet()) {
        Map<String, Course> coursesMapping = deptEntry.getValue().getCourseSelection();
        if (coursesMapping.containsKey(Integer.toString(courseCode))) {
          Course course = coursesMapping.get(Integer.toString(courseCode));
          coursesInfo.append(course.toString()).append("\n");
        }
      }
      if (coursesInfo.length() == 0) {
        return new ResponseEntity<>("No courses found with the given course code",
            HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(coursesInfo.toString(), HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays whether the course has at minimum reached its enrollmentCapacity.
   *
   * @param deptCode   A {@code String} representing the department the user wishes to find the
   *                   course in.
   * @param courseCode A {@code int} representing the course the user wishes to retrieve.
   * @return A {@code ResponseEntity} object containing either the requested information and an HTTP
   *     200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/isCourseFull", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> isCourseFull(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.isCourseFull(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the number of majors in the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find number of
   *                 majors for.
   * @return A {@code ResponseEntity} object containing either number of majors for the specified
   *     department and an HTTP 200 response or, an appropriate message indicating the proper
   *     response.
   */
  @GetMapping(value = "/getMajorCountFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>("There are: " + departmentMapping.get(deptCode)
            .getNumberOfMajors() + " majors in the department", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the department chair for the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find the
   *                 department chair of.
   * @return A {@code ResponseEntity} object containing either department chair of the specified
   *     department and an HTTP 200 response or, an appropriate message indicating the proper
   *     response.
   */
  @GetMapping(value = "/idDeptChair", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> identifyDeptChair(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>(departmentMapping.get(deptCode).getDepartmentChair() + " is "
            + "the department chair.", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the location for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes to find the
   *                   course in.
   * @param courseCode A {@code int} representing the course the user wishes to find information
   *                   about.
   * @return A {@code ResponseEntity} object containing either the location of the course and an
   *     HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/findCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseLocation(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.getCourseLocation() + " is where the course "
            + "is located.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the instructor for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes to find the
   *                   course in.
   * @param courseCode A {@code int} representing the course the user wishes to find information
   *                   about.
   * @return A {@code ResponseEntity} object containing either the course instructor and an HTTP 200
   *     response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/findCourseInstructor", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseInstructor(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.getInstructorName() + " is the instructor for"
            + " the course.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the time the course meets at for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes to find the
   *                   course in.
   * @param courseCode A {@code int} representing the course the user wishes to find information
   *                   about.
   * @return A {@code ResponseEntity} object containing either the details of the course timeslot
   *     and an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/findCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseTime(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>("The course meets at: " + requestedCourse.getCourseTimeSlot(),
            HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to add a student to the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200 response with an appropriate
   *     message or the proper status code in tune with what has happened.
   */
  @PatchMapping(value = "/addMajorToDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addMajorToDept(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.addPersonToMajor();
        return new ResponseEntity<>("Attribute was updated successfully", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to remove a student from the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200 response with an appropriate
   *     message or the proper status code in tune with what has happened.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeMajorFromDept(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.dropPersonFromMajor();
        return new ResponseEntity<>("Attribute was updated or is at minimum", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to drop a student from the specified course.
   *
   * @param deptCode   A {@code String} representing the department.
   * @param courseCode A {@code int} representing the course within the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200 response with an appropriate
   *     message or the proper status code in tune with what has happened.
   */
  @PatchMapping(value = "/dropStudentFromCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> dropStudent(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        boolean isStudentDropped = requestedCourse.dropStudent();

        if (isStudentDropped) {
          return new ResponseEntity<>("Student has been dropped.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Student has not been dropped.", HttpStatus.BAD_REQUEST);
        }
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
   * Update the enrollment count for a course in the department. This method handles PATCH requests
   * to update the enrollment count for a course according to its department code and course code.
   * If the course exists, its enrollment number is updated. If not, a 404 error is returned.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param count      the new enrollment count for the course
   * @return a ResponseEntity with either a success message and HTTP 200 status, or an error message
   *     if the course is not found.
   */
  @PatchMapping(value = "/setEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> setEnrollmentCount(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode,
      @RequestParam("count") int count) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        boolean result = requestedCourse.setEnrolledStudentCount(count);
        if (result) {
          return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Cannot set negative value as enrollment number.",
              HttpStatus.BAD_REQUEST);
        }
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Enroll a student for a course in the department. This method handles PATCH requests to update
   * the enrollment count by 1 for a course according to its department code and course code. If the
   * course exists, its enrollment number is updated. If not, a 404 error is returned.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @return a ResponseEntity with either a success message and HTTP 200 status, or an error message
   *     if the course is not found.
   */
  @PatchMapping(value = "/enrollStudentInCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> enrollStudentInCourse(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        boolean result = requestedCourse.enrollStudent();
        if (result) {
          return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Course is full and student cannot be added.",
              HttpStatus.BAD_REQUEST);
        }

      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
   * Endpoint for changing the time of a course. This method handles PATCH requests to change the
   * time of a course identified by department code and course code.If the course exists, its time
   * is updated to the provided time.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param time       the new time for the course
   * @return a ResponseEntity with a success message if the operation is successful, or an error
   *     message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTime(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode,
      @RequestParam("time") String time) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignTime(time);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the instructor of a course. This method handles PATCH requests to change
   * the instructor of a course identified by department code and course code. If the course exists,
   * its instructor is updated to the provided instructor.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the instructor for
   * @param teacher    the new instructor for the course
   * @return a ResponseEntity with a success message if the operation is successful, or an error
   *     message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTeacher(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode,
      @RequestParam("teacher") String teacher) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignInstructor(teacher);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
   * Endpoint for changing the location of a course. This method handles PATCH requests to change
   * the location of a course identified by department code and course code. If the course exists,
   * its location is updated to the provided location.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the instructor for
   * @param location   the new location for the course
   * @return a ResponseEntity with a success message if the operation is successful, or an error
   *     message if the course is not found
   */
  @PatchMapping(value = "/changeCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseLocation(@RequestParam("deptCode") String deptCode,
      @RequestParam("courseCode") int courseCode,
      @RequestParam("location") String location) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignLocation(location);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }


}