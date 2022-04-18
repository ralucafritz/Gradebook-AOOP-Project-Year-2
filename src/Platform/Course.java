package Platform;

import Client.Professor;
import Extras.Generator;
import Interfaces.GetNameInterface;

public class Course implements GetNameInterface {
    private String name;
//    private int credits;   -> TO BE IMPLEMENTED IN THE NEAR FEATURE TO REACH A FINAL GRADE
    private Professor professor;

    // constructors

    public Course(String name) throws Exception {
        this.name = name;
        this.professor = Generator.professorGenerator();
        this.professor.addCourse(this);
    }

    public Course(String name, Professor professor) {
        this.name = name;
//        this.credits = credits;
        this.professor = professor;
        this.professor.addCourse(this);
    }

    // mutators

    public void setName(String name) {
        this.name = name;
    }

//    public void setCredits(int credits) {
//        this.credits = credits;
//    }

    public void changeProfessor(Professor professorToBeSelected) {
        this.professor = professorToBeSelected;
        professorToBeSelected.addCourse(this);
    }

    // accessors

    public String getName() {
        return name;
    }

//    public int getCredits() {
//        return credits;
//    }

    public Professor getProfessor() {
        return professor;
    }

    // extra methods


}
