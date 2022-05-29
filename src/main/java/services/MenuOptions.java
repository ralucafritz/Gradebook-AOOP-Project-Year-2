package services;

public class MenuOptions {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////// THIS CLASS's PURPOSE IS TO STORE THE METHODS THAT PRINT THE MENU OPTIONS FOR EACH OF THE MENUS ////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void menuOptions(){
        System.out.println("Welcome to the main menu! \n" +
                "For now, you have the following options: \n" +
                "1. Create/Remove a Group, \n" +
                "2. Create/Remove a Student, \n" +
                "3. Create/Remove a Professor, \n" +
                "4. Create/Remove a Course, \n" +
                "5. Enter 'Student Menu', \n" +
                "6. Enter 'Professor Menu', \n" +
                "7. Enter 'Reports Menu', \n" +
                "8. End the program.");
        System.out.print("Please select your choice: ");
    }

    public static void studentMenuOptions(String name) {
        System.out.println("Welcome to the Student Menu - " + name + " ! \n" +
                "You have the following options: \n" +
                "1. Show the current enrolled courses,\n" +
                "2. Show the current enrolled courses with their grades,\n" +
                "3. Show the group number,\n" +
                "4. Show the group's students list,\n" +
                "5. Show failed classes, (If the course is graded)\n" +
                "6. Go to the previous menu.");
                System.out.print("Please select your choice: ");
    }

    public static void professorMenuOptions(String name){
        System.out.println("Welcome to the Professor Menu - " + name + " ! \n" +
                "You have the following options:\n" +
                "1. Show the current teaching courses,\n" +
                "2. Mark grades for a course,\n" +
                "3. Show the groups enrolled in your courses,\n" +
                "4. Go to the previous menu.");
        System.out.print("Please select your choice: ");
    }

    public static void reportsMenuOptions() {
        System.out.println("Welcome to the Reports Menu! \n" +
                "You have the following options:\n" +
                "1. Return the professors names for a specific group,\n" +
                "2. Return the groups that are enrolled in a specific course,\n" +
                "3. Return all the students that passed a course and the name of the course (if the course has already been graded),\n" +
                "4. Return all the students that failed a course and the name of the course (if the course has already been graded),\n" +
                "5. Return all the students enrolled in a specific course,\n" +
                "6. Return all the students without grades, \n" +
                "7. Go to the previous menu.");
        System.out.print("Please select your choice: ");
    }

    public static void createOrRemove(String objectType) {
        System.out.println("Welcome to the creation of a " + objectType + "\n" +
                "You have the following options: \n" +
                "1. Add a " + objectType + "\n" +
                "2. Remove a " + objectType);
        System.out.print("Please select your choice: ");
    }

}
