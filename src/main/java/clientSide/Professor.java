package clientSide;

import extras.Gender;
import extras.Util;
import repositories.CourseRepository;
import repositories.ProfessorRepository;

import java.util.HashSet;
import java.util.Set;

public class Professor extends Account{

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Set<Course> courses =  new HashSet<>();

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CONSTRUCTORS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /// EMPTY CONSTRUCTOR FOR A PROFESSOR
    public Professor()  {
        super();

    }

    // CONSTRUCTOR WITH PARAMETERS
    public Professor(String name, Gender gender, String dateOfBirth)  {
        super(name, gender, dateOfBirth);
    }

    // CONSTRUCTOR FOR LOADING DATA FROM THE DB
    public Professor(String name, String gender, String dateOfBirth, String courses, int currentId)  {
        super(name, gender, dateOfBirth);

        String[] courseIds = courses.split(","); // ex 1-10,2-20..

        for (String id : courseIds) {
            // ex str = 1,2,3...
            if(!id.isEmpty()) {
                CourseRepository courseRepository = CourseRepository.getInstance();
                Course course = courseRepository.getObjectById(Integer.parseInt(id));

                addCourse(course);
            }
        }
        setCurrentID(currentId);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// MUTATORS //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addCourse(Course courseToBeAdded){
        this.courses.add(courseToBeAdded);

    }

    public void removeCourse(Course courseToBeRemoved){
        this.courses.remove(courseToBeRemoved);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// ACCESSORS ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Set<Course> getCourses() {
        return courses;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// PRINTS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void accountInfo(){
        super.accountInfo();
    }

    public void printCourses() {
        if(!courses.isEmpty()) {
            System.out.println("\t List teaching courses: \n" +
                    Util.setToString(this.courses, "\t \t", "\n"));

        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////// EXTRA METHODS /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void mark(Student student, Course course, int grade)  {
        try {
            if (this == course.getProfessor() && this.courses.contains(course)) {
                student.setGrade(course, grade);
            } else
                throw new Exception(this.getName() + " is not teaching this course");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String returnCoursesList() {
        StringBuilder stringBuilder = new StringBuilder();
        CourseRepository courseRepository = CourseRepository.getInstance();
        for (Course course : courses) {
            if (stringBuilder.length() != 0)
                stringBuilder.append(",");
            stringBuilder.append(courseRepository.getIdByObject(course));
        }

        return stringBuilder.toString();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// DATABASE //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDB() {
        int professorId = ProfessorRepository.getInstance().getIdByObject(this);
        ProfessorRepository.getInstance().updateProfessor(this, professorId);
    }
}
