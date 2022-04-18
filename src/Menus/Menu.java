package Menus;

import Client.Professor;
import Client.Student;
import Extras.Util;
import Interfaces.SetAccountInterface;
import Platform.Course;
import Platform.Group;

import java.util.*;

public class Menu {

    private ArrayList<Student> studentsList = new ArrayList<Student>();
    private ArrayList<Professor> professorsList = new ArrayList<Professor>();
    private Set<Course> coursesList = new HashSet<>();
    private ArrayList<Group> groupsList = new ArrayList<Group>();

    // constructor
    public Menu() {
        this.studentsList = new ArrayList<Student>();
        this.groupsList = new ArrayList<Group>();
        this.professorsList = new ArrayList<Professor>();
        this.coursesList = new HashSet<>();
    }

    public void printMenuOptions() throws Exception {
        System.out.println("Welcome to the main menu! \n" +
                "For now, you have the following options: \n" +
                "1. Create a Group, \n" +
                "2. Create a Student, \n" +
                "3. Create a Professor, \n" +
                "4. Create a Course, \n" +
                "5. Enter 'Student Menu', \n" +
                "6. Enter 'Professor Menu', \n" +
                "7. End the program. \n" +
                "Please select your choice: ");
        menuOptions();
    }

    public void menuOptions() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        boolean check = true;
        switch (choice) {
            case 1:
                createGroup();
                break;
            case 2:
                Student student = new Student();
                createStudentOrProfessor(student, true);
                break;
            case 3:
                Professor professor = new Professor();
                createStudentOrProfessor(professor, false);
                break;
            case 4:
                createCourse();
                break;
            case 5:
                if(studentsList.size() != 0) {
                    System.out.println("Select student: ");
                    this.printStudentList();
                    System.out.println("Insert student name: ");
                    String studentName = scanner.next();
                    for (Student student1 : studentsList) {
                        if (student1.getName().equals(studentName)) {
                            printStudentMenuOptions(student1);
                        }
                    }
                }
                break;
            case 6:
                if(professorsList.size() != 0) {
                    System.out.println("Select professor: ");
                    this.printProfessorList();
                    System.out.println("Insert professor name: ");
                    String professorName = scanner.nextLine();
                    for (Professor professor1 : professorsList) {
                        if (professor1.getName().equals(professorName)) {
                            printProfessorMenuOptions(professor1);
                        }
                    }
                }
                else
                    System.out.println("No professors found.");
                break;
            case 7:
                check = false;
            default:
                System.out.println("You introduced an invalid choice, please try again: ");
                break;
        }
        if(check)
            printMenuOptions();
    }

    private void printStudentMenuOptions(Student student) throws Exception {
        System.out.println("Welcome to the Student Menu! \n" +
                "You have the following options: \n" +
                "1. Show the current enrolled courses,\n" +
                "2. Show the current enrolled courses with their grades,\n" +
                "3. Show the group number,\n" +
                "4. Show the group classmates,\n" +
                "5. Show failed classes, (If the course is graded)\n" +
                "6. Go to the previous menu. \n" +
                "Please select your choice: ");
        studentMenuOptions(student);
    }

    private void studentMenuOptions(Student student) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        boolean check = true;
        switch (choice) {
             case 1:
                 student.printCourses();
                 break;
             case 2:
                 student.coursesAndGrades();
                 break;
             case 3:
                 for (Group group : groupsList) {
                     if (group.getStudentsList().contains(student))
                         System.out.println(group.getName());
                 }
                 break;
             case 4:
                 for (Group group : groupsList) {
                     if (group.getStudentsList().contains(student))
                         group.printStudentsList();
                 }
                 break;
             case 5:
                 StringBuilder failedClases = new StringBuilder();
                 for (Course course : student.getCourses()) {
                     int grade = student.getCoursesList().get(course);
                     if (grade < 5 && grade != 0) {
                         failedClases.append("\t" + course.getName() + ": " + grade + "\n");
                     }
                 }
                 if (failedClases.length() != 0) {
                     System.out.println(failedClases);
                 } else
                     System.out.println("No failed classes. Good job!");
                 break;
             case 6:
                 check = false;
                 break;
             default:
                 System.out.println("You introduced an invalid choice, please try again: ");
                 break;
         }
         if(check)
             printStudentMenuOptions(student);
         else
             printMenuOptions();

    }

    private void printProfessorMenuOptions(Professor professor) throws Exception {
        System.out.println("Welcome to the Professor Menu! \n" +
                "You have the following options:\n" +
                "1. Show the current teaching courses,\n" +
                "2. Mark grades for a course,\n" +
                "3. Show the groups enrolled in your courses,\n" +
                "4. Go to the previous menu. \n" +
                "Please select your choice: ");
        professorMenuOptions(professor);
    }

    private void professorMenuOptions(Professor professor) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        boolean check = false;
        switch (choice) {
            case 1:
                professor.printCourses();
                break;
            case 2:
                System.out.println("Insert the name of the course you want to mark: ");
                String courseName = scanner.nextLine();
                for (Course course : professor.getCourses()) {
                    if (course.getName().equals(courseName)) {
                        for (Student student : this.getStudentsList()) {
                            if (student.getCourses().contains(course)) {
                                System.out.println("Insert the grade for " + student.getName() + ": ");
                                int grade = scanner.nextInt();
                                professor.mark(student, course, grade);
                            }
                        }
                    }
                }
                break;
            case 3:
                StringBuilder stringToPrint = new StringBuilder();
                for (Course course : professor.getCourses()) {
                    stringToPrint.append("Groups that are enrolled in " + course.getName() + "\n");
                    for (Group group : groupsList) {
                        if (group.getCoursesList().contains(course)) {
                            stringToPrint.append("\t" + group.getName() + "\n");
                        }
                    }
                }
                System.out.println(stringToPrint);
                break;
            case 4:
                check = false;
                break;
            default:
                System.out.println("You introduced an invalid choice, please try again.");
                break;
        }
        if(check)
            printProfessorMenuOptions(professor);
        else
            printMenuOptions();

    }

