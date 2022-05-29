package repositories;

import clientSide.Course;
import services.config.DatabaseConfiguration;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CourseRepository {

    private ProfessorRepository professorRepository = ProfessorRepository.getInstance();

    public static CourseRepository instance;

    private CourseRepository() {}

    public static CourseRepository getInstance() {
        if(instance == null)
            instance = new CourseRepository();
        return instance;
    }


    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS course " +
                "(" +
                    "courseId int PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(40), " +
                    "hasProfessor boolean," +
                    "professorId int," +
                    "currentId int" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // CREATE
    public void addCourse(Course course) {

        String insertCourseSql = "INSERT INTO course(name, hasProfessor, professorId, currentId) VALUES(?, ?,?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCourseSql)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setBoolean(2, course.hasProfessor());
            ProfessorRepository professorRepository = ProfessorRepository.getInstance();
            preparedStatement.setInt(3, professorRepository.getIdByObject(course.getProfessor()));
            preparedStatement.setInt(4, course.getID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET / READ
    public  Course getObjectById(int id) {

        String selectSQL = "SELECT * FROM course WHERE courseId =?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                return mapToCourse(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdByObject(Course course1) {
        String selectSQL = "SELECT * FROM course WHERE currentId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            ProfessorRepository professorRepository = ProfessorRepository.getInstance();

            preparedStatement.setInt(1, course1.getID());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // DELETE
    public void deleteCourseById(int id) {
        UtilRepository.deleteObjectById(id, "course", "courseId");
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
                        resultSet.getInt(4),
                        resultSet.getInt(5));
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

    public void updateCourse(Course course1, int id) {

        String updateProfessorSql = "UPDATE course SET name=? AND hasProfessor=? AND professorId=? WHERE courseId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProfessorSql)) {
            preparedStatement.setString(1, course1.getName());
            preparedStatement.setBoolean(2, course1.hasProfessor());
            preparedStatement.setInt(3, professorRepository.getIdByObject(course1.getProfessor()));
            preparedStatement.setInt(4, course1.getID());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Course> getCourseList() {

        String selectCoursesList = "SELECT * FROM course";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        Set<Course> coursesList = new HashSet<>();
        try (Statement stmt = connection.createStatement()) { //try with resources

            ResultSet resultSet = stmt.executeQuery(selectCoursesList);

            while(resultSet.next()) {

                Course course = mapToCourse(resultSet);

                if (course != null){
                    System.out.println("Course ID # " + resultSet.getString(1) + "loaded");
                    coursesList.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return coursesList;
    }

    // private methods

    private Course mapToCourse(ResultSet resultSet) {
        ProfessorRepository professorRepository = ProfessorRepository.getInstance();
        try{
            return new Course(
                    resultSet.getString(2),
                    resultSet.getBoolean(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
