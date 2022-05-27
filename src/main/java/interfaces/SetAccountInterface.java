package interfaces;

// INTERFACE USED FOR THE TEMPLATE METHOD THAT CREATES A PROFESSOR OR A STUDENT
public interface SetAccountInterface {
    public void setName(String name);
    public void setGender(String gender) throws Exception;
    public void setDateOfBirth(String dateOfBirth) throws Exception;
}
