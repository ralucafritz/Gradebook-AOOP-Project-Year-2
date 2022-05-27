package clientSide;

import extras.Gender;
import extras.Util;
import interfaces.GetNameInterface;
import interfaces.SetAccountInterface;

import java.sql.Date;
import java.util.Calendar;

public abstract class Account implements GetNameInterface, SetAccountInterface {

    // instances fields

    private String name;
    private Gender gender;
    private Calendar dateOfBirth;

    // constructors

    public Account() throws Exception {
        this(null, Gender.Unknown, "01-01-1960");
    }

    public Account(String name, String dateOfBirth) throws Exception {
        this(name, Gender.Unknown, dateOfBirth);
    }

    public Account(String name, Gender gender, String dateOfBirth) throws Exception {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = Util.stringToDate(dateOfBirth);
    }

    public Account(String name, String gender, String dateOfBirth) throws Exception {
        this.name = name;
        this.gender = Util.genderStringValidation(gender);

        this.dateOfBirth = Util.stringToDate(dateOfBirth);
    }

    public Account(String name, String gender, Date  dateOfBirth) throws Exception {
        this(name, gender, Util.dateToString(dateOfBirth, false));

    }

    // mutators

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) throws Exception {
        this.gender = Util.genderStringValidation(gender);
    }

    public void setDateOfBirth(String dateOfBirth) throws Exception {
        this.dateOfBirth = Util.stringToDate(dateOfBirth);
    }

    // accessors

    public String getName() {
        return name;
    }

    public String getGender() {
        if(gender == Gender.M)
            return "M";
        else
            return "F";
    }

    public int getAge() {
        Calendar dateNow = Calendar.getInstance();
        int day = dateNow.get(Calendar.DATE) - dateOfBirth.get(Calendar.DATE);
        int month = (dateNow.get(Calendar.MONTH) + 1) - dateOfBirth.get(Calendar.MONTH);
        int age = dateNow.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        if (month < 0) {
            age--;
        } else if (month == 0) {
            if (day < 0)
                age--;
        }
        return age;
    }

    public String getDateOfBirth() {
        int day = dateOfBirth.get(Calendar.DATE);
        int month = dateOfBirth.get(Calendar.MONTH);
        int year = dateOfBirth.get(Calendar.YEAR);

        return year + "-" + month + "-" + day;
    }

    // extra methods:

    public void accountInfo() {
        System.out.println("Name: " + this.name + "\n" +
                "Gender: " + getGender() + "\n" +
                "Age: " + this.getAge());
    }

    public int compareTo(Account ac) {
        return this.name.compareTo(ac.name);
    }

}