//      FUTURE IMPLEMENTATION
//    public void printCourseMenuOptions() {
//        System.out.println("Welcome to the Course Menu! \n" +
//                "You have the following options:\n" +
//                "1. Show the current professor,\n" +
//                "2. Show the groups enrolled in this course,\n" +
//                "3. Show the percentage of PASSED students (if the course has already been graded),\n" +
//                "4. Show the percentage of FAILED students (if the course has already been graded),\n" +
//                "5. Show a list with the FAILED students (if the course has been graded).\n" +
//                "Please select your choice: ");
//          courseMenuOptions();
//    }
//
//    public void courseMenuOptions(int choice) {
//
//
//    }

    // add by creating
    // STUDENT OR PROFESSOR
    private <T extends SetAccountInterface> T createStudentOrProfessor(T account, boolean isStudent) throws Exception {
        if (isStudent && groupsList.isEmpty()) {
            throw new Exception("Please create a group before creating a student account!");
        } else {
            System.out.println("Insert the name: ");
            Scanner scanner = new Scanner(System.in);
            account.setName(scanner.nextLine());
            System.out.println("Insert the gender (M/F): ");
            account.setGender(scanner.nextLine());
            System.out.println("Insert the date of birth (DD-MM-YYYY): ");
            account.setDateOfBirth(scanner.nextLine());
            if (isStudent) {
                this.studentsList.add((Student) account);
            } else {
                this.professorsList.add((Professor) account);
            }
            return account;
        }
    }

    // GROUP
    private Group createGroup() {
        Group group = new Group();
        this.groupsList.add(group);
        return group;
    }

    // COURSE
    private Course createCourse() throws Exception {
        System.out.println("Insert the name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        Professor professorForCourse = new Professor();
        if (professorsList.size() != 0) {
            System.out.println("Is the professor for this course already in the system? \n");
            printProfessorList();
            System.out.println("Y/N");
            String choice = scanner.nextLine();

            if (choice.toUpperCase().equals("N")) {
                System.out.println("Insert the full name of the professor: ");
                String profName = scanner.nextLine();
                for (Professor professor : professorsList)
                    if (professor.getName().equals(profName)) {
                        professorForCourse = professor;
                    }
            } else {
                professorForCourse = this.createStudentOrProfessor(professorForCourse, false);
            }

        } else {
            professorForCourse = this.createStudentOrProfessor(professorForCourse, false);
        }
        Course course = new Course(name, professorForCourse);
        coursesList.add(course);
        return course;
    }

    ////// add existing

    private void addProfesor(Professor professor) {
        if (!professorsList.contains(professor))
            this.professorsList.add(professor);
    }

    private void addStudent(Student student) {
        if (!studentsList.contains(student))
            this.studentsList.add(student);
    }

    private void addGroup(Group group) {
        if (!groupsList.contains(group))
            this.groupsList.add(group);
    }

    private void addCourse(Course course) {
        if (!coursesList.contains(course))
            this.coursesList.add(course);
    }

    ////// remove
    private void removeProfesor(Professor professor) {
        if (professorsList.contains(professor))
            this.professorsList.remove(professor);

    }

    private void removeStudent(Student student) {
        if (studentsList.contains(student))
            this.studentsList.remove(student);
    }

    private void removeGroup(Group group) {
        if (groupsList.contains(group))
            this.groupsList.remove(group);
    }

    private void removeCourse(Course course) {
        if (coursesList.contains(course))
            this.coursesList.remove(course);
    }


    // getters and setters
    private ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    private void setStudentsList(ArrayList<Student> studentsList) {
        this.studentsList = studentsList;
    }

    private ArrayList<Professor> getProfessorsList() {
        return professorsList;
    }

    private void setProfessorsList(ArrayList<Professor> professorsList) {
        this.professorsList = professorsList;
    }

    private Set<Course> getCoursesList() {
        return coursesList;
    }

    private void setCoursesList(Set<Course> coursesList) {
        this.coursesList = coursesList;
    }

    private ArrayList<Group> getGroupsList() {
        return groupsList;
    }

    private void setGroupsList(ArrayList<Group> groupsList) {
        this.groupsList = groupsList;
    }

    // PRINTS
    private void printProfessorList() {
        System.out.println(Util.arrayListToString(professorsList));
    }

    private void printStudentList() {
        System.out.println(Util.arrayListToString(studentsList));
    }

    private void printGroupList() {
        System.out.println(Util.arrayListToString(groupsList));
    }

    private void printCourseList() {
        System.out.println(Util.setToString(coursesList));
    }


}
