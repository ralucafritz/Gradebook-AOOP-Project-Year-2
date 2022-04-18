package Extras;


import Interfaces.GetNameInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class Util extends Exception {

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

    public static <T extends GetNameInterface> StringBuilder arrayListToString(ArrayList<T> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : arrayList) {
            stringBuilder.append("\t").append(item.getName()).append("\n");
        }

        return stringBuilder;
    }

    public static <T extends GetNameInterface> StringBuilder setToString(Set<T> set) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : set) {
            stringBuilder.append("\t").append(item.getName()).append("\n");
        }

        return stringBuilder;
    }

    public static int getRandomNumber(int max, int min)
    {
        return (int) Math.floor(Math.random() * (max - min +1) + min);
    }

}
