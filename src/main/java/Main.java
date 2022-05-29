import services.Menu;
import services.config.DatabaseConfiguration;

public class Main {

    public static void main(String[] args) throws Exception {
////
        Menu.getInstance().startMenu();

        DatabaseConfiguration.closeDatabaseConnection();

//        StudentRepository studentRepository = new StudentRepository();
//        GroupRepository groupRepository = new GroupRepository();
//        ProfessorRepository professorRepository = new ProfessorRepository();
//        CourseRepository courseRepository = new CourseRepository();

//        studentRepository.createTable();
//        groupRepository.createTable();
//        professorRepository.createTable();
//        courseRepository.createTable();
//
//        for(int i=0; i<=60; i++)
//        {
//            Student student = Generator.studentGenerator();
//            studentRepository.addStudent(student);
//        }
//
//        for(int i=0;i<=3;i++)
//        {
//            Group group = new Group();
//            groupRepository.addGroup(group);
//        }
//
//        Professor professor = Generator.professorGenerator();
//
//        professorRepository.addProfessor(professor);
//        Course course = Generator.courseGenerator(professor);
//
//        professor.addCourse(course);
//        professorRepository.updateProfessor(professor, professor.getID());
//        courseRepository.addCourse(course);

    }
}
