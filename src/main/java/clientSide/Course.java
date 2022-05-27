package clientSide;

import extras.Generator;
import interfaces.GetNameInterface;

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

    public Course(String name, boolean hasProfessor,Professor professor) {
        this.name = name;
        this.professor = professor;
        this.hasProfessor = true;
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

    public boolean hasProfessor() {
        return hasProfessor;
    }

    // extra methods


}
