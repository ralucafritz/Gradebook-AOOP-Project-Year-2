package extras;

import clientSide.Professor;
import clientSide.Student;
import clientSide.Course;
import clientSide.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Generator {

    // this class was created for testing purposes in the initial phase when I needed to test different methods and
    // menu options.
    // at the moment nothing is used from this class.


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

    // name generator from `names.txt` file that takes 2 random names from the file to create a realistic first and
    // last name

    public static String nameGenerator() throws Exception {
        // the list of random names in names.txt are from
        // https://www.usna.edu/Users/cs/roche/courses/s15si335/proj1/files.php%3Ff=names.txt.html

        int randFirst;
        int randLast;
        String first = "";
        String last = "";
        try {
            File namesFile = new File("names.txt");
            Scanner scanner = new Scanner(namesFile);

            Path path = namesFile.toPath();
            long fileLen = Files.lines(path).count();

            // creating 2 random numbers for the first and last name
            randFirst = (int) (Math.random() * fileLen);
            randLast = (int) (Math.random() * fileLen);

            // testing to see if randFrist is identical to randLast and if the randLast is greater than 0 so that
            // we change the randLast number in order for the first and the last name to never be identical.
            if (randFirst == randLast && randLast > 0)
                randLast--;
            else
                randLast++;

            // scan through the first randFirst-1 numbers and read the randFirst number name
            for (int i = 0; i <= randFirst - 1; i++)
                if(i != randFirst)
                {
                    scanner.nextLine();
                }
                else
                {
                    first = scanner.nextLine();
                }

            // resetting scanner to the beginning of the file
            scanner = new Scanner(namesFile);

            // scan through the first randLast-1 numbers and read the randLast number name
            for (int i = 0; i <= randLast - 1; i++)
                if(i != randLast)
                {
                    scanner.nextLine();
                }
                else
                {
                    last = scanner.nextLine();
                }

            scanner.close();

        } catch (FileNotFoundException e) {
            throw new Exception("ERROR: Invalid File" + e);
        }

        return (first + " " + last);
    }

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

    public static Student studentGenerator() throws Exception {
        return new Student(nameGenerator(),randomGenderGenerator(), dateOfBirthGenerator(true));
    }

    public static Professor professorGenerator() throws Exception {
        return new Professor(nameGenerator(), randomGenderGenerator(), dateOfBirthGenerator(false));
    }

    public static Gender randomGenderGenerator() {
        return Gender.values()
                [
                        new Random().nextInt(Gender.values().length - 1)
                ];
    }

    public static Group groupGenerator(int numberStudents) throws Exception {

        ArrayList<Student> studentsList =  new ArrayList<Student>();
        for (int i = 0; i < numberStudents; i++) {
            Student student = studentGenerator();
            studentsList.add(student);
        }
        return new Group(studentsList);
    }

    public static Course courseGenerator() throws Exception {
        int randCredits = Util.getRandomNumber(5,1);
        int randIndex = Util.getRandomNumber(coursesNames.size() - 1, 0);
        String name = coursesNames.get(randIndex);
        Professor professor = professorGenerator();

        return new Course(name, true, professor);
    }

}
