package fsd.assignment.assignment1.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class StudentData {
    /* TODO: include an instance of StudentData
    */
    private static StudentData instance;
    private static String filename = "student-data.txt";

    private ObservableList<Student> students;
    //私有构造器
    private  StudentData(){};
    //get instance
    public static StudentData getInstance() {
        /* TODO: complete the getter for the instance created
        */
        //双检锁，static所以要用类去当锁
        if (instance == null){
            synchronized (StudentData.class){
                if (instance == null){
                    instance = new StudentData();
                }
                return instance;
            }
        }
        return instance;
    }

    public ObservableList<Student> getStudents() {
        /* TODO: complete the getter for the observable arraylist
        */
        // TODO: 2022/3/9 不确定是不是直接返回一个arraylist
        return students;
    }

    public void loadStudentData() throws IOException {
        students = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                /* TODO: split each input line using a tab
                 */
                String[] studentItem;
                studentItem = input.split("\t");
                /* TODO: using the String create each piece of data so that all the instance variables
                         have a value accordingly
                 */
                String studId;
                String yearStudy;
                String mod1;
                String mod2;
                String mod3;
                studId = studentItem[0];
                yearStudy = studentItem[1];
                mod1 = studentItem[2];
                mod2 = studentItem[3];
                mod3 = studentItem[4];
                /* TODO: complete the call to the constructor by passing in the parameters
                 */
                Student studDataItem;
                studDataItem = new Student(studId,yearStudy,mod1,mod2,mod3);
                /* TODO: add the studentDataItem to the students array
                 */
                //>include the statement here
                //students.add(studDataItem);
                addStudentData(studDataItem);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeStudentData() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            /* TODO: complete the iterator
             */
            Iterator<Student> it = students.iterator();
            while (it.hasNext()) {
                /* TODO: accept the next item from the iterated list
                 */
                Student item = it.next();
                /* TODO: complete the write() using String.format
                         remember to separate each string with a tab
                 */
                bw.write(String.format("%s\t%s\t%s\t%s\t%s",
                        item.getStudId(),
                        item.getYearOfStudy(),
                        item.getModule1(),
                        item.getModule2(),
                        item.getModule3()));
                /* TODO: once a student item is written to the file ensure that
                         the next item is stored on a new line
                 */
                //>insert statement here
                if (!it.hasNext()){break;}
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void addStudentData(Student studentToAdd){
        /* TODO: complete the addStudentData so that a student can be added
                 to students
         */
        students.add(studentToAdd);
        /*try {
            storeStudentData();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    public void deleteStudent(Student stu){
        /* TODO: complete the addStudentData so that a student can be removed
                 from students
         */
        students.remove(stu);
    }
}
