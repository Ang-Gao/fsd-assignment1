package fsd.assignment.assignment1.datamodel;

public class Student {
    private String studId;
    private String yearOfStudy;
    private String module1;
    private String module2;
    private String module3;

    /* TODO: the constructor must accept parameters so that all
             instance variables are set accordingly
    */
    public Student(String studId,String yearOfStudy,String module1,String module2,String module3) {
        this.studId = studId;
        this.yearOfStudy = yearOfStudy;
        this.module1 = module1;
        this.module2 = module2;
        this.module3 = module3;
    }

    /* TODO: ensure that all getters are included here
    */

    public String getStudId() {
        return studId;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public String getModule1() {
        return module1;
    }

    public String getModule2() {
        return module2;
    }

    public String getModule3() {
        return module3;
    }
    /* TODO: include a toString() that returns studId
    */
    // TODO: 2022/3/9 内容不确定
    @Override
    public String toString() {
        return "Student{" +
                "studId='" + studId + '\'' +
                '}';
    }
}
