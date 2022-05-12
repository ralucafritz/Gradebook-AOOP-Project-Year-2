package Client;

import Extras.Gender;
import Extras.Util;
import Platform.Course;

import java.util.HashSet;
import java.util.Set;

public class Professor extends Account{

    private Set<Course> courses =  new HashSet<>();

    // constructors
    public Professor() throws Exception {
        super();
    }

    public Professor(String name, String dateOfBirth) throws Exception {
        super(name, dateOfBirth);
    }

    public Professor(String name, String gender, String dateOfBirth) throws Exception {
        super(name, gender, dateOfBirth);
    }

    public Professor(String name, Gender gender, String dateOfBirth) throws Exception {
        super(name, gender, dateOfBirth);
    }

    // mutators

    public void addCourse(Course courseToBeAdded){
        this.courses.add(courseToBeAdded);

    }

    public void removeCourse(Course courseToBeRemoved){
        this.courses.remove(courseToBeRemoved);
    }

    // accessors

    public Set<Course> getCourses() {
        return courses;
    }

    // extra methods

    public void accountInfo(){
        super.accountInfo();
        printCourses();
    }

    public void printCourses() {
        System.out.println("List teaching courses: \n" +  Util.setToString(this.courses));
    }

    public void mark(Student student, Course course, int grade) throws Exception {
        if(this == course.getProfessor() && this.courses.contains(course))
        {
            student.setGrade(course, grade);
        }
        else
            throw new Exception(this.getName() + " is not teaching this course" );
    }
}
