package Client;

import Extras.Gender;
import Extras.Util;
import Platform.Course;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Student extends Account {

    private Map<Course, Integer> courses = new HashMap<>();
//    private ArrayList<Course> courses =  new ArrayList< Course>();
//    private Group group;

    // constructors

    public Student() throws Exception {
       super();
    }

    public Student(String name, String dateOfBirth) throws Exception {
        super(name, dateOfBirth);
    }

    public Student(String name, String gender, String dateOfBirth) throws Exception {
        super(name, gender, dateOfBirth);
    }

    public Student(String name, Gender gender, String dateOfBirth) throws Exception {
        super(name, gender, dateOfBirth);
    }
// mutators
//
//    public void setGroup(Platform.Group group) {
//        this.group = group;
//    }

    public void addCourse(Course course) throws Exception{
        if (!this.courses.containsKey(course) && !getCourseName(course).equals(course.getName()))
        {
            this.courses.put(course, 0);
        }
        else{
            throw new Exception(this.getName() + " is already enrolled in this course.");
        }
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    public void setGrade(Course course, int gradeValue) throws Exception{
        if(this.courses.containsKey(course) && gradeValue != this.courses.get(course))
        {
            int oldValue = this.courses.get(course);
            this.courses.replace(course, oldValue, gradeValue);
        }
        else
            throw new Exception(this.getName() + " is not enrolled in this course");

    }


    // accessors

    public Set<Course> getCourses() {
        return courses.keySet();
    }

    public String getCourseName(Course course) {
        for (Course item : this.getCourses()){
            if(item.getName().equals(course.getName()))
                return item.getName();
        }
        return "";
    }

//    public Platform.Group getGroup() {
//        return group;
//    }

    // extra methods

    public void accountInfo() {
        super.accountInfo();
//        System.out.println("Group: " + this.group.getName() + "\n" +
        System.out.println("List enlisted courses: \n" + Util.setToString(this.courses.keySet()));
    }

    public String coursesAndGrades() {
        StringBuilder coursesAndGradesStr = new StringBuilder();
        coursesAndGradesStr.append("List of courses and grades: ");
        for(Course course : courses.keySet()) {
            coursesAndGradesStr.append("\n \t ").append(course.getName()).append(": ").append(courses.get(course));
        }
        return coursesAndGradesStr.toString();
    }

}
