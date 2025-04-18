package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;


/**
 * Represents a course within an educational institution. This class stores information about the
 * course, including its location, instructor, time slot, and enrollment capacity.
 */
public class Course implements Serializable {

  /**
   * Constructs a new Course object with the given parameters. Initial count starts at 0.
   *
   * @param instructorName The name of the instructor teaching the course.
   * @param courseLocation The location where the course is held.
   * @param timeSlot       The time slot of the course.
   * @param capacity       The maximum number of students that can enroll in the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 0;
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    if (isCourseFull()) {
      return false;
    }
    enrolledStudentCount++;
    return true;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    if (enrolledStudentCount > 0) {
      enrolledStudentCount--;
      return true;
    }
    return false;
  }


  public String getCourseLocation() {
    return this.courseLocation;
  }


  public String getInstructorName() {
    return this.instructorName;
  }


  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
  }

  @Override
  public String toString() {
    return "\nInstructor: " + instructorName + "; Location: " + courseLocation + "; Time: "
        + courseTimeSlot;
  }


  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }


  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }


  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Set enrollment for a course.
   *
   * @return true if the enrollment is successfully set, false otherwise.
   */
  public boolean setEnrolledStudentCount(int count) {
    if (count >= 0) {
      this.enrolledStudentCount = count;
      return true;
    }
    return false;
  }


  public boolean isCourseFull() {
    return enrolledStudentCount >= enrollmentCapacity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Course course = (Course) o;

    return enrollmentCapacity == course.enrollmentCapacity
        && enrolledStudentCount == course.enrolledStudentCount && instructorName.equals(
        course.instructorName) && courseLocation.equals(course.courseLocation)
        && courseTimeSlot.equals(course.courseTimeSlot);
  }

  @Override
  public int hashCode() {
    int result = instructorName.hashCode();
    result = 31 * result + courseLocation.hashCode();
    result = 31 * result + courseTimeSlot.hashCode();
    result = 31 * result + enrollmentCapacity;
    result = 31 * result + enrolledStudentCount;
    return result;
  }

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
