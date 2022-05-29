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
    private ArrayList<Student> studentsList = new ArrayList<>();
    private ArrayList<Professor> professorsList = new ArrayList<>();
    private Set<Course> coursesList = new HashSet<>();

    // STATIC
    private static int ID = 100;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CONSTRUCTORS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /// EMPTY CONSTRUCTOR FOR A GROUP THAT ONLY HAS A NAME WITH NO STUDENTS / PROFESSORS / COURSES
    public Group() {
        ArrayList<Group> listGroup = GroupRepository.getInstance().getGroupList();
        for(Group group : listGroup) {
            if(group.getName().equals(String.valueOf(ID))){
                ID++;
            }
        }
        this.currentID = ID;
        ID ++;
    }

    // CONSTRUCTOR USED IN THE GENERATOR CLASS FOR GENERATING A SPECIFIC NUMBER OF STUDENTS AND ADDING THEM IN THE GROUP
    public Group( ArrayList<Student> students) {
        ArrayList<Group> listGroup = GroupRepository.getInstance().getGroupList();
        for(Group group : listGroup) {
            if(group.getName().equals(String.valueOf(ID))){
                ID++;
            }
        }
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
            Course thisCourse = getCourseEnrolled(courseToBeAdded);
            // remove course from courses list
            if(thisCourse==null) {
                // add for group
                this.coursesList.add(courseToBeAdded);
                // add for students in the group
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
            Course thisCourse = getCourseEnrolled(courseToBeRemoved);
                // remove course from courses list
            if(thisCourse!=null){
                this.coursesList.remove(courseToBeRemoved);

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
        Student thisStudent = getStudentLearning(studentToBeAdded);
        if(thisStudent==null){
            this.studentsList.add(studentToBeAdded);
            this.getCoursesList().forEach(studentToBeAdded::addCourse);
        }
    }

    // REMOVE STUDENT
    public void removeStudent(Student studentToBeRemoved) {
        Student thisStudent = getStudentLearning(studentToBeRemoved);
        if(thisStudent!=null){
            this.studentsList.remove(studentToBeRemoved);
        }
    }

    public void addProfessor(Professor professorToBeAdded) {
        Professor thisProfessor = getProfessorTeaching(professorToBeAdded);
        if (thisProfessor == null) {
            this.professorsList.add(professorToBeAdded);
        }
    }

    public void removeProfessor(Professor professorToBeRemoved) {
        Professor thisProfessor = getProfessorTeaching(professorToBeRemoved);
        if(thisProfessor!=null) {
            this.professorsList.remove(thisProfessor);
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

    public Professor getProfessorTeaching(Professor professor){
        for (Professor professor1 : this.getProfessorsList()) {
            if (professor1.getID()==professor.getID())
                return professor1;
        }
        return null;
    }

    public Student getStudentLearning(Student student){
        for (Student student1 : this.getStudentsList()) {
            if (student1.getID()==student.getID())
                return student1;
        }
        return null;
    }

    public Course getCourseEnrolled(Course course){
        for (Course course1 : this.getCoursesList()) {
            if (course1.getID()==course.getID())
                return course1;
        }
        return null;
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
            coursesList.forEach(course -> {
                 if (course != null) {
                     if (stringBuilder.length() != 0)
                         stringBuilder.append(",");
                     stringBuilder
                             .append(CourseRepository.getInstance().getIdByCourse(course));
                 }
             });
        }
        else if(object instanceof Professor){
            professorsList.forEach(professor -> {
                 if (professor != null) {
                     if (stringBuilder.length() != 0)
                         stringBuilder.append(",");
                     stringBuilder
                             .append(ProfessorRepository.getInstance().getIdByObject(professor));
                 }
             });

        }else if(object instanceof Student) {
            this.studentsList.forEach(student -> {
                if (student != null) {
                    if (stringBuilder.length() != 0)
                        stringBuilder.append(",");
                    int idStudent = StudentRepository.getInstance().getIdByStudent(student);
                    if (idStudent != -1)
                        stringBuilder.append(idStudent);
                }
            });
        }
        return stringBuilder.toString();
    }

    public boolean isProfessorStillTeaching(Professor professor) {
        for(Professor professor1 : getProfessorsList()) {
            for(Course course : professor1.getCourses()) {
                if(getCourseEnrolled(course)!=null)
                    return true;
            }
        }
        return false;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// DATABASE //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDB() {
        int groupId = GroupRepository.getInstance().getIdByObject(this);
        GroupRepository.getInstance().updateGroup(this, groupId);
    }

}
