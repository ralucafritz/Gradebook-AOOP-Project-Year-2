package Services;

import java.io.*;

public class CsvReports {
    private static String getFilePath(String nameFile) {
        return "E:\\repos\\aoop-project\\src\\main\\java\\Services\\reports\\" + nameFile + ".csv";
    }

    public static boolean createReport(String column1, String column2, String nameFile){
        try {
            File newFile = new File(getFilePath(nameFile));
            if (newFile.createNewFile()) {
                System.out.println("File created: " + nameFile);

                // REPORT HEADER
                CsvReports.writeToReport(column1, column2, nameFile);
                return false;
            } else {
                System.out.println("File `" + nameFile + ".csv` already exists.");
                return true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    return true;
    }
//
//    public static void writeGradesToReport(Student student, Course course, String nameFile){
////
////         int grade = student.getGrade(course);
////
////         if(option == GradesOptions.NOT_MARKED && grade == 0) {
////             nameFile += "ReportsStudentsNotMarked";
////         }
////         else if(option == GradesOptions.PASSED && grade > 5) {
////             nameFile += "ReportsPassedStudents";
////         }
////         else if(option == GradesOptions.FAILED && grade < 5 && grade > 0) {
////             nameFile += "ReportsFailedStudents";
////         }
////         else {
////              return;
////         }
//
//        writeToReport(student.getName(), course.getName(), nameFile);
//
//    }

    public static void writeToReport(String column1, String column2, String nameFile) {
        String filePath = getFilePath(nameFile);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
        bufferedWriter.append(column1);
        bufferedWriter.append(",");
        bufferedWriter.append(column2);
        bufferedWriter.append("\n");

        System.out.println("Report successful.\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readDataFromReport(String nameFile) {
        String filePath = getFilePath(nameFile);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String report;

            while((report = bufferedReader.readLine()) != null) {
                String [] reportData = report.split(",");
                System.out.println("\t" + reportData[0] + " \t " + reportData[1]);
            }
            System.out.println("\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
