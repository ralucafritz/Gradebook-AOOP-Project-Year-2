package repositories;

import clientSide.Course;
import clientSide.Group;
import clientSide.Professor;
import clientSide.Student;
import services.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;

public class GroupRepository {

    public static GroupRepository instance;

    private GroupRepository () {}

    public static GroupRepository getInstance() {
        if(instance == null)
            instance = new GroupRepository();
        return instance;
    }


    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS groupUni" +
                "("+
                    "groupId int PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(3) UNIQUE, " +
                    "studentsList varchar(1000), " +
                    "professorsList varchar(1000), " +
                    "coursesList varchar(1000)" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // CREATE
    public void addGroup(Group group) {
        String insertGroupSql = "INSERT INTO groupUni" +
                "(" +
                    "name, " +
                    "studentsList, " +
                    "professorsList, " +
                    "coursesList" +
                ") " +
                "VALUES(?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertGroupSql)) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.returnList(new Student()));
            preparedStatement.setString(3, group.returnList(new Professor()));
            preparedStatement.setString(4, group.returnList(new Course()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET / READ
    public Group getObjectById(int id) throws Exception {

        String selectGroupByIdSql = "SELECT * FROM groupUni WHERE groupId =?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectGroupByIdSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Group groupToBeReturned = mapToGroup(resultSet);
                if (groupToBeReturned != null) {
                    return groupToBeReturned;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public int getIdByObject(Group group) {
        String selectObjGroupSQL = "SELECT * FROM groupUni WHERE name=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectObjGroupSQL)) {
            preparedStatement.setString(1, group.getName());

            ResultSet resultSet = preparedStatement.executeQuery();

            int idObject = -1;

            if (resultSet.next())
                idObject = resultSet.getInt(1);
            return idObject;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

//    public void addStudent(int idGroup, int idStudent) throws SQLException {
//        UtilRepository.addObjToList(idGroup, idStudent, "groupUni","studentsList","groupId");
//    }
//
//    public void addProfessor(int idGroup, int idProfessor) throws SQLException {
//        UtilRepository.addObjToList(idGroup, idProfessor, "groupUni","professorsList","groupId");
//    }
//
//    public void addCourse(int idGroup, int idCourse) throws SQLException {
//        UtilRepository.addObjToList(idGroup, idCourse, "groupUni","courseList","groupId");
//    }

    // PRINT

    public void displayGroups() {
        String selectGroupSql = "SELECT * FROM groupUni";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources

            ResultSet resultSet = stmt.executeQuery(selectGroupSql);

            while(resultSet.next()) {

                    Group group = mapToGroup(resultSet);

                    if (group != null)
                        System.out.println("Group #ID " +
                                getIdByObject(group) + ": " +
                                group.getName() + "\n" +
                                group.printStudentsList("\t", "\n"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateGroup(Group group, int idGroup){
        String updateGroupSql = "UPDATE groupUni SET " +
                "name=?, " +
                "studentsList=?, " +
                "professorsList=?, " +
                "coursesList=? " +
                "WHERE " +
                "groupId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateGroupSql)) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.returnList(new Student()));
            preparedStatement.setString(3, group.returnList(new Professor()));
            preparedStatement.setString(4, group.returnList(new Course()));
            preparedStatement.setInt(5, idGroup);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteGroupById(int id) {
        UtilRepository.deleteObjectById(id, "groupUni", "groupId");
    }

    public ArrayList<Group> getGroupList() {

        String selectGroupsSql = "SELECT * FROM groupUni";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        ArrayList<Group> groupList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) { //try with resources

            ResultSet resultSet = stmt.executeQuery(selectGroupsSql);

            while(resultSet.next()) {

                Group group = mapToGroup(resultSet);

                if (group != null){
                    System.out.println("Group ID # " + resultSet.getString(1) + " loaded");
                    groupList.add(group);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return groupList;
    }

    // private methods

    private Group mapToGroup(ResultSet resultSet) throws SQLException, Exception {
         return new Group(
              resultSet.getString(2),
              resultSet.getString(3),
              resultSet.getString(4),
              resultSet.getString(5));


    }



}
