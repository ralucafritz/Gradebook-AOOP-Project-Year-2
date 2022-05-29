package services;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvReports {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// THIS CLASS's PURPOSE IS TO WRITE AND READ CSV REPORTS ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static String getFilePath(String nameFile) {
        return "E:\\repos\\aoop-project\\src\\main\\java\\Services\\reports\\" + nameFile + ".csv";
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////// CREATE REPORT FILE ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean createReport(String column1, String column2, String nameFile){
        try {
            File newFile = new File(getFilePath(nameFile));
            if (newFile.createNewFile()) {
                System.out.println("File created: " + nameFile);

                // REPORT HEADER
                CsvReports.writeToReportWithHeader(column1, column2, nameFile, true);
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


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// WRITE TO REPORT /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void writeToReportWithHeader(String column1, String column2, String nameFile, boolean isHeader) {
        String filePath = getFilePath(nameFile);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {

        bufferedWriter.append(column1);
        bufferedWriter.append(",");
        bufferedWriter.append(column2);
        bufferedWriter.append(",");

        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss - dd.MM.yyyy");
        String timeStamp = date.format(new Date());

        if(isHeader)
        {
            bufferedWriter.append("Timestamp");
        }else
        {
            bufferedWriter.append(timeStamp);
        }

        bufferedWriter.append("\n");

        System.out.println("Report successful.\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToReport(String column1, String column2, String nameFile)
    {
        writeToReportWithHeader(column1, column2, nameFile, false);
    }

    public static void readDataFromReport(String nameFile) {
        String filePath = getFilePath(nameFile);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String report;

            while((report = bufferedReader.readLine()) != null) {
                String [] reportData = report.split(",");
                System.out.println("\t" + reportData[0] + " \t " + reportData[1] + "\t" + reportData[2]);

            }
            System.out.println("\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void endOfReport(String nameFile) {
        writeToReport("========","========", nameFile);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////// EXTRA COMMENTED OUT CODE //////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

}
