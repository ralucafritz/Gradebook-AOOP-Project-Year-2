package clientSide;

import extras.Util;
import interfaces.GetNameInterface;
import repositories.CourseRepository;
import repositories.GroupRepository;
import repositories.ProfessorRepository;
import repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


public class Group implements GetNameInterface {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final int currentID;
    private ArrayList<Student> studentsList = new ArrayList<Student>();
    private ArrayList<Professor> professorsList = new ArrayList<Professor>();
    private Set<Course> coursesList = new HashSet<>();

    // STATIC
    private static int ID = 100;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CONSTRUCTORS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /// EMPTY CONSTRUCTOR FOR A GROUP THAT ONLY HAS A NAME WITH NO STUDENTS / PROFESSORS / COURSES
    public Group() {
        this.currentID = ID;
        ID ++;
    }

    // CONSTRUCTOR USED IN THE GENERATOR CLASS FOR GENERATING A SPECIFIC NUMBER OF STUDENTS AND ADDING THEM IN THE GROUP
    public Group( ArrayList<Student> students) {
        this.studentsList = students;
        this.currentID = ID;
        ID ++;
    }

    // CONSTRUCTOR FOR LOADING DATA FROM THE DB
    public Group(String name, String studentsList, String professorsList, String coursesList)  {
        this.currentID = Integer.parseInt(name);
        this.studentsList = Util.studentsListToArrayList(studentsList);
        this.professorsList = Util.professorsListToArrayList(professorsList);
        this.coursesList = Util.coursesListToSet(coursesList);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// MUTATORS //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addCourse(Course courseToBeAdded)  {
        try {
            if (!coursesList.contains(courseToBeAdded)) {
                this.coursesList.add(courseToBeAdded);
                this.professorsList.add(courseToBeAdded.getProfessor());
                for (Student student : studentsList) {
                    student.addCourse(courseToBeAdded);
                    student.updateDB();
                }
            } else {
                throw new Exception("This group is already enrolled in that course.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeCourse(Course courseToBeRemoved) {
        try {
            if (coursesList.contains(courseToBeRemoved)) {
                // remove course from courses list
                this.coursesList.remove(courseToBeRemoved);

                // remove the professor if the professor doesn't teach something else
                Professor professor = courseToBeRemoved.getProfessor();
                professor.removeCourse(courseToBeRemoved);

                boolean checkProfessorStatus = false;
                for(Course course: coursesList) {
                    // check if any other course has this professor
                    if(course.getProfessor().getID()==professor.getID())
                        checkProfessorStatus = true;
                    // if the professor is found as a professor for another class, leave the for block
                    if(checkProfessorStatus)
                        continue;
                }
                // if the professor doesn't teach any other class, it gets removed from the professorsList of this group
                if(!checkProfessorStatus)
                    this.professorsList.remove(professor);

                // remove course from the students of the group
                for (Student student : studentsList) {
                    student.removeCourse(courseToBeRemoved);
                    student.updateDB();
                }
            } else {
                throw new Exception("This group is not enrolled in that course.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ADD STUDENT
    public void addStudent(Student studentToBeAdded) {
        if(!studentsList.contains(studentToBeAdded)) {
            this.studentsList.add(studentToBeAdded);
        }
    }

    // REMOVE STUDENT
    public void removeStudent(Student studentToBeRemoved) {
        if(studentsList.contains(studentToBeRemoved)) {
            this.studentsList.remove(studentToBeRemoved);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// ACCESSORS ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// PRINTS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String printStudentsList(String sep1, String sep2) {
        this.sortStudentsByName();
       return Util.arrayListToString(this.studentsList, sep1, sep2).toString();
    }

    public String printProfessorList(String sep1, String sep2) {
        this.sortProfessorsByName();
        return Util.arrayListToString(this.professorsList, sep1, sep2).toString();
    }

    public String printCoursesList(String sep1, String sep2) {
        return Util.setToString(this.coursesList, sep1,sep2).toString();
    }

    public void sortStudentsByName() {
        this.studentsList.sort(Comparator.comparing(Student::getName));
    }

    public void sortProfessorsByName() {
        this.professorsList.sort(Comparator.comparing(Professor::getName));
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// RETURN LIST FROM OBJECT /////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // I KNOW THIS CODE IS SORT OF DUPLICATED BUT I REALLY COULDN'T THINK OF A WAY TO PREVENT THAT
    // THIS METHOD IS MEANT TO RETURN A LIST OF OBJECT DEPENDING ON THE TYPE OF OBJECT IT RECEIVES AS A PARAMETER

    public <T extends GetNameInterface> String returnList(T object) {
        StringBuilder stringBuilder = new StringBuilder();
        if(object instanceof Course) {

            CourseRepository courseRepository = CourseRepository.getInstance();
           coursesList.forEach(course -> {
                if (stringBuilder.length() != 0)
                    stringBuilder.append(",");
                stringBuilder
                        .append(courseRepository.getIdByObject(course));
           });
        }
        else if(object instanceof Professor){

            ProfessorRepository professorRepository= ProfessorRepository.getInstance();
            professorsList.forEach(professor -> {
                if (stringBuilder.length() != 0)
                    stringBuilder.append(",");
                stringBuilder
                        .append(professorRepository.getIdByObject(professor));
            });
        }else if(object instanceof Student) {

            StudentRepository studentRepository = StudentRepository.getInstance();
            studentsList.forEach(student -> {
                if(stringBuilder.length() != 0)
                    stringBuilder.append(",");
                stringBuilder.append(studentRepository.getIdByObject(student));
            });
        }
        return stringBuilder.toString();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// DATABASE //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDB() {
        int groupId = GroupRepository.getInstance().getIdByObject(this);
        GroupRepository.getInstance().updateGroup(this, groupId);
    }

}
