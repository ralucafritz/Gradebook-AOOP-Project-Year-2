package Platform;

import Client.Student;
import Extras.Util;
import Interfaces.NamableForNow;

import java.util.ArrayList;

public class Group implements NamableForNow {
    private int currentID;
    private ArrayList<Student> studentsList = new ArrayList<Student>();
    private ArrayList<Course> coursesList=  new ArrayList<Course>();

    private static int ID =  100;
    // constructors

    public Group() {
        this.currentID = ID;
        ID ++;
    }

    public Group( ArrayList<Student> students) {
        this.studentsList = students;
        this.currentID = ID;
        ID ++;
    }

    // mutators

    public void addCourse(Course courseToBeAdded) {
        this.coursesList.add(courseToBeAdded);

    }

    public void removeCourse(Course courseToBeRemoved) {
        this.coursesList.remove(courseToBeRemoved);
    }

    public void addStudent(Student studentToBeAdded) {

        this.studentsList.add(studentToBeAdded);
    }

    public void removeStudent(Student studentToBeRemoved) {

        this.studentsList.remove(studentToBeRemoved);
    }

    // accessors

    public String getName() {
        return "" + currentID;
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    public ArrayList<Course> getCoursesList() {
        return coursesList;
    }

    // extra methods:

    @Override
    public String toString() {
        return this.getName();
    }

    public void printStudentsList() {
        System.out.println(Util.arrayListToString(this.studentsList));
    }

    public void printCoursesList() {
        System.out.println(Util.arrayListToString(this.coursesList));
    }

    // ADD GROUP TO GO THROUGH THE STUDENT LIST TO ADD THE COURSE
}
