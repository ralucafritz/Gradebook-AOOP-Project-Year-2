package Platform;

import Client.Professor;
import Interfaces.NameInterface;

public class Course implements NameInterface {
    private String name;
    private int credits;
    private Professor professor;

    // constructors

    public Course(String name, int credits, Professor professor) {
        this.name = name;
        this.credits = credits;
        this.professor = professor;
        professor.addCourse(this);
    }

    // mutators

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void changeProfessor(Professor professorToBeSelected) {
        this.professor = professorToBeSelected;
        professorToBeSelected.addCourse(this);
    }

    // accessors

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public Professor getProfessor() {
        return professor;
    }

    // extra methods


}
