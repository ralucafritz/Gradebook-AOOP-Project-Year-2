package repositories;

import clientSide.Professor;
import extras.Util;
import services.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;

public class ProfessorRepository {

    public static ProfessorRepository instance;

    private ProfessorRepository() {}

    public static ProfessorRepository getInstance() {
        if(instance == null)
            instance = new ProfessorRepository();
        return instance;
    }

    private CourseRepository courseRepository;

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS professor " +
                "(" +
                    "professorId int PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(40), " +
                    "gender varchar(1), " +
                    "dateOfBirth Date," +
                    "coursesList varchar(1000)," +
                    "currentId int UNIQUE" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // CREATE
    public void addProfessor(Professor professor) {

        String insertProfessorSql = "INSERT INTO professor(name, gender, dateOfBirth, coursesList, currentId) VALUES(?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertProfessorSql)) {
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setString(2, professor.getGender());
            Date date = java.sql.Date.valueOf(professor.getDateOfBirth());
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, "");
            preparedStatement.setInt(5, professor.getID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET / READ
    public Professor getProfessorById(int id)   {

        String selectProfessorSql = "SELECT * FROM professor WHERE  professorId =?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectProfessorSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Professor professorToReturn =  mapToProfessor(resultSet);
                if (professorToReturn != null) {
                    return professorToReturn;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdByObject(Professor professor)   {
        String getIdByProfessorSQL = "SELECT * FROM professor WHERE currentId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(getIdByProfessorSQL)) {
            preparedStatement.setInt(1, professor.getID());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // DELETE
    public void deleteProfessorById(int id)   {
        UtilRepository.deleteObjectById(id, "professor", "professorId");
    }

    // READ ALL
    public void displayProfessors() {
        String displayProfessorsSQL = "SELECT * FROM professor";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            //try with resources
            ResultSet resultSet = stmt.executeQuery(displayProfessorsSQL);

            while (resultSet.next()) {
                Professor professor = mapToProfessor(resultSet);

                if(professor!= null) {
                    System.out.println("Professorul #ID " +
                            resultSet.getString(1) + ":");
                    professor.accountInfo();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateProfessor(Professor professor, int id) {
        String updateProfessorSql = "UPDATE professor " +
                "SET " +
                    "name=?, " +
                    "gender=?,  " +
                    "dateOfBirth=?,  " +
                    "coursesList=?," +
                    "currentId=? " +
                "WHERE " +
                    "professorId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProfessorSql)) {
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setString(2, professor.getGender());
            preparedStatement.setDate(3, java.sql.Date.valueOf(Util.reverseYearAndDay(professor.getDateOfBirth(), true)));
            preparedStatement.setString(4, professor.returnCoursesList());
            preparedStatement.setInt(5, professor.getID());
            preparedStatement.setInt(6,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Professor> getProfessorsList() {
        String selectProfessorsSQL = "SELECT * FROM professor";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ArrayList<Professor> professorsList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) { //try with resources

            ResultSet resultSet = stmt.executeQuery(selectProfessorsSQL);

            while(resultSet.next()) {

                Professor professor = mapToProfessor(resultSet);

                if (professor != null){
                    System.out.println("Professor ID # " + resultSet.getString(1) + " loaded");
                    professorsList.add(professor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return professorsList;
    }

    // private methods

    private Professor mapToProfessor(ResultSet resultSet) {
        try {
            return new Professor(
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

}
