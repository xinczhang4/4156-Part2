package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


/**
 * Unit tests for the RouteController class. This class contains tests to verify the functionality
 * of the RouteController class.
 */
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(RouteController.class)
public class Part2_Function {

  @Autowired
  private MockMvc mockMvc;

  @InjectMocks
  private RouteController testRouteController;

  @Mock
  private MyFileDatabase myFileDatabase;

  /**
   * Sets up the test environment before all test cases by initializing test data for the Department
   * and Course classes.
   *
   * @throws Exception if any error occurs during setup
   */
  @BeforeAll
  public static void setUpBeforeClass() throws Exception {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};

    phys1001 = new Course("Szabolcs Marka", "301 PUP", times[3], 150);
    phys1001.setEnrolledStudentCount(131);
    //data for psyc dept
    psyc1001 = new Course("Patricia G Lindemann", "501 SCH", "1:10-2:25", 200);
    psyc1001.setEnrolledStudentCount(191);
    psyc1610 = new Course("Christopher Baldassano", "200 SCH", times[2], 45);
    psyc1610.setEnrolledStudentCount(42);
    Course psyc2235 = new Course("Katherine T Fox-Glassman", "501 SCH", times[0], 125);
    psyc2235.setEnrolledStudentCount(128);
    Course psyc2620 = new Course("Jeffrey M Cohen", "303 URIS", "1:10-3:40", 60);
    psyc2620.setEnrolledStudentCount(55);
    Course psyc3212 = new Course("Mayron Piccolo", "200 SCH", "2:10-4:00", 15);
    psyc3212.setEnrolledStudentCount(15);
    Course psyc3445 = new Course("Mariam Aly", "405 SCH", "2:10-4:00", 12);
    psyc3445.setEnrolledStudentCount(12);
    Course psyc4236 = new Course("Trenton Jerde", "405 SCH", "6:10-8:00", 18);
    psyc4236.setEnrolledStudentCount(17);
    Course psyc4493 = new Course("Jennifer Blaze", "200 SCH", "2:10-4:00", 15);
    psyc4493.setEnrolledStudentCount(9);

    Map<String, Course> courses = new HashMap<>();
    courses.put("1001", psyc1001);
    courses.put("1610", psyc1610);
    courses.put("2235", psyc2235);
    courses.put("2620", psyc2620);
    courses.put("3212", psyc3212);
    courses.put("3445", psyc3445);
    courses.put("4236", psyc4236);
    courses.put("4493", psyc4493);

    psyc = new Department("PSYC", courses, "Nim Tottenham", 437);
  }

  @Test
  public void retrieveCoursesSuccessTest() throws Exception {
    mockMvc.perform(get("/retrieveCourses")
            .param("courseCode", "1001"))
        .andExpect(status().isOk())
        .andExpect(content().string(phys1001.toString().concat("\n").concat(psyc1001.toString()).concat("\n")));
  }

  @Test
  public void retrieveCoursesSingleSuccessTest() throws Exception {
    mockMvc.perform(get("/retrieveCourses")
            .param("courseCode", "1610"))
        .andExpect(status().isOk())
        .andExpect(content().string(psyc1610.toString().concat("\n")));
  }

  @Test
  public void retrieveCoursesFailedTest() throws Exception {
    mockMvc.perform(get("/retrieveCourses")
            .param("courseCode", "0000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No courses found with the given course code"));
  }


  private static Course phys1001;
  private static Department psyc;
  private static Course psyc1001;
  private static Course psyc1610;
}