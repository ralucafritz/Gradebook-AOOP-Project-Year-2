package clientSide;

import extras.Gender;
import extras.Util;
import repositories.CourseRepository;
import repositories.StudentRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Student extends Account {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // the `Map courses` is storing the list of courses and the grades for each course
    // the grades are initialized with 0 when a course is added and it needs a professor to mark the course in
    // order to change it

    private Map<Course, Integer> courses = new HashMap<>();


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CONSTRUCTORS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /// EMPTY CONSTRUCTOR FOR A STUDENT
    public Student() {
        super();
    }

    // CONSTRUCTOR WITH PARAMETERS
    public Student(String name, Gender gender, String dateOfBirth)  {
        super(name, gender, dateOfBirth);
    }

    // CONSTRUCTOR FOR LOADING DATA FROM THE DB
    public Student(String name, String gender, String dateOfBirth, String courses, int currentID)  {
        super(name, gender, dateOfBirth);

        if(!courses.isEmpty()) {
             String[] coursesGradesArr = courses.split(","); // ex 1-10,2-20..

             for (String str : coursesGradesArr) {
                 // ex str = 1-10
                 String[] strArr = str.split("-");

                 int idCourse = Integer.parseInt(strArr[0]);
                 int grade = Integer.parseInt(strArr[1]);

                 CourseRepository courseRepository = CourseRepository.getInstance();

                 Course course = courseRepository.getObjectById(idCourse);

                 addCourse(course);
             }
        }
        setCurrentID(currentID);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// MUTATORS //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ADD COURSE AND EDIT THE COURSES LIST
    public void addCourse(Course course)  {
        try {
            if (!this.courses.containsKey(course) && !getCourseName(course).equals(course.getName())) {
                this.courses.put(course, 0);
            } else {
                throw new Exception(this.getName() + " is already enrolled in this course.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // REMOVE COURSE FROM THE COURSES LIST
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    // MARKING A GRADE OF A COURSE
    // THIS CAN ONLY BE DONE BY A PROFESSOR
    public void setGrade(Course course, int gradeValue)  {
        try {
            if (this.courses.containsKey(course) && gradeValue != this.courses.get(course)) {
                int oldValue = this.courses.get(course);
                this.courses.replace(course, oldValue, gradeValue);
            } else
                throw new Exception(this.getName() + " is not enrolled in this course");
        } catch (Exception e) {
        e.printStackTrace();
         }
    }

    // SETTER FOR HTE COURSES LIST ---- NOT NEEDED AND NOT USED
    public void setCourses(Map<Course, Integer> courses) {
        this.courses = courses;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// ACCESSORS ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Set<Course> getCourses() {
        return courses.keySet();
    }

    public Map<Course, Integer> getCoursesList() {
        return courses;
    }

    public String getCourseName(Course course) {
        for (Course item : this.getCourses()) {
            if (item.getName().equals(course.getName()))
                return item.getName();
        }
        return "";
    }

    public int getGrade(Course course) {
        for (Course course1 : this.courses.keySet())
            if (course == course1)
                return this.courses.get(course);
        return -1;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// PRINTS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // PRINT DATA ABOUT THE STUDENT
    public void accountInfo() {
        super.accountInfo();
    }

    // PRINT LIST OF COURSES
    public void printCourses() {
        if (!courses.isEmpty()) {
            System.out.println("\t List enlisted courses: \n" +
                    Util.setToString(this.courses.keySet(), "\t\t", "\n"));
        }
    }

    // PRINT LIST OF COURSES AND THEIR GRADES
    public void coursesAndGrades() {
        StringBuilder coursesAndGradesStr = new StringBuilder();

        coursesAndGradesStr.append("List of courses and grades: ");
        for (Course course : this.courses.keySet()) {
            if (this.courses.get(course) != 0)
                coursesAndGradesStr.append("\n \t ").append(course.getName()).append(": ").append(this.courses.get(course));
            else
                coursesAndGradesStr.append("\n \t ").append(course.getName()).append(": ").append("NOT GRADED");
        }
        System.out.println(coursesAndGradesStr);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////// EXTRA METHODS /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // CHECK IF THE STUDENT IS ENROLLED IN THE COURSE SPECIFIED OR NOT
    public boolean checkEnrolledCourse(Course course) {
        if (this.courses.containsKey(course)) {
            return true;
        }
        return false;
    }

    // RETURN A MAP OF COURSES AND GRADES FROM LOADING THE DATABASE DATA
    public static Map<Course, Integer> getCoursesGradesMap(String coursesAndGrades)  {
        String[] coursesGradesArr = coursesAndGrades.split(","); // ex 1-10,2-20..

        Map<Course, Integer> coursesAndGradesLocal = new HashMap<>();

        for (String str : coursesGradesArr) {
            // EXMPLE TEMPLATE OF str = 1-10
            String[] strArr = str.split("-");

            // RETRIEVE THE ID AND THE GRADE IN 2 SEPARATE INTS
            int idCourse = Integer.parseInt(strArr[0]);
            int grade = Integer.parseInt(strArr[1]);

            Course course = CourseRepository.getInstance().getObjectById(idCourse);
            coursesAndGradesLocal.put(course, grade);
        }
        return coursesAndGradesLocal;
    }

    // RETURN A STRING MADE OUT OF COURSES AND GRADES TO SAVE THE DATA IN THE DATABASE
    // WITH THE TEMPLATE COURSEID-GRADE,COURSEID-GRADE...
    public String returnCoursesAndGradesList()  {
        CourseRepository courseRepository = CourseRepository.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        for (Course course : courses.keySet())
        {
            if(stringBuilder.length() != 0)
                stringBuilder.append(",");
            stringBuilder
                    .append(courseRepository.getIdByObject(course))
                    .append("-")
                    .append(courses.get(course));
        }
        return stringBuilder.toString();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// DATABASE //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDB() {
        if(StudentRepository.getInstance().getIdByObject(this)!=-1) {
            int studentId = StudentRepository.getInstance().getIdByObject(this);
            StudentRepository.getInstance().updateStudent(this, studentId);
        }
    }
}