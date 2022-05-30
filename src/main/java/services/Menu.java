package services;

import clientSide.Course;
import clientSide.Group;
import clientSide.Professor;
import clientSide.Student;
import extras.Util;
import interfaces.SetAccountInterface;
import repositories.CourseRepository;
import repositories.GroupRepository;
import repositories.ProfessorRepository;
import repositories.StudentRepository;
import services.config.DatabaseConfiguration;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Scanner;
import java.util.Set;

public class Menu {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// INITIALIZATION CLASS MENU ////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Menu instance;
    private final Scanner sc;
    private boolean init;

    private ArrayList<Student> studentsList;
    private ArrayList<Professor> professorsList;
    private Set<Course> coursesList;
    private ArrayList<Group> groupsList;

    private Menu(){
        databaseInit();
        sc = new Scanner(System.in);
        init = false;
        reportsFilesInit();
    }

    public static Menu getInstance(){
        if(instance == null)
            instance = new Menu();
        return instance;
    }

    public void reportsFilesInit() {
        CsvReports.createReport("PROFESSOR","GROUP","ReportsProfessorsForAGroup");
        CsvReports.createReport("GROUP","COURSE","ReportsGroupsInACourse");
        CsvReports.createReport("STUDENT","PASSED COURSE","ReportsPassedStudents");
        CsvReports.createReport("STUDENT","FAILED COURSE","ReportsFailedStudents");
        CsvReports.createReport("STUDENT","ENROLLED IN","ReportsStudentsInACourse");
        CsvReports.createReport("STUDENT","NOT GRADED IN","ReportsStudentsNotMarked");
    }

