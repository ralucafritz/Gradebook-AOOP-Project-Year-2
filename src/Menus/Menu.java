package Menus;

import Client.Professor;
import Client.Student;
import Extras.Util;
import Interfaces.SetAccountInterface;
import Platform.Course;
import Platform.Group;

import java.util.*;

public class Menu {

    private ArrayList<Student> studentsList;
    private ArrayList<Professor> professorsList;
    private Set<Course> coursesList;
    private ArrayList<Group> groupsList;

    // constructor
    public Menu() {
        this.studentsList = new ArrayList<>();
        this.groupsList = new ArrayList<>();
        this.professorsList = new ArrayList<>();
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
                    System.out.println("Insert student name: (use 1 name only - 2 names-> bug)");
                    String studentName = scanner.next();
                    for (Student student1 : studentsList) {
                        if (student1.getName().equalsIgnoreCase(studentName)) {
                            printStudentMenuOptions(student1);
                        }
                    }
                }
                break;
            case 6:
                if(professorsList.size() != 0) {
                    System.out.println("Select professor: ");
                    this.printProfessorList();
                    System.out.println("Insert professor name: (use 1 name only - 2 names-> bug)");
                    String professorName = scanner.next();
                    for (Professor professor1 : professorsList) {
                        if (professor1.getName().equalsIgnoreCase(professorName)) {
                            printProfessorMenuOptions(professor1);
                        }
                    }
                }
                else
                    System.out.println("No professors found in the system.");
                break;
            case 7:
                System.exit(0);
                break;
            default:
                System.out.println("You introduced an invalid choice, please try again: ");
                break;
        }

        printMenuOptions();

    }

    private void printStudentMenuOptions(Student student) throws Exception {
        System.out.println("Welcome to the Student Menu! \n" +
                "You have the following options: \n" +
                "1. Show the current enrolled courses,\n" +
                "2. Show the current enrolled courses with their grades,\n" +
                "3. Show the group number,\n" +
                "4. Show the group's students list,\n" +
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
                 for (Group group1 : this.getGroupsList()) {
                     if (group1.getStudentsList().contains(student))
                         System.out.println(group1.getName());
                 }
                 break;
             case 4:
                 for (Group group1 : this.getGroupsList()) {
                     if (group1.getStudentsList().contains(student))
                         group1.printStudentsList();
                 }
                 break;
             case 5:
                 StringBuilder failedCourses = new StringBuilder();
                 for (Course course1 : student.getCourses()) {
                     int grade = student.getCoursesList().get(course1);
                     if (grade < 5 && grade != 0) {
                         failedCourses.append("\t").append(course1.getName().toUpperCase()).append(": ").append(grade).append("\n");
                     }
                 }
                 if (failedCourses.length() != 0) {
                     System.out.println(failedCourses);
                 } else
                     System.out.println("No failed classes yet, good job!");
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
        boolean check = true;
        switch (choice) {
            case 1:
                professor.printCourses();
                break;
            case 2:
                System.out.println("Insert the name of the course you want to mark: ");
                professor.printCourses();
                String courseName = scanner.next();
                for (Course course1 : professor.getCourses()) {
                    if (course1.getName().equalsIgnoreCase(courseName)) {
                        for (Student student1 : this.getStudentsList()) {
                            if (student1.getCourses().contains(course1)) {
                                System.out.println("Insert the grade for " + student1.getName() + ": ");
                                int grade = scanner.nextInt();
                                professor.mark(student1, course1, grade);
                            }
                        }
                    }
                }
                break;
            case 3:
                StringBuilder stringToPrint = new StringBuilder();
                for (Course course : professor.getCourses()) {
                    stringToPrint.append("Groups that are enrolled in ").append(course.getName()).append("\n");
                    for (Group group : this.getGroupsList()) {
                        if (group.getCoursesList().contains(course)) {
                            stringToPrint.append("\t").append(group.getName()).append("\n");
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
//                "6. Add course to a group. \n" +
//                "Please select your choice: ");
//          courseMenuOptions();
//    }
//
//    public void courseMenuOptions(int choice) {
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
            account.setGender(scanner.nextLine().toUpperCase());
            System.out.println("Insert the date of birth (DD-MM-YYYY): ");
            account.setDateOfBirth(scanner.nextLine());
            if (isStudent) {
                System.out.println("Which group will this student be assigned to? ");
                printGroupList();
                System.out.println("Insert group name: ");
                String groupName = scanner.nextLine();
                for (Group group1 : groupsList){
                    if(group1.getName().equalsIgnoreCase(groupName))
                        group1.addStudent((Student) account);
                }
                addStudent((Student) account);
            } else {
               addProfessor((Professor) account);
            }
            return account;
        }
    }

    // GROUP
    private void createGroup() {
        Group group = new Group();
        addGroup(group);
    }

    // COURSE
    private void createCourse() throws Exception {
        System.out.println("Insert the name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next().toUpperCase();

        Professor professorForCourse = new Professor();
        if (professorsList.size() != 0) {
            System.out.println("Is the professor for this course already in the system? \n");
            printProfessorList();
            System.out.println("Y/N");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Insert the full name of the professor: ");
                String profName = scanner.next();
                for (Professor professor : professorsList)
                    if (professor.getName().equalsIgnoreCase(profName)) {
                        professorForCourse = professor;
                    }
            } else {
                professorForCourse = this.createStudentOrProfessor(professorForCourse, false);
            }
        } else {
            professorForCourse = this.createStudentOrProfessor(professorForCourse, false);
        }
        Course course = new Course(name, professorForCourse);
        System.out.println("Select the group that will be enrolled in this course: "); // for now it only works for one group at a time
        printGroupList();
        System.out.println("Insert the name of the group: ");
        String groupName = scanner.next();
        for(Group group1 : groupsList) {
            if(group1.getName().equalsIgnoreCase(groupName))
                group1.addCourse(course);
        }

        addCourse(course);
    }

    ////// add existing
//
    private void addProfessor(Professor professor) {
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
//
//    ////// remove
//    private void removeProfesor(Professor professor) {
//        if (professorsList.contains(professor))
//            this.professorsList.remove(professor);
//
//    }
//
//    private void removeStudent(Student student) {
//        if (studentsList.contains(student))
//            this.studentsList.remove(student);
//    }
//
//    private void removeGroup(Group group) {
//        if (groupsList.contains(group))
//            this.groupsList.remove(group);
//    }
//
//    private void removeCourse(Course course) {
//        if (coursesList.contains(course))
//            this.coursesList.remove(course);
//    }

    // getters and setters
    private ArrayList<Student> getStudentsList() {
        return studentsList;
    }


    private ArrayList<Professor> getProfessorsList() {
        return professorsList;
    }

    private Set<Course> getCoursesList() {
        return coursesList;
    }

    private ArrayList<Group> getGroupsList() {
        return groupsList;
    }
//    SETTERS

//    private void setStudentsList(ArrayList<Student> studentsList) {
//        this.studentsList = studentsList;
//    }
//
//    private void setProfessorsList(ArrayList<Professor> professorsList) {
//        this.professorsList = professorsList;
//    }
//
//    private void setCoursesList(Set<Course> coursesList) {
//        this.coursesList = coursesList;
//    }
//
//    private void setGroupsList(ArrayList<Group> groupsList) {
//        this.groupsList = groupsList;
//    }

    // PRINTS
    private void printProfessorList() {
        System.out.println("Professors list: ");
        System.out.println(Util.arrayListToString(professorsList));
    }

    private void printStudentList() {
        System.out.println("Students list: ");
        System.out.println(Util.arrayListToString(studentsList));
    }

    private void printGroupList() {
        System.out.println("Groups list: ");
        System.out.println(Util.arrayListToString(groupsList));
    }

    private void printCourseList() {
        System.out.println("Courses list: ");
        System.out.println(Util.setToString(coursesList));
    }


}
