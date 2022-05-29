package extras;

import clientSide.Professor;
import clientSide.Student;
import clientSide.Course;
import clientSide.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Generator {

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// THIS CLASS WAS CREATED FOR TESTING PURPOSES IN THE INITIAL PHASE WHEN I NEEDED TO TEST DIFFERENT METHODS AND MENU OPTIONS. //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // AT THE MOMENT NOTHING IS USED FROM THIS CLASS.

    // LIST THAT CONTAINS THE COURSE NAMES I SAVED FOR THE COURSE GENERATOR TO USE IN ORDER TO GENERATE COURSES
    private static final List<String> coursesNames = Arrays.asList(
            "Advanced Algorithms",
            "Advanced Object Orientated Programming",
            "Computer Networking",
            "Software Development Methods",
            "Foreign Languages",
            "Artificial Intelligence",
            "Operating Systems",
            "Statistics and Probabilities",
            "Databases");

    // NAME GENERATOR FROM `NAMES.TXT` FILE THAT TAKES 2 RANDOM NAMES FROM THE FILE TO CREATE A REALISTIC FIRST AND
    // LAST NAME

    public static String nameGenerator() {
        // the list of random names in names.txt are from
        // https://www.usna.edu/Users/cs/roche/courses/s15si335/proj1/files.php%3Ff=names.txt.html

        int randFirst;
        int randLast;
        String first = "";
        String last = "";
        try {
            File namesFile = new File( "names.txt");
            Scanner sc = new Scanner(namesFile);

            Path path = namesFile.toPath();
            try {
                long fileLen = Files.lines(path).count();


            // creating 2 random numbers for the first and last name
            randFirst = (int) (Math.random() * fileLen);
            randLast = (int) (Math.random() * fileLen);

            // testing to see if randFrist is identical to randLast and if the randLast is greater than 0 so that
            // we change the randLast number in order for the first and the last name to never be identical.
            if (randFirst == randLast && randLast > 0)
                randLast--;
            else if(randLast < 0)
                    randLast++;

            // scan through the first randFirst-1 numbers and read the randFirst number name
            for (int i = 0; i <= randFirst; i++)
                if(i != randFirst)
                {
                    sc.nextLine();
                }
                else
                {
                    first = sc.nextLine();
                }

            // resetting scanner to the beginning of the file
                sc = new Scanner(namesFile);

            // scan through the first randLast-1 numbers and read the randLast number name
            for (int i = 0; i <= randLast; i++)
                if(i != randLast)
                {
                    sc.nextLine();
                }
                else
                {
                    last = sc.nextLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (first + " " + last);
    }

    // DATE OF BIRTH GENERATOR
    public static String dateOfBirthGenerator(boolean isStudent) {
        // to be technically correct we will choose a day between 1 and 27
        int day =  Util.getRandomNumber(27,1);
        int month = Util.getRandomNumber(12,1);
        int minAge;

        if (isStudent)
            minAge = 18;
        else
            minAge = 25;

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // from 1960 to current year - minAge ( which can be 18 or 25)
        int year = Util.getRandomNumber(currentYear - minAge, 1960);

        String monthString, dayToString;

        // transform to format 'DD'
        if (day < 10)
            dayToString = "0" + day;
        else
            dayToString = "" + day;

        // transform to format 'MM'
        if (month < 10)
            monthString = "0" + month;
        else
            monthString = "" + month;

        return dayToString + "-" + monthString + "-" + year;
    }

    // STUDENT GENERATOR
    public static Student studentGenerator(){
        return new Student(nameGenerator(),randomGenderGenerator(), dateOfBirthGenerator(true));
    }

    // PROFESSOR GFNERATOR
    public static Professor professorGenerator()   {
        return new Professor(nameGenerator(), randomGenderGenerator(), dateOfBirthGenerator(false));
    }

    // RANGOM GENER GENERATOR
    public static Gender randomGenderGenerator() {
        return Gender.values()
                [
                        new Random().nextInt(Gender.values().length - 1)
                ];
    }

    // GROUP GENERATOR WITH A GIVEN NUMBER OF STUDENTS TO GENERATE AUTOMATICALLY
    public static Group groupGenerator(int numberStudents) {

        ArrayList<Student> studentsList =  new ArrayList<Student>();
        for (int i = 0; i < numberStudents; i++) {
            Student student = studentGenerator();
            studentsList.add(student);
        }
        return new Group(studentsList);
    }

    // COURSE GENERATOR
    public static Course courseGenerator(Professor professor) {
        int randIndex = Util.getRandomNumber(coursesNames.size() - 1, 0);
        String name = coursesNames.get(randIndex);

        return new Course(name, true, professor);
    }

}
