package Services;

import Client.Professor;
import Client.Student;
import Extras.Util;
import Interfaces.SetAccountInterface;
import Platform.Course;
import Platform.Group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Menu {


    private ArrayList<Student> studentsList;
    private ArrayList<Professor> professorsList;
    private Set<Course> coursesList;
    private ArrayList<Group> groupsList;

    private boolean checkCreation = false;


    // constructor with empty arraylists
    public Menu() {
        this.studentsList = new ArrayList<>();
        this.groupsList = new ArrayList<>();
        this.professorsList = new ArrayList<>();
        this.coursesList = new HashSet<>();
    }

    // main menu options printed
    public void printMenuOptions() throws Exception {
        System.out.println("Welcome to the main menu! \n" +
                "For now, you have the following options: \n" +
                "1. Create a Group, \n" +
                "2. Create a Student, \n" +
                "3. Create a Professor, \n" +
                "4. Create a Course, \n" +
                "5. Enter 'Student Menu', \n" +
                "6. Enter 'Professor Menu', \n" +
                "7. Enter 'Reports Menu', \n" +
                "8. End the program. \n" +
                "Please select your choice: ");
        menuOptions();
    }

    // main menu options choices
    public void menuOptions() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                // create group
                createGroup();
                break;
            case 2:
                // create new student
                Student student = new Student();
                createStudentOrProfessor(student, true);
                break;
            case 3:
                // create new professor
                Professor professor = new Professor();
                createStudentOrProfessor(professor, false);
                break;
            case 4:
                // create course
                createCourse();
                break;
            case 5:
                // if there are students in the system -> print student list and type their name
                if(studentsList.size() != 0) {
                    System.out.println("Select student: ");
                    this.printStudentList();
//                    System.out.println("Insert student name: ");
                    String studentName = scanner.nextLine();
                    for (Student student1 : studentsList) {
                        if (student1.getName().equalsIgnoreCase(studentName)) {
                            printStudentMenuOptions(student1);
                        }
                    }
                }
                else
                    System.out.println("No students found in the system.");
                break;
            case 6:
                // if there are professors in the system -> print professors list and type their name
                if(professorsList.size() != 0) {
                    System.out.println("Select professor: ");
                    this.printProfessorList();
//                    System.out.println("Insert professor name: ");
                    String professorName = scanner.nextLine();
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
                printReportsMenuOptions();
                break;
            case 8:
                // End program message
                System.out.println("See you soon!");
                // leave program
                scanner.close();
                System.exit(0);
                break;
            default:
                // invalid choice -> outside range <1-7>
                System.out.println("You introduced an invalid choice, please try again: ");
                break;
        }

        // print main menu options
        printMenuOptions();

    }

    // student menu print
    private void printStudentMenuOptions(Student student) throws Exception {
        System.out.println("Welcome to the Student Menu - " + student.getName() + " ! \n" +
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

    // student menu choices
    private void studentMenuOptions(Student student) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        boolean check = true;
        switch (choice) {
             case 1:
                 // print the courses the student is enrolled in
                 student.printCourses();
                 break;
             case 2:
                 // print the courses the student is enrolled in ++ their grades
                 student.coursesAndGrades();
                 break;
             case 3:
                 // print the group name
                 for (Group group1 : this.getGroupsList()) {
                     if (group1.getStudentsList().contains(student))
                         System.out.println(group1.getName());
                 }
                 break;
             case 4:
                 // print the group students, including this student
                 for (Group group1 : this.getGroupsList()) {
                     if (group1.getStudentsList().contains(student))
                         group1.printStudentsList();
                 }
                 break;
             case 5:
                 // print the failed classes - if any
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
                 // if 6  -> print main menu options
                 // if not -> print student menu potions
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

    // professor menu print
    private void printProfessorMenuOptions(Professor professor) throws Exception {
        System.out.println("Welcome to the Professor Menu - " + professor.getName() + " ! \n" +
                "You have the following options:\n" +
                "1. Show the current teaching courses,\n" +
                "2. Mark grades for a course,\n" +
                "3. Show the groups enrolled in your courses,\n" +
                "4. Go to the previous menu. \n" +
                "Please select your choice: ");
        professorMenuOptions(professor);
    }
    // professor menu choices
    private void professorMenuOptions(Professor professor) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        boolean check = true;
        switch (choice) {
            case 1:
                // print courses the professor teaches
                professor.printCourses();
                break;
            case 2:
                // mark the students in a specific course
                System.out.println("Insert the name of the course you want to mark: ");
                professor.printCourses();
                String courseName = scanner.nextLine();
                for (Course course1 : professor.getCourses()) {
                    if (course1.getName().equalsIgnoreCase(courseName)) {
                        System.out.println("Course selected: " + courseName);
                        for (Student student1 : this.getStudentsList()) {
                            if (student1.getCourses().contains(course1)) {
                                System.out.println("Insert the grade for " + student1.getName() + ": ");
                                int grade = scanner.nextInt();
                                scanner.nextLine();
                                professor.mark(student1, course1, grade);
                            }
                        }
                    }
                }
                break;
            case 3:
                // print groups enrolled in a course
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

    public void printReportsMenuOptions() throws Exception {
        // create or check if created report files
        // menu private variable that changes to true
        // removed so that the check is made before every possible report in case one of the files gets deleted in
        // the meantime

//        if(!checkCreation){
//            CsvReports.createReport("PROFESSOR","GROUP","ReportsProfessorsForAGroup");
//            CsvReports.createReport("GROUP","COURSE","ReportsGroupsInACourse");
//            CsvReports.createReport("STUDENT","PASSED COURSE","ReportsPassedStudents");
//            CsvReports.createReport("STUDENT","FAILED COURSE","ReportsFailedStudents");
//            CsvReports.createReport("STUDENT","ENROLLED IN","ReportsStudentsInACourse");
//            CsvReports.createReport("STUDENT","NOT GRADED IN","ReportsStudentsNotMarked");
//            checkCreation = true;
//        }

        System.out.println("Welcome to the Reports Menu! \n" +
                "You have the following options:\n" +
                "1. Return the professors names for a specific group,\n" +
                "2. Return the groups that are enrolled in a specific course,\n" +
                "3. Return the students that passed a course, and the name of the course (if the course has already been graded),\n" +
                "4. Return the students that failed a course, and the name of the course (if the course has already been graded),\n" +
                "5. Return the students enrolled in a specific course,\n" +
                "6. Return the students without grades, \n" +
                "7. Go to the previous menu. \n" +
                "Please select your choice: ");
          reportsMenuOptions();
    }

    public void reportsMenuOptions() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        boolean check = true;
        String nameFile = "";
        switch (choice) {
            case 1:
                // professors names for a specific group
                CsvReports.createReport("PROFESSOR","GROUP","ReportsProfessorsForAGroup");
                nameFile = "ReportsProfessorsForAGroup";
                System.out.println("Select the group:");
                printGroupList();
                String groupName = scanner.nextLine();
                for(Group group1 : groupsList)
                {
                    if(group1.getName().equalsIgnoreCase(groupName))
                    {
                        String finalNameFile = nameFile;
                        group1.getProfessorsList().forEach(professor1 ->
                                CsvReports.createReport(professor1.getName(), groupName, finalNameFile));
                    }
                }
                CsvReports.readDataFromReport(nameFile);
                break;
            case 2:
                // groups that are enrolled in a specific course
                nameFile += "ReportsGroupsInACourse";
                CsvReports.createReport("GROUP","COURSE",nameFile);
                System.out.println("Select the course:");
                printCourseList();
                String courseName = scanner.nextLine();

                for(Group group1: groupsList)
                {
                    String finalNameFile1 = nameFile;
                    group1.getCoursesList().forEach(course1 -> {
                        if(course1.getName().equalsIgnoreCase(courseName)){
                            CsvReports.createReport(group1.getName(), courseName, finalNameFile1);
                        }
                    });
                }
                CsvReports.readDataFromReport(nameFile);
                break;
            case 3:
                // students that passed a course, and the name of the course
                nameFile += "ReportsPassedStudents";
                CsvReports.createReport("STUDENT","PASSED COURSE",nameFile);
                for(Course course1 : coursesList)
                {
                    for(Student student1 : studentsList)
                        if(student1.checkEnrolledCourse(course1) && student1.getGrade(course1) > 5)
                        {
                            CsvReports.writeToReport(student1.getName(), course1.getName(), nameFile);
                        }
                }
                CsvReports.readDataFromReport(nameFile);
                break;
            case 4:
                // students that failed a course, and the name of the course
                nameFile += "ReportsFailedStudents";
                CsvReports.createReport("STUDENT","FAILED COURSE",nameFile);

                for(Course course1 : coursesList)
                {
                    for(Student student1 : studentsList)
                        if(student1.checkEnrolledCourse(course1) && student1.getGrade(course1) > 0 && student1.getGrade(course1) < 5 )
                        {
                            CsvReports.writeToReport(student1.getName(), course1.getName(), nameFile);
                        }
                }
                CsvReports.readDataFromReport(nameFile);
                break;
            case 5:
                // students enrolled in a specific course
                nameFile = "ReportsStudentsInACourse";
                CsvReports.createReport("STUDENT","ENROLLED IN", nameFile);
                System.out.println("Select the course you want to check the enrolled students for: ");
                printCourseList();
                String nameCourse = scanner.nextLine();
                for(Course course1 : coursesList)
                {
                    if(course1.getName().equalsIgnoreCase(nameCourse))
                        for(Student student1 : studentsList)
                            if(student1.checkEnrolledCourse(course1))
                            {
                                CsvReports.writeToReport(student1.getName(), course1.getName(), nameFile);
                            }
                }
                CsvReports.readDataFromReport(nameFile);
                break;
            case 6:
                // students without grades
                nameFile =  "ReportsStudentsNotMarked";
                CsvReports.createReport("STUDENT","NOT GRADED IN",nameFile);
                for(Course course1 : coursesList)
                {
                    for(Student student1 : studentsList)
                        if(student1.checkEnrolledCourse(course1) && student1.getGrade(course1) == 0)
                        {
                            CsvReports.writeToReport(student1.getName(), course1.getName(), nameFile);
                        }
                }
                CsvReports.readDataFromReport(nameFile);
                break;
            case 7:
                check = false;
                break;
            default:
                System.out.println("You introduced an invalid choice, please try again.");
                break;
        }

        if(check)
            printReportsMenuOptions();
        else
            printMenuOptions();
    }


    // add by creating
    // STUDENT OR PROFESSOR

    // template method for creating a student or a professor, both of them implementing the Interface SetAacountInterface
    // if we are creating a student -> the student will be assigned to an already created group

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
    // inserting  a name for the course
    // verifying if the professor list is empty or not, if it is, create a new professor for this course
    // if it s not empty -> print the professors list and then select the professor for this course
    private void createCourse() throws Exception {
        System.out.println("Insert the name of the course: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().toUpperCase();

        Professor professorForCourse = new Professor();
        if (professorsList.isEmpty()) {
            System.out.println("Is the professor for this course already in the system? \n");
            printProfessorList();
            System.out.println("Y/N");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Insert the full name of the professor: ");
                String profName = scanner.nextLine();
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
        String groupName = scanner.nextLine();
        for(Group group1 : groupsList) {
            if(group1.getName().equalsIgnoreCase(groupName))
                group1.addCourse(course);
        }

        addCourse(course);
    }

    ////// add already created  professors, students or groups to their lists
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
