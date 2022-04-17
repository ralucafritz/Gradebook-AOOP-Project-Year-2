package Extras;

import Client.*;
import Platform.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Generator {

    private static final String[] coursesNames = {"Advanced Algorithms","Advanced Object Orientated Programming",
            "Computer Networking", "Software Development Methods", "Foreign Languages", "Artificial Intelligence",
            "Operating Systems", "Statistics and Probabilities", "Databases"};

    public static String nameGenerator() throws Exception {
        // the list of random names in names.txt are from
        // https://www.usna.edu/Users/cs/roche/courses/s15si335/proj1/files.php%3Ff=names.txt.html

        int randFirst;
        int randLast;
        String first = " ";
        String last = " ";
        try {
            File namesFile = new File("names.txt");
            Scanner scanner = new Scanner(namesFile);

            Path path = namesFile.toPath();
            long fileLen = Files.lines(path).count();

            randFirst = (int) (Math.random() * fileLen);
            randLast = (int) (Math.random() * fileLen);

            if (randFirst == randLast && randLast > 0)
                randLast--;
            else
                randLast++;

            for (int i = 0; i <= randFirst - 1; i++)
                first = scanner.nextLine();

            // resetting scanner to the beginning of the file
            scanner = new Scanner(namesFile);

            for (int i = 0; i <= randLast - 1; i++)
                last = scanner.nextLine();

            scanner.close();

        } catch (FileNotFoundException e) {
            throw new Exception("ERROR: Invalid File" + e);
        }

        return (first + " " + last);
    }

    public static String dateOfBirthGenerator(int studentOrProfessor) {
        // to be technically correct we will choose a day between 1 and 27
        int day = (int) Math.floor(Math.random() * 10 + 1);
        int month = (int) Math.floor(Math.random() * 12 + 1);
        int minAge;
        if (studentOrProfessor == 0)
            minAge = 18;
        else
            minAge = 25;

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // from 1960 to current year - minAge ( which can be 18 or 25)
        int year = (int) Math.floor(Math.random() * ((currentYear - minAge) - 1960 + 1) + 1960);

        String monthString, dayToString;
        // transform to format DD
        if (day < 10)
            dayToString = "0" + day;
        else
            dayToString = "" + day;
        // transform to format MM
        if (month < 10)
            monthString = "0" + month;
        else
            monthString = "" + month;

        return dayToString + "-" + monthString + "-" + year;
    }

    public static Student studentGenerator() throws Exception {
        Gender gender = Gender.values()[new Random().nextInt(Gender.values().length)];
        return new Student(nameGenerator(),gender, dateOfBirthGenerator(0));
    }

    public static Professor professorGenerator() throws Exception {
        Gender gender = Gender.values()[new Random().nextInt(Gender.values().length)];
        return new Professor(nameGenerator(), gender, dateOfBirthGenerator(1));
    }

    public static Group groupGenerator() throws Exception {

        ArrayList<Student> studentsList =  new ArrayList<Student>();
        for (int i = 0; i < 30; i++) {
            Student student = studentGenerator();
            studentsList.add(student);
        }

        return new Group(studentsList);
    }

    public static Course courseGenerator() throws Exception {
        int randCredits = (int) Math.floor(Math.random() * 5 + 1);
        int randIndex = (int) Math.floor(Math.random() * (coursesNames.length));
        String name = coursesNames[randIndex];
        Professor professor = professorGenerator();

        return new Course(name, randCredits, professor);
    }

}
