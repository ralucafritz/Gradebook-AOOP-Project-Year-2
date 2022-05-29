package extras;


import clientSide.Course;
import clientSide.Professor;
import clientSide.Student;
import interfaces.GetNameInterface;
import repositories.CourseRepository;
import repositories.ProfessorRepository;
import repositories.StudentRepository;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util  {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////// THIS CLASS's PURPOSE IS TO STORE METHODS THAT ARE USEFUL ALL AROUND THE PROJECT  ////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// DATA PROCESSING /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // stringToDate method transforms any birth date introduced as a string, into a variable of type Calendar(Date)
    public static Calendar stringToDate(String givenDate) {

        String[] arrDate = givenDate.split("-");
        int day, month, year;
        day = Integer.parseInt(arrDate[0]);
        month = Integer.parseInt(arrDate[1]);
        year = Integer.parseInt(arrDate[2]);
        Calendar returnDate = Calendar.getInstance();
        try {
            if (day <= 31 && day >= 1 && month >= 1 && month <= 12 && year >= 1960 && year <= returnDate.get(Calendar.YEAR)) {
                returnDate.set(Calendar.DATE, day);
                returnDate.set(Calendar.MONTH, month);
                returnDate.set(Calendar.YEAR, year);
            } else throw new Exception("WRONG DATE FORMAT OR DATE INPUT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    public static String dateToString(Date givenDate, boolean isTypeUS) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date = dateFormat.format(givenDate);
        return reverseYearAndDay(date, isTypeUS);
    }

    public static String reverseYearAndDay(String date, boolean isTypeUS) {
        String[] dateArr = date.split("-");
        if (isTypeUS && dateArr[0].length()==4) // if it needs year in the beginning
            return dateArr[0] + "-" + (Integer.parseInt(dateArr[1]) + 1) + "-" + dateArr[2];
        else if (isTypeUS && dateArr[2].length() == 4)
            return dateArr[2] + "-" + (Integer.parseInt(dateArr[1]) + 1) + "-" + dateArr[0];
        return dateArr[2] + "-" + (Integer.parseInt(dateArr[1]) + 1) + "-" + dateArr[0];
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////// VALIDATION DATA ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // gender validation method:
    // it checks if the gender introduced as a string is either M or F and then returns the enum Gender appropriate
    // for each case
    public static Gender genderStringValidation(String gender)  {
        try {
            if (gender.equals("M") || gender.equals("F")) {
                if(gender.equals("M"))
                    return Gender.M;
                else
                    return Gender.F;
            } else {
                throw new Exception("Invalid gender format");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Gender.Unknown;
    }

    public static int validationChoice(Scanner sc) {
        while(true)
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                return choice;
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("You have introduced a wrong value, please try again. ");
                System.out.print("Insert your choice: ");
            }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// CONVERSION LISTS TO STRING //////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // template method that creates a string from an arraylist and it results in a list of names
    public static <T extends GetNameInterface> StringBuilder arrayListToString(ArrayList<T> arrayList, String sep1, String sep2) {
        StringBuilder stringBuilder = new StringBuilder();

        arrayList.forEach( (item) -> {
            stringBuilder
                    .append(sep1)
                    .append(item.getName())
                    .append(sep2);
        });
        return stringBuilder;
    }

    // template method that creates a string from an set and it results in a list of names
    public static <T extends GetNameInterface> StringBuilder setToString(Set<T> set, String sep1, String sep2) {
        StringBuilder stringBuilder = new StringBuilder();

        set.forEach( (item) -> {
            stringBuilder
                    .append(sep1)
                    .append(item.getName())
                    .append(sep2);
        });
        return stringBuilder;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////// HELPFUL RANDOM METHODS ////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // get a random number for the generator classes
    public static int getRandomNumber(int max, int min)
    {
        return (int) Math.floor(Math.random() * (max - min +1) + min);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// CONVERSION STRINGS TO LISTS /////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                        /// NEEDS OPTIMIZATION !!!!!!!

    public static Set<Course> coursesListToSet(String coursesList){
        Set<Course> setToBeReturned = new HashSet<>();

        String[] courses = coursesList.split(","); // ex 1,2,3...

        for (String id : courses) {
            if(id.equals(""))
                continue;
            CourseRepository courseRepository = CourseRepository.getInstance();
            Course course = courseRepository.getObjectById(Integer.parseInt(id));

            setToBeReturned.add(course);
        }
        return setToBeReturned;
    }

    public static ArrayList<Student> studentsListToArrayList(String studentList) {
        ArrayList<Student> arrayListToBeReturned = new ArrayList<>();

        String[] students = studentList.split(","); // ex 1,2,3...

        for (String id : students) {
            if(id.equals(""))
                continue;
            StudentRepository studentRepository= StudentRepository.getInstance();
            Student student = studentRepository.getStudentById(Integer.parseInt(id));

            arrayListToBeReturned.add(student);
        }
        return arrayListToBeReturned;
    }

    public static ArrayList<Professor> professorsListToArrayList(String professorList)   {
        ArrayList<Professor> arrayListToBeReturned = new ArrayList<>();

        String[] professors = professorList.split(","); // ex 1,2,3...

        for (String id : professors) {
            if(id.equals(""))
                continue;
            ProfessorRepository professorRepository = ProfessorRepository.getInstance();
            Professor professor = professorRepository.getProfessorById(Integer.parseInt(id));

            arrayListToBeReturned.add(professor);
        }
        return arrayListToBeReturned;
    }
}
