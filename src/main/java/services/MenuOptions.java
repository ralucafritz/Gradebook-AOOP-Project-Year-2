package services;

public class MenuOptions {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////// THIS CLASS's PURPOSE IS TO STORE THE METHODS THAT PRINT THE MENU OPTIONS FOR EACH OF THE MENUS ////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void menuOptions(){
        System.out.println("\n" +
                "\t \t \t \t Welcome to the main menu! \n" +
                "\t For now, you have the following options: \n" +
                "\t \t 1. Create/Remove a Group, \n" +
                "\t \t 2. Create/Remove a Student, \n" +
                "\t \t 3. Create/Remove a Professor, \n" +
                "\t \t 4. Create/Remove a Course, \n" +
                "\t \t 5. Enter 'Student Menu', \n" +
                "\t \t 6. Enter 'Professor Menu', \n" +
                "\t \t 7. Enter 'Reports Menu', \n" +
                "\t \t 8. End the program. \n");
        System.out.print("Please select your choice: ");
    }

    public static void studentMenuOptions(String name) {
        System.out.println("\n" +
                "\t \t \t \t Welcome to the Student Menu - " + name + " ! \n" +
                "\t You have the following options: \n" +
                "\t \t 1. Show the current enrolled courses,\n" +
                "\t \t 2. Show the current enrolled courses with their grades,\n" +
                "\t \t 3. Show the group number,\n" +
                "\t \t 4. Show the group's students list,\n" +
                "\t \t 5. Show failed classes, (If the course is graded)\n" +
                "\t \t 6. Go to the previous menu. \n");
        System.out.print("Please select your choice: ");
    }

    public static void professorMenuOptions(String name){
        System.out.println("\n" +
                "\t \t \t \t Welcome to the Professor Menu - " + name + " ! \n" +
                "\t You have the following options:\n" +
                "\t \t 1. Show the current teaching courses,\n" +
                "\t \t 2. Mark grades for a course,\n" +
                "\t \t 3. Show the groups enrolled in your courses,\n" +
                "\t \t 4. Go to the previous menu. \n");
        System.out.print("Please select your choice: ");
    }

    public static void reportsMenuOptions() {
        System.out.println("\n" +
                "\t \t \t \t Welcome to the Reports Menu! \n" +
                "\t You have the following options:\n" +
                "\t \t 1. Return the professors names for a specific group,\n" +
                "\t \t 2. Return the group that are enrolled in a specific course" +
                                    "[only a group at a time can study a specific course],\n" +
                "\t \t 3. Return all the students that passed a course and the name of the course " +
                                    "(if the course has already been graded),\n" +
                "\t \t 4. Return all the students that failed a course and the name of the course " +
                                    "(if the course has already been graded),\n" +
                "\t \t 5. Return all the students enrolled in a specific course,\n" +
                "\t \t 6. Return all the students without grades, \n" +
                "\t \t 7. Go to the previous menu.\n");
        System.out.print("Please select your choice: ");
    }

    public static void createOrRemove(String objectType) {
        System.out.println("\n" +
                "\t \t \t \t Welcome to the creation of a " + objectType + "\n" +
                "\t You have the following options: \n" +
                "\t \t 1. Add a " + objectType + "\n" +
                "\t \t 2. Remove a " + objectType + "\n");
        System.out.print("Please select your choice: ");
    }
}
