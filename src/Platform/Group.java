package Platform;

import Client.Professor;
import Client.Student;
import Extras.Util;
import Interfaces.NameInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Group implements NameInterface {
    private int currentID;
    private ArrayList<Student> studentsList = new ArrayList<Student>();
    private ArrayList<Professor> professorsList = new ArrayList<Professor>();
    private Set<Course> coursesList = new HashSet<>();

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

    public void addCourse(Course courseToBeAdded) throws Exception {
        this.coursesList.add(courseToBeAdded);
        this.professorsList.add(courseToBeAdded.getProfessor());
        for(Student student : studentsList){
            student.addCourse(courseToBeAdded);
        }
    }

    public void removeCourse(Course courseToBeRemoved) {
        this.coursesList.remove(courseToBeRemoved);
        this.coursesList.remove(courseToBeRemoved.getProfessor());
        for(Student student: studentsList) {
            student.removeCourse(courseToBeRemoved);
        }
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

    public Set<Course> getCoursesList() {
        return coursesList;
    }

    public ArrayList<Professor> getProfessorsList() {
        return professorsList;
    }

    // extra methods:

    public void printStudentsList() {
        System.out.println(Util.arrayListToString(this.studentsList));
    }

    public void printCoursesList() {
        System.out.println(Util.setToString(this.coursesList));
    }

    public void sortStudentsByName() {
        this.studentsList.sort(Comparator.comparing(Student::getName));
    }

    public void sortProfessorsByName() {
        this.professorsList.sort(Comparator.comparing(Professor::getName));
    }

}
