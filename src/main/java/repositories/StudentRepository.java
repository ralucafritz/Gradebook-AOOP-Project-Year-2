package repositories;

import clientSide.Student;
import extras.Util;
import services.config.DatabaseConfiguration;

import java.sql.*;

public class StudentRepository {

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS student " +
                "(" +
                    "studentId int PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(40), " +
                    "gender varchar(1), " +
                    "dateOfBirth Date" +
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

        String insertStudentSql = "INSERT INTO student(name, gender, dateOfBirth) VALUES(?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentSql)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setDate(3, java.sql.Date.valueOf(student.getDateOfBirth()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET / READ
    public Student getStudentById(int id) throws Exception {

        String selectSQL = "SELECT * FROM student WHERE  id =?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return mapToStudent(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // DELETE
    public void deleteStudentById(int id)  {
        String deleteSql = "DELETE FROM student WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConfiguration.closeDatabaseConnection();
    }

    // READ ALL
    public void displayStudents() {
        String selectSql = "SELECT * FROM student";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);

            while (resultSet.next()) {
                Student student = new Student(resultSet.getString(2), resultSet.getString(3), Util.dateToString(resultSet.getDate(4), false));
                if(student!= null) {
                    System.out.println("Studentul #ID " + resultSet.getString(1) + ":");
                    student.accountInfo();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateStudent(String name, String gender, String dateOfBirth, int id) {
        String udpateStudentSql = "UPDATE student SET name=? AND gender=? AND dateOfBirth=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(udpateStudentSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setDate(3, java.sql.Date.valueOf(Util.reverseYearAndDay(dateOfBirth, true)));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // private methods

    private Student mapToStudent(ResultSet resultSet) throws SQLException, Exception {
        if(resultSet.next()) {
            return new Student(resultSet.getString(2), resultSet.getString(3), Util.dateToString(resultSet.getDate(4), false));
        }
        return null;
    }


}
