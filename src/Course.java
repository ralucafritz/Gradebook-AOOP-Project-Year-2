public class Course implements NamableForNow{
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

    // extra methods

    @Override
    public String toString() {
        return "Course name: " + this.name + "\n" +
                "Professor name: " + this.professor.getName();
    }
}