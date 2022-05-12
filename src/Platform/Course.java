package Platform;

import Client.Professor;
import Extras.Generator;
import Interfaces.GetNameInterface;

public class Course implements GetNameInterface {
    private String name;
    private Professor professor;
    private boolean hasProfessor;

    // constructors

    public Course(String name) throws Exception {
        this.name = name;
        this.professor = Generator.professorGenerator();
        this.professor.addCourse(this);
    }

    public Course(String name, Professor professor) {
        this.name = name;
        this.professor = professor;
        this.professor.addCourse(this);
    }

    // mutators

    public void setName(String name) {
        this.name = name;
    }

    public void changeProfessor(Professor professorToBeSelected) {
        this.professor = professorToBeSelected;
        professorToBeSelected.addCourse(this);
    }

    // accessors

    public String getName() {
        return name;
    }

    public Professor getProfessor() {
        return professor;
    }

    // extra methods


}
