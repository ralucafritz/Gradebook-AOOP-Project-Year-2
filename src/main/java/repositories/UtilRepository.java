package repositories;

import services.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilRepository {

    private ProfessorRepository professorRepository;
    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;

    public static void deleteObjectById(int id, String objectName, String objectNameId) {
        String deleteSql = "DELETE FROM " + objectName + " WHERE " + objectNameId + "=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addObjToList(int idObjRepo, int idObjToAdd, String objRepo, String listName, String objRepoId) throws SQLException {
        String addObjSql = "UPDATE " + objRepo + " SET " + listName + "=? WHERE " + objRepoId + "=" + idObjRepo;

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(addObjSql)) {

            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM " + objRepo + " WHERE " + objRepoId + "=" + idObjRepo);

            while(resultSet.next()) {
                String list = resultSet.getString(3);
                if(list.length()!=0)
                    list += ",";
                list += idObjToAdd;
                preparedStatement.setString(1, list);
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
