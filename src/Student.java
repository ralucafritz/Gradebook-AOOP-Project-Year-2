import java.util.ArrayList;

public class Student extends Account{

    private ArrayList<Course> courses =  new ArrayList<Course>();
//    private Group group;

    // constructors

    public Student() throws Exception {
       super();
    }

    public Student(String name, String dateOfBirth) throws Exception {
        super(name, dateOfBirth);
    }

    public Student(String name, String gender, String dateOfBirth) throws Exception {
        super(name, gender, dateOfBirth);
    }

    public Student(String name, Gender gender, String dateOfBirth) throws Exception {
        super(name, gender, dateOfBirth);
    }
// mutators
//
//    public void setGroup(Group group) {
//        this.group = group;
//    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    // accessors

    public ArrayList<Course> getCourses() {
        return courses;
    }

//    public Group getGroup() {
//        return group;
//    }

    // extra methods

    public void accountInfo() {
        super.accountInfo();
//        System.out.println("Group: " + this.group.getName() + "\n" +
        System.out.println("List enlisted courses: \n \t" + Util.arrayListToString(this.courses));
    }

}
