package extras;


import interfaces.GetNameInterface;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;


// UTIL CLASS
// USED FOR USEFUL METHODS USED ACROSS THE PROJECT

public class Util extends Exception {

    // stringToDate method transforms any birth date introduced as a string, into a variable of type Calendar(Date)
    public static Calendar stringToDate(String givenDate) throws Exception {

        String[] arrDate = givenDate.split("-");
        int day, month, year;
        day = Integer.parseInt(arrDate[0]);
        month = Integer.parseInt(arrDate[1]);
        year = Integer.parseInt(arrDate[2]);
        Calendar returnDate = Calendar.getInstance();
        if (day <= 31 && day >= 1 && month >= 1 && month <= 12 && year >= 1960 && year <= returnDate.get(Calendar.YEAR)) {
            returnDate.set(Calendar.DATE, day);
            returnDate.set(Calendar.MONTH, month);
            returnDate.set(Calendar.YEAR, year);
        } else throw new Exception("WRONG DATE FORMAT OR DATE INPUT");


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

// gender validation method:
    // it checks if the gender introduced as a string is either M or F and then returns the enum Gender appropriate
    // for each case
    public static Gender genderStringValidation(String gender) throws Exception {
        if (gender.equals("M") || gender.equals("F")) {
            if(gender.equals("M"))
                return Gender.M;
            else
                return Gender.F;
        } else {
            throw new Exception("Invalid gender format");
        }
    }

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

    // get a random number for the generator classes
    public static int getRandomNumber(int max, int min)
    {
        return (int) Math.floor(Math.random() * (max - min +1) + min);
    }

}
