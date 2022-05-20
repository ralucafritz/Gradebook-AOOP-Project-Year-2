package Services;

import Client.Student;
import Extras.GradesOptions;
import Platform.Course;

import java.io.*;

public class CsvReports {
    private String getFilePath (String nameFile) {
        return "E:\\repos\\aoop-project\\" + nameFile + ".csv";
    }

    public void writeGradesToReport(Student student, Course course, GradesOptions option){

         int grade = student.getGrade(course);
         String nameFile = "";

         if(option == GradesOptions.NOT_MARKED && grade == 0) {
             nameFile += "ReportStudentsNotMarked";
         }
         else if(option == GradesOptions.PASSED && grade > 5) {
             nameFile += "ReportPassedStudents";
         }
         else if(option == GradesOptions.FAILED && grade < 5 && grade > 0) {
             nameFile += "ReportFailedStudents";
         }
         else {
              return;
         }

        writeToReport(student.getName(), course.getName(), nameFile);

    }

    public void writeToReport(String column1, String column2, String nameFile) {
        String filePath = getFilePath(nameFile);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
        bufferedWriter.append(column1);
        bufferedWriter.append(",");
        bufferedWriter.append(column2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initReportHeader(String word, String nameFile) {
        writeToReport("NAME", word, nameFile);
    }

    public void readDataFromReport(String nameFile) {
        String filePath = getFilePath(nameFile);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String report;

            while((report = bufferedReader.readLine()) != null) {
                String [] reportData = report.split(",");
                System.out.println(reportData[0] + " - " + reportData[1]);
            }

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
