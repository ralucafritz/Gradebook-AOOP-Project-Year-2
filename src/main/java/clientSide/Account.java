package clientSide;

import extras.Gender;
import extras.Util;
import interfaces.GetNameInterface;
import interfaces.SetAccountInterface;
import repositories.CourseRepository;
import repositories.ProfessorRepository;
import repositories.StudentRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public abstract class Account implements GetNameInterface, SetAccountInterface {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////// THIS ABSTRACT CLASS IS MEANT TO BECOME A SORT OF TEMPLATE FOR THE CLASSES PROFESSOR AND STUDENT ///////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // instances fields
    private String name;
    private Gender gender;
    private Calendar dateOfBirth;

    // currentID only used for load data data from the database
    private int currentID;

    // ID creates an ID for each object it's created
    private static int ID=1;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CONSTRUCTORS ///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // THE CONSTRUCTORS IN THE ACCOUNT ABSTRACT CLASS ARE ONLY MEANT FOR THE INHERITANCE OF THE CLASSES
    // STUDENT & PROFESSORS

    /// EMPTY CONSTRUCTOR FOR AN ACCOUNT
    public Account() {
        this(null, Gender.Unknown, "01-01-1960");
        this.currentID = ID;
        ID ++;
    }

    public Account(String name, String dateOfBirth)  {
        this(name, Gender.Unknown, dateOfBirth);

        ArrayList<Student> listStudent = StudentRepository.getInstance().getStudentsList();
        ArrayList<Professor> listProfessor = ProfessorRepository.getInstance().getProfessorsList();

        for(Student student : listStudent) {
            for(Professor professor : listProfessor){
                while(student.getID()==ID || professor.getID()==ID){
                         ID++;
                 }
            }
        }
        this.currentID = ID;
        ID ++;
    }

    public Account(String name, Gender gender, String dateOfBirth)  {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = Util.stringToDate(dateOfBirth);

        ArrayList<Student> listStudent = StudentRepository.getInstance().getStudentsList();
        ArrayList<Professor> listProfessor = ProfessorRepository.getInstance().getProfessorsList();

        for(Student student : listStudent) {
            for(Professor professor : listProfessor){
                while(student.getID()==ID || professor.getID()==ID){
                    ID++;
                }
            }
        }

        this.currentID = ID;
        ID ++;
    }

    public Account(String name, String gender, String dateOfBirth)  {
        this.name = name;
        this.gender = Util.genderStringValidation(gender);
        this.dateOfBirth = Util.stringToDate(dateOfBirth);

        this.currentID = ID;
        ID ++;
    }

    public Account(String name, String gender, Date  dateOfBirth)  {
        this(name, gender, Util.dateToString(dateOfBirth, false));
        this.currentID = ID;
        ID ++;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////// MUTATORS //////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender)  {
        this.gender = Util.genderStringValidation(gender);
    }

    public void setDateOfBirth(String dateOfBirth)  {
        this.dateOfBirth = Util.stringToDate(dateOfBirth);
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// ACCESSORS ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public String getGender() {
        if(gender == Gender.M)
            return "M";
        else
            return "F";
    }

    public int getID() {
        return currentID;
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////// EXTRA METHODS /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void accountInfo() {
        System.out.println("\t Name: " + this.name + "\n" +
                "\t Gender: " + getGender() + "\n" +
                "\t Age: " + this.getAge());
    }

    public int compareTo(Account ac) {
        return this.name.compareTo(ac.name);
    }



}