    public void databaseInit() {
        StudentRepository studentRepository = StudentRepository.getInstance();
        ProfessorRepository professorRepository = ProfessorRepository.getInstance();
        CourseRepository courseRepository = CourseRepository.getInstance();
        GroupRepository groupRepository = GroupRepository.getInstance();

        studentRepository.createTable();
        professorRepository.createTable();
        courseRepository.createTable();
        groupRepository.createTable();

        DatabaseConfiguration.closeDatabaseConnection();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////// TERMINATION CLASS MENU //////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void terminateProgram() {
        // End program message
        System.out.println
                (
                    "\t\t\tThank you for your time! \n" +
                            "\t\t\t\tSee you soon!"
                );
        // close connections
        sc.close();
        DatabaseConfiguration.closeDatabaseConnection();
        // end program
        System.exit(0);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////// PRINT MENUS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
    // main menu options printed
    public void startMenu() {

        if(!init) {
            this.professorsList = ProfessorRepository.getInstance().getProfessorsList();
            this.coursesList = CourseRepository.getInstance().getCourseList();
            this.studentsList = StudentRepository.getInstance().getStudentsList();
            this.groupsList = GroupRepository.getInstance().getGroupList();
            init = true;
        }
        MenuOptions.menuOptions();
        menuOptions();
    }

    // student menu print
    private void printStudentMenuOptions(Student student)  {
        MenuOptions.studentMenuOptions(student.getName());
        studentMenuOptions(student);
    }

    // professor menu print
    private void printProfessorMenuOptions(Professor professor)  {
        MenuOptions.professorMenuOptions(professor.getName());
        professorMenuOptions(professor);
    }

    // reports menu print
    public void printReportsMenuOptions()  {
        // the files were created when the menu is initialized;
        MenuOptions.reportsMenuOptions();
        reportsMenuOptions();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////// MENUS OPTIONS /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // main menu options choices
    public void menuOptions()  {
        int choice = Util.validationChoice(sc);

        switch (choice) {
            case 1:
                // create group
                MenuOptions.createOrRemove("group");
                int createOrRemoveGroup = Util.validationChoice(sc);
                if(createOrRemoveGroup==1) {
                    createGroup();
                }else if(createOrRemoveGroup==2) {
                    Group group = selectGroup();
                    removeGroup(group);
                }
                break;
            case 2:
                // create new student
                MenuOptions.createOrRemove("student");
                int createOrRemoveStudent = Util.validationChoice(sc);
                if(createOrRemoveStudent==1) {
                    createStudentOrProfessor(new Student(), true);
                }else if(createOrRemoveStudent==2) {
                    Student student = selectStudent();
                    removeStudent(student);
                }
                break;
            case 3:
                // create new professor
                MenuOptions.createOrRemove("professor");
                int createOrRemoveProfessor = Util.validationChoice(sc);
                if(createOrRemoveProfessor==1) {
                    createStudentOrProfessor(new Professor(), false);
                }else if(createOrRemoveProfessor==2) {
                    Professor professor = selectProfessor();
                    removeProfessor(professor);
                }
                break;
            case 4:
                // create course
                MenuOptions.createOrRemove("course");
                int createOrRemoveCourse = Util.validationChoice(sc);
                if(createOrRemoveCourse==1) {
                    createCourse();
                }else if(createOrRemoveCourse==2) {
                    Course course = selectCourse();
                    removeCourse(course);
                }
                break;
            case 5:
                // if there are students in the system -> print student list and type their name
                // (done in selectStudent)
                Student student = selectStudent();
                if(student!=null)
                    printStudentMenuOptions(student);
                break;
            case 6:
                // if there are professors in the system -> print professors list and type their name
                // (done in selectProfessor)
                Professor professor = selectProfessor();
                    if (professor != null)
                        printProfessorMenuOptions(professor);
                break;
            case 7:
                // reports menu
                printReportsMenuOptions();
                break;
            case 8:
                // end program
                terminateProgram();
                break;
            default:
                // invalid choice -> outside range <1-7>
                System.out.println("You introduced an invalid choice, please try again: ");
                break;
        }
        // print main menu options
        startMenu();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// STUDENT MENU OPTIONS /////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // student menu choices
    private void studentMenuOptions(Student student)  {
        int choice = Util.validationChoice(sc);

        boolean checkIfWeLeave = false; // check if we go to the previous menu or not

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
                     group1.getStudentsList().forEach(student1 -> {
                         if(student1.getID()==student.getID())
                             System.out.println("Group: "
                                     + group1.getName());
                     });
                 }
                 break;
             case 4:
                 // print the group students, including this student
                 try {
                     for (Group group1 : this.getGroupsList()) {
                         group1.getStudentsList().forEach(student1 -> {
                             if (student1.getID() == student.getID())
                                 System.out.println("Students in group "
                                         + group1.getName()
                                         + ": \n "
                                         + group1.printStudentsList("\t", "\n"));
                         });
                     }
                 }catch (ConcurrentModificationException e) {
                     // empty catch
                 }
                 break;
             case 5:
                 // print the failed classes - if any
                 StringBuilder failedCourses = new StringBuilder();
                 for (Course course1 : student.getCourses()) {
                     int grade = student.getCoursesList().get(course1);
                     if (grade < 5 && grade != 0) {

                         failedCourses
                                 .append("\t")
                                 .append(course1.getName())
                                 .append(": ")
                                 .append(grade)
                                 .append("\n");
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
                 checkIfWeLeave = true;
                 break;
             default:
                 System.out.println("You introduced an invalid choice, please try again: ");
                 break;
         }
         if(!checkIfWeLeave)
             printStudentMenuOptions(student);
         else
             startMenu();

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// PROFESSOR MENU OPTIONS //////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // professor menu choices
    private void professorMenuOptions(Professor professor)  {

        int choice = Util.validationChoice(sc);

        boolean checkIfWeLeave = false; // check if we go out of this menu or not

        switch (choice) {
            case 1:
                // print courses the professor teaches
                professor.printCourses();
                break;
            case 2:
                // mark the students in a specific course
                System.out.println("Insert the name of the course you want to mark: ");
                professor.printCourses();

                String courseName = sc.nextLine();

                for (Course course1 : professor.getCourses()) {

                    if (course1.getName().equalsIgnoreCase(courseName)) {

                        System.out.println("Course selected: " + courseName);

                        for (Student student1 : this.getStudentsList()) {
                            for(Course course2 : student1.getCourses()) {
                                if (course2.getID() == course1.getID()) {
                                    System.out.println("Insert the grade for " + student1.getName() + ": ");

                                    // read the grade
                                    int grade = sc.nextInt();
                                    sc.nextLine();

                                    // mark the course
                                    professor.mark(student1, course1, grade);

                                    // update objects
                                    student1.updateDB();
                                    professor.updateDB();
                                }
                            }
                        }
                        }
                    }
                break;
            case 3:
                // print groups enrolled in a course

                StringBuilder stringToPrint = new StringBuilder();
                for (Course course : professor.getCourses()) {
                    stringToPrint.append("Groups that are enrolled in ")
                            .append(course.getName())
                            .append("\n");
                    for (Group group : this.getGroupsList()) {
                        group.getCoursesList().forEach(course2 -> {
                            if(course2.getID()==course.getID())
                                stringToPrint
                                        .append("\t")
                                        .append(group.getName())
                                        .append("\n");
                        });
                    }
                }
                System.out.println(stringToPrint);
                break;
            case 4:
                checkIfWeLeave = true;
                break;
            default:
                System.out.println("You introduced an invalid choice, please try again.");
                break;
        }
        if(!checkIfWeLeave)
            printProfessorMenuOptions(professor);
        else
            startMenu();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////// REPORTS MENU OPTIONS  //////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void reportsMenuOptions()  {

        int choice = Util.validationChoice(sc);
        boolean checkIfWeLeave = false;

        switch (choice) {
            case 1:
                // professors names for a specific group
                String nameFileRepProfGroup = "ReportsProfessorsForAGroup";

                // select group from list
                Group groupRepProf = selectGroup();
                if(groupRepProf!=null) {
                    groupRepProf.getProfessorsList().forEach
                            (professor1 -> CsvReports.writeToReport
                                    (professor1.getName(), groupRepProf.getName(), nameFileRepProfGroup));

                    CsvReports.readDataFromReport(nameFileRepProfGroup);
                    CsvReports.endOfReport(nameFileRepProfGroup);
                }
                break;
            case 2:
                // groups that are enrolled in a specific course
                String nameFileRepGroupCourse = "ReportsGroupsInACourse";

                System.out.println("Select the course:");
                printCoursesList();

                System.out.print("Insert the name of the course: ");
                String courseName = sc.nextLine().toUpperCase();

                groupsList.forEach(courseRepGroup -> {
                    courseRepGroup.getCoursesList().forEach
                            (course1 -> {
                                if(course1.getName().equalsIgnoreCase(courseName)) {
                                    CsvReports.writeToReport
                                            (courseRepGroup.getName(), courseName, nameFileRepGroupCourse);
                                }
                            });
                });
                CsvReports.readDataFromReport(nameFileRepGroupCourse);
                CsvReports.endOfReport(nameFileRepGroupCourse);
                break;
            case 3:
                // all students that passed a course + the name of the course
                String nameFileRepPassStudents = "ReportsPassedStudents";

                coursesList.forEach(course1 -> {
                    studentsList.forEach
                            (student1 -> {
                                if(student1.isCourseInList(course1) && student1.getGrade(course1) >= 5) {
                                    CsvReports.writeToReport
                                            (student1.getName(), course1.getName() + " - " + student1.getGrade(course1), nameFileRepPassStudents);
                                }
                            });
                });
                CsvReports.readDataFromReport(nameFileRepPassStudents);
                CsvReports.endOfReport(nameFileRepPassStudents);
                break;

            case 4:
                // all students that failed a course +  the name of the course
                String nameFileRepFailStudents = "ReportsFailedStudents";

                coursesList.forEach( course1 -> {
                    studentsList.forEach
                            (student1 -> {
                                if (student1.isCourseInList(course1)
                                        && student1.getGrade(course1) > 0
                                        && student1.getGrade(course1) < 5) {
                                    CsvReports.writeToReport
                                            (student1.getName(), course1.getName() + " - " + student1.getGrade(course1), nameFileRepFailStudents);
                                }
                            });
                });
                CsvReports.readDataFromReport(nameFileRepFailStudents);
                CsvReports.endOfReport(nameFileRepFailStudents);
                break;
            case 5:
                // students enrolled in a specific course
                String nameFileRepCourseStudents = "ReportsStudentsInACourse";

                Course courseRepStudents = selectCourse();
                if(courseRepStudents!=null) {
                    studentsList.forEach(student1 -> {
                        if (student1.isCourseInList(courseRepStudents)) {
                            CsvReports.writeToReport
                                    (student1.getName(), courseRepStudents.getName(), nameFileRepCourseStudents);
                        }
                    });

                    CsvReports.readDataFromReport(nameFileRepCourseStudents);
                    CsvReports.endOfReport(nameFileRepCourseStudents);
                }
                break;
            case 6:
                // all students without grades
                String nameFileRepNotGraded = "ReportsStudentsNotMarked";

                coursesList.forEach(course1 -> {
                    for(Student student1 : studentsList)
                        if(student1.isCourseInList(course1)
                                && student1.getGrade(course1) == 0)
                        {
                            CsvReports.writeToReport
                                    (student1.getName(), course1.getName(), nameFileRepNotGraded);
                        }
                });
                CsvReports.readDataFromReport(nameFileRepNotGraded);
                CsvReports.endOfReport(nameFileRepNotGraded);
                break;
            case 7:
                checkIfWeLeave = true;
                break;
            default:
                System.out.println("You introduced an invalid choice, please try again.");
                break;
        }
        if(!checkIfWeLeave)
            printReportsMenuOptions();
        else
            startMenu();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////// SELECT OBJECT /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Student selectStudent(){
        if(studentsList.size() != 0) {
            System.out.println("Select student from list: ");
            this.printStudentsList();
            System.out.print("Insert the student's name: ");
            // retrieve the wanted student name
            String studentName = sc.nextLine();
            // go through the list of students and check if the student we wanted
            for (Student student1 : studentsList) {
                if (student1.getName().equalsIgnoreCase(studentName)) {
                    return student1;
                }
            }
        }
        else
            System.out.println("No students found in the system.");
        return null;
    }

    private Professor selectProfessor(){
        if(professorsList.size() != 0) {
            System.out.println("Select professor: ");
            this.printProfessorsList();
            System.out.print("Insert the professor's name: ");
            String professorName = sc.nextLine();
            for (Professor professor1 : professorsList) {
                if (professor1.getName().equalsIgnoreCase(professorName)) {
                    return professor1;
                }
            }
        }
        else
            System.out.println("No professors found in the system.");
        return null;
    }

    private Group selectGroup(){
        if(groupsList.size() != 0) {
            System.out.println("Select group from list: ");
            this.printGroupsList();
            System.out.print("Insert the group's number: ");
            // retrieve the wanted student name
            String groupName = sc.nextLine();
            // go through the list of students and check if the student we wanted
            for (Group group1 : groupsList) {
                if (group1.getName().equalsIgnoreCase(groupName)) {
                    return group1;
                }
            }
        }
        else
            System.out.println("No groups found in the system.");
        return null;
    }

    private Course selectCourse(){
        if(coursesList.size() != 0) {
            System.out.println("Select course from list: ");
            this.printCoursesList();
            System.out.print("Insert the course's name: ");
            // retrieve the wanted student name
            String courseName = sc.nextLine();
            // go through the list of students and check if the student we wanted
            for (Course course1 : coursesList) {
                if (course1.getName().equalsIgnoreCase(courseName)) {
                    return course1;
                }
            }
        }
        else
            System.out.println("No groups found in the system.");
        return null;
    }


    public String yesOrNo(){
        System.out.print("Yes/No: ");
        String choice = sc.nextLine();
        while(!choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("no")){
            System.out.println("The answer must be either \"yes\" or \"no\". \n" +
                    "Please type another answer: ");
            System.out.print("Please insert your choice: ");
            choice = sc.nextLine();
        }
        return choice;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// ADD OBJ BY CREATING //////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // GROUP
    private void createGroup() {
        Group group = new Group();
        if (!groupsList.contains(group)) {
            this.groupsList.add(group);
            GroupRepository.getInstance().addGroup(group);
        }
    }

    // STUDENT OR PROFESSOR

    // template method for creating a student or a professor, both of them implementing the
    // Interface SetAacountInterface
    // if we are creating a student -> the student will be assigned to an already created group

    private <T extends SetAccountInterface> T createStudentOrProfessor(T account, boolean isStudent)  {
        try {
            if (isStudent && groupsList.isEmpty()) {
                throw new Exception("Please create a group before creating a student account!");
            } else {
                System.out.println("Insert the name: ");
                account.setName(sc.nextLine());

                System.out.println("Insert the gender (M/F): ");
                account.setGender(sc.nextLine().toUpperCase());

                System.out.println("Insert the date of birth (DD-MM-YYYY): ");
                account.setDateOfBirth(sc.nextLine());

                if (isStudent) {

                    if (!studentsList.contains(account)) {
                        this.studentsList.add((Student) account);
                        StudentRepository.getInstance().addStudent((Student) account);
                    }

                    Group group1 = selectGroup();
                    if(group1!=null) {
                        group1.addStudent((Student) account);
                        group1.updateDB();

                    }
                } else {
                    if (!professorsList.contains((Professor) account)) {
                        this.professorsList.add((Professor) account);
                        ProfessorRepository.getInstance().addProfessor((Professor) account);
                    }
                }
                return account;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // COURSE

    // inserting  a name for the course
    // verifying if the professor list is empty or not, if it is, create a new professor for this course
    // if it s not empty -> print the professors list and then select the professor for this course

    private void createCourse()  {
        System.out.println("Insert the name of the course: ");

        String name = sc.nextLine().toUpperCase();

        Professor professorForCourse = null;
        // if there are professors in the system:
        if (!professorsList.isEmpty()) {

            System.out.println("Is the professor for this course already in the system? \n");
            String choice = yesOrNo();

            if (choice.equalsIgnoreCase("yes")) {
                printProfessorsList();
                System.out.println("Insert the full name of the professor: ");
                String profName = sc.nextLine();
                for (Professor professor : professorsList)
                    if (professor.getName().equalsIgnoreCase(profName)) {
                        professorForCourse = professor;
                    }
            } else if (choice.equalsIgnoreCase("no")){
                professorForCourse = this.createStudentOrProfessor(new Professor(), false);
            }
        } else {
            // if there are no professors in the system create a new one
            professorForCourse = this.createStudentOrProfessor(new Professor(), false);
        }

        // create course
        Course course = new Course(name);



        // assign the course to a specific group
        System.out.println("Select the group that will be enrolled in this course: ");
        // for now it only works for one group at a time
        printGroupsList();

        System.out.print("Insert the number of the group: ");
        String groupName = sc.nextLine();

        this.coursesList.add(course);
        CourseRepository.getInstance().addCourse(course);

        assert professorForCourse != null;
        professorForCourse.addCourse(course);
        professorForCourse.updateDB();


        boolean correctName = false;
        while(!correctName){
            for(Group group1 : groupsList) {
                if (group1.getName().equalsIgnoreCase(groupName)) {
                    group1.addCourse(course);
                    group1.addProfessor(professorForCourse);
                    group1.updateDB();
                    correctName = true;
                }
            }
            if(!correctName) {
                System.out.println("You have inserted a wrong number. \n");
                System.out.print("Please insert the number of an existing group: ");
                groupName = sc.nextLine();
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// REMOVE OBJECTS /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void removeProfessor(Professor professor) {
        if (professorsList.contains(professor))
        {
            // if we delete a professor, we have to also delete the class that the professor teaches :D
            // delete professor from courses it teaches
            for(Course course : professor.getCourses())
            {
                removeCourse(course);
            }
            for(Group  group : groupsList)
            {
                group.removeProfessor(professor);
                group.updateDB();
            }

            // delete professor from professors list
            this.professorsList.remove(professor);
            int professorId = ProfessorRepository.getInstance().getIdByObject(professor);
            ProfessorRepository.getInstance().deleteProfessorById(professorId);
        }
    }

    private void removeStudent(Student student) {
        // remove student from the group list
        if (studentsList.contains(student)) {
            for(Group group:groupsList)
            {
                if(group.getStudentsList().contains(student)) {
                    group.removeStudent(student);
                    group.updateDB();
                }
            }
            // remove student from the students list
            this.studentsList.remove(student);
            int studentId = StudentRepository.getInstance().getIdByStudent(student);
            StudentRepository.getInstance().deleteStudentById(studentId);
        }
    }

    private void removeGroup(Group group) {
        if (groupsList.contains(group))
        {
            // remove group from the groups list implies also deleting all the students in the group
            this.groupsList.remove(group);

            group.getStudentsList().forEach(student -> {
                studentsList.remove(student);
                int studentId = StudentRepository.getInstance().getIdByStudent(student);
                StudentRepository.getInstance().deleteStudentById(studentId);
            });

            int groupId = GroupRepository.getInstance().getIdByObject(group);
            GroupRepository.getInstance().deleteGroupById(groupId);
        }
    }

    private void removeCourse(Course course) {
        if (coursesList.contains(course)) {

            // remove it from the professor
            for(Professor professor : professorsList)
            {
                try {
                    professor.getCourses().forEach(course1 -> {
                        if (course1.getID() == course.getID()) {

                            professor.removeCourse(course1);
                            professor.updateDB();
                        }

                    });
                } catch(ConcurrentModificationException e) {
                    // empty catch
                }
            }

            groupsList.forEach(group -> {
                group.getCoursesList().forEach(course2 -> {
                    if(course2.getID()==course.getID())
                    {
                        group.removeCourse(course2);
                        group.updateDB();
                    }
                });
            });

            // delete course from courses list
            this.coursesList.remove(course);

            int courseId = CourseRepository.getInstance().getIdByCourse(course);
            CourseRepository.getInstance().deleteCourseById(courseId);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////// GET LISTS ////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////// SET LISTS ////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setStudentsList(ArrayList<Student> studentsList) {
        this.studentsList = studentsList;
    }

    private void setProfessorsList(ArrayList<Professor> professorsList) {
        this.professorsList = professorsList;
    }

    private void setCoursesList(Set<Course> coursesList) {
        this.coursesList = coursesList;
    }

    private void setGroupsList(ArrayList<Group> groupsList) {
        this.groupsList = groupsList;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////// PRINT LISTS //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void printProfessorsList() {
        System.out.println("Professors list: ");
        System.out.println(Util.arrayListToString(professorsList, "\t", "\n"));
    }

    private void printStudentsList() {
        System.out.println("Students list: ");
        System.out.println(Util.arrayListToString(studentsList,"\t", "\n"));
    }

    private void printGroupsList() {
        System.out.println("Groups list: ");
        System.out.println(Util.arrayListToString(groupsList,"\t", "\n"));
    }

    private void printCoursesList() {
        System.out.println("Courses list: ");
        System.out.println(Util.setToString(coursesList,"\t", "\n"));
    }

}
