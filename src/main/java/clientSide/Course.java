package clientSide;

import interfaces.GetNameInterface;
import repositories.CourseRepository;
import repositories.GroupRepository;

import java.util.ArrayList;
import java.util.Set;

public class Course implements GetNameInterface {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String name;
    private int currentID;
    private static int ID;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CONSTRUCTORS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /// EMPTY CONSTRUCTOR FOR A COURSE
    public Course() {
        Set<Course> listCourse = CourseRepository.getInstance().getCourseList();
        for(Course course : listCourse) {
            if(course.getID()==ID){
                ID++;
            }
        }
        this.currentID = ID;
        ID ++;
    }

    // CONSTRUCTOR WITH PARAMETERS

    public Course(String name) {
        this.name = name;
        Set<Course> listCourse = CourseRepository.getInstance().getCourseList();
        for(Course course : listCourse) {
            if(course.getID()==ID){
                ID++;
            }
        }
        this.currentID = ID;
        ID ++;
    }

    // CONSTRUCTOR FOR LOADING DATA FROM THE DB
    public Course(String name, int currentID)   {
        this.name = name;
        this.currentID = currentID;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// MUTATORS //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }
//
//    public void changeProfessor(Professor professorToBeSelected) {
//        this.professor = professorToBeSelected;
//        professorToBeSelected.addCourse(this);
//    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// ACCESSORS ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public int getID() {
        return currentID;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// DATABASE //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDB() {
        int courseId = CourseRepository.getInstance().getIdByCourse(this);
        CourseRepository.getInstance().updateCourse(this, courseId);
    }

}
