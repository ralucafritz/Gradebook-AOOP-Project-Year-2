package repositories;

import clientSide.Group;
import services.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupRepositoryStudents {

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS student" +
                "(" +
                    "groupId int PRIMARY KEY AUTO_INCREMENT=100, " +
                    "studentList , " +
                    "professorsList , " +
                    "coursesList  " +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGroup(Group group) {
        String insertStudentSql = "INSERT INTO student(idNme, studentList, professorsList, crousesList) VALUES(?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentSql)) {
            preparedStatement.setString(1, group.printStudentsList("", ","));
            preparedStatement.setString(3, group.printProfessorList("", ","));
            preparedStatement.setString(2, group.printCoursesList("", ","));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // private methods
//
//    private Group mapToGroup(ResultSet resultSet) throws SQLException, Exception {
//        if(resultSet.next()) {
//            String[] studentsArr = resultSet.getString(2).split(",");
//            String[] professorsArr = resultSet.getString(3).split(",");
//            String[] coursesArr = resultSet.getString(4).split(",");
//
//            ArrayList<Student> studentsList = new ArrayList<>();
//            for(String str : studentsArr)
//            {
//                studentsList.add(str)
//            }
//            ArrayList<Professor> professorsList = new ArrayList<>();
//            Set<Course> coursesList = new HashSet<>();
//        }
//        return null;
//    }



}
