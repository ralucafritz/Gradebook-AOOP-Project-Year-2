package Client;

import Extras.*;
import Interfaces.GetNameInterface;
import Interfaces.SetAccountInterface;

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
            return "Male";
        else
            return "Female";
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

    // extra methods:

    public void accountInfo() {
        System.out.println("Name: " + this.name + "\n" +
                "Gender: " + this.gender + "\n" +
                "Age: " + this.getAge());
    }

}


