package repositories;

import clientSide.Course;
import extras.Util;
import services.config.DatabaseConfiguration;

import java.sql.*;

public class CourseRepository {

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS professor " +
                "(" +
                    "courseId int PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(40), " +
                    "hasProfessor boolean," +
                    "constraint ads_pk" +
                    "FOREIGN KEY(professorId) REFERENCES professor(professorId) ON  DELETE CASCADE" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // CREATE
    public void addCourse(Course course, int id) {

        String insertCourseSql = "INSERT INTO course(name, hasProfessor, id) VALUES(?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCourseSql)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setBoolean(2, course.hasProfessor());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET / READ
    public Course getCourse(int id) throws Exception {

        String selectSQL = "SELECT * FROM course WHERE  id =?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return mapToCourse(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // DELETE
    public void deleteCourseById(int id)  {
        String deleteSql = "DELETE FROM professor WHERE id=?";

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
    public void displayCourses() {
        String selectSql = "SELECT * FROM course";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);

            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        new ProfessorRepository().getProfessorById(resultSet.getInt(4)) );
                if(course!= null) {
                    System.out.println("Course #ID " +
                            resultSet.getString(1) +
                            ": " +
                            resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updatecourse(String name, String gender, String dateOfBirth, int id) {
        String updateProfessorSql = "UPDATE professor SET name=? AND gender=? AND dateOfBirth=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProfessorSql)) {
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

    private Course mapToCourse(ResultSet resultSet) throws SQLException, Exception {
        if(resultSet.next()) {
            ProfessorRepository professorRepository = new ProfessorRepository();

            return new Course(
                    resultSet.getString(2),
                    resultSet.getBoolean(3),
                    professorRepository.getProfessorById(resultSet.getInt(4)));
        }
        return null;
    }


}
