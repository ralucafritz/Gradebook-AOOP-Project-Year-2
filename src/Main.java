import Extras.Generator;
import Platform.Course;
import Platform.Group;

public class Main {

    public static void main(String[] args) throws Exception {

        Group group = Generator.groupGenerator();
        Course course = Generator.courseGenerator();
        group.addCourse(course);

        System.out.println(group);
        System.out.println(course);




    }
}
