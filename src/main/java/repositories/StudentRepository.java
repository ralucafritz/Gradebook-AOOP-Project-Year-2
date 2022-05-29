package repositories;

import clientSide.Student;
import extras.Util;
import services.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;

public class StudentRepository {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private CourseRepository courseRepository;

    public static StudentRepository instance;

    private StudentRepository() {}

    public static StudentRepository getInstance() {
        if(instance == null)
            instance = new StudentRepository();
        return instance;
    }


    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS student " +
                "(" +
                    "studentId int PRIMARY KEY AUTO_INCREMENT," +
                    "name varchar(40) ," +
                    "gender varchar(1)," +
                    "dateOfBirth Date," +
                    "coursesAndGrades varchar(1000)," +
                    "currentID int UNIQUE" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // CREATE

    public void addStudent(Student student) {

        String insertStudentSql = "INSERT INTO student(name, gender, dateOfBirth, coursesAndGrades, currentID) VALUES(?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentSql)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setDate(3, Date.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(4, "");
            preparedStatement.setInt(5, student.getID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // GET / READ
    public Student getStudentById(int id) {

        String selectStudentSql = "SELECT * FROM student WHERE studentId =?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStudentSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Student studentToReturn = mapToStudent(resultSet);
                if (studentToReturn != null) {
                    return studentToReturn;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdByStudent(Student student) {
        String selectSQL = "SELECT * FROM student WHERE currentId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, student.getID());

            ResultSet resultSet = preparedStatement.executeQuery();

            int objectId = -1;
            if(resultSet.next())
                objectId = resultSet.getInt(1);

            return objectId;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Student> getStudentsList() {
        String selectStudentsSQL = "SELECT * FROM student";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ArrayList<Student> studentsList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) { //try with resources

            ResultSet resultSet = stmt.executeQuery(selectStudentsSQL);

            while(resultSet.next()) {

                Student student = mapToStudent(resultSet);

                if (student != null){
                    System.out.println("Student ID # " + resultSet.getString(1) + " loaded");
                    studentsList.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentsList;
    }

    // DELETE
    public void deleteStudentById(int id){
        UtilRepository.deleteObjectById(id, "student", "studentId");
    }

    // READ ALL
    public void displayStudents() {
        String selectStudentSql = "SELECT * FROM student";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            //try with resources
            ResultSet resultSet = stmt.executeQuery(selectStudentSql);

            while (resultSet.next()) {

                Student student = mapToStudent(resultSet);

                if(student!= null) {
                    System.out.println("Studentul #ID " +
                            resultSet.getString(1) + ":");
                    student.accountInfo();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateStudent(Student student, int id) {
        String updateStudentSql = "UPDATE student " +
                "SET " +
                    "name=?, " +
                    "gender=?, " +
                    "dateOfBirth=?, " +
                    "coursesAndGrades=?, " +
                    "currentID=? " +
                "WHERE " +
                    "studentId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStudentSql)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setDate(3, java.sql.Date.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(4, student.returnCoursesAndGradesList());
            preparedStatement.setInt(5,student.getID());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // private methods

    private Student mapToStudent(ResultSet resultSet) {
        try {
            return new Student(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Util.dateToString(resultSet.getDate(4), false),
                    resultSet.getString(5),
                    resultSet.getInt(6));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// COMMENTED UNUSED CODE ///////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    public void addCourse(int idStudent, int idCourse) throws SQLException {
//        String addCourseSql = "UPDATE student SET coursesAndGrades=? WHERE studentId=" + idStudent;
//
//        Connection connection = DatabaseConfiguration.getDatabaseConnection();
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(addCourseSql)) {
//
//            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM student WHERE studentId=" + idStudent);
//
//            while (resultSet.next()) {
//                // get student data
//
//                String coursesAndGradesList = resultSet.getString(5);
//                coursesAndGradesList += "," + idCourse + "-0";
//
//                preparedStatement.setString(1,coursesAndGradesList);
//            }
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void removeCourse(int idStudent, Course course) throws SQLException {
//        String removeCourseSql = "UPDATE student SET coursesAndGrades=? WHERE studentId=?";
//
//        Connection connection = DatabaseConfiguration.getDatabaseConnection();
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(removeCourseSql)) {
//
//            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM student WHERE studentId=" + idStudent);
//
//            while (resultSet.next()) {
//                // get student data
//                if(resultSet.getString(1).equals(String.valueOf(idStudent))) {
//                    Student student = mapToStudent(resultSet);
//
//                    student.removeCourse(course);
//                    preparedStatement.setString(1, student.returnCoursesAndGradesList());
//
//                }
//                preparedStatement.setString(2, String.valueOf(idStudent));
//            }
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void addGrade(int idStudent, Course course, int grade) throws SQLException {
//        String addCourseSql = "UPDATE student SET coursesAndGrades=? WHERE studentId=?";
//
//        Connection connection = DatabaseConfiguration.getDatabaseConnection();
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(addCourseSql)) {
//
//            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM student WHERE studentId=" + idStudent);
//
//            while (resultSet.next()) {
//                // get student data
//                if(resultSet.getString(1).equals(String.valueOf(idStudent))) {
//                    Student student = mapToStudent(resultSet);
//
//                    student.setGrade(course, grade);
//                    preparedStatement.setString(1, student.returnCoursesAndGradesList());
//                }
//                preparedStatement.setString(2, String.valueOf(idStudent));
//            }
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
