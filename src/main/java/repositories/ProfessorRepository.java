package repositories;

import clientSide.Professor;
import extras.Util;
import services.config.DatabaseConfiguration;

import java.sql.*;

public class ProfessorRepository {

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS professor " +
                "(" +
                    "professorId int PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(40), " +
                    "gender varchar(1), " +
                    "dateOfBirth " +
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

        String insertProfessorSql = "INSERT INTO professor(name, gender, dateOfBirth) VALUES(?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertProfessorSql)) {
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setString(2, professor.getGender());
            preparedStatement.setDate(3, java.sql.Date.valueOf(professor.getDateOfBirth()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET / READ
    public Professor getProfessorById(int id) throws Exception {

        String selectSQL = "SELECT * FROM professor WHERE  id =?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return mapToProfessor(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // DELETE
    public void deleteProfessorById(int id)  {
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
    public void displayProfessors() {
        String selectSql = "SELECT * FROM professor";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);

            while (resultSet.next()) {
                Professor professor = new Professor(resultSet.getString(2), resultSet.getString(3), Util.dateToString(resultSet.getDate(4), false));
                if(professor!= null) {
                    System.out.println("Professorul #ID " + resultSet.getString(1) + ":");
                    professor.accountInfo();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateProfessor(String name, String gender, String dateOfBirth, int id) {
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

    private Professor mapToProfessor(ResultSet resultSet) throws SQLException, Exception {
        if(resultSet.next()) {
            return new Professor(resultSet.getString(2), resultSet.getString(3), Util.dateToString(resultSet.getDate(4), false));
        }
        return null;
    }
}
