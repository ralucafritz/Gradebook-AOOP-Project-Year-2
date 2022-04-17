public class Main {

    public static void main(String[] args) throws Exception {

        Group group = Generator.groupGenerator();
        Course course = Generator.courseGenerator();
        group.addCourse(course);

        System.out.println(group);
        System.out.println(course);




    }
}
