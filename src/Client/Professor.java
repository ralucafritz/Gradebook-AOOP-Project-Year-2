package Client;

import Extras.*;
import Platform.*;

import java.util.ArrayList;

public class Professor extends Account{

    private ArrayList<Course> courses =  new ArrayList<Course>();

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


    public ArrayList<Course> getCourses() {
        return courses;
    }

    // extra methods

    public void accountInfo(){
        super.accountInfo();
        System.out.println("List teaching courses: \n \t " +  Util.arrayListToString(this.courses));

    }
}
