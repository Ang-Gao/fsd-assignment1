package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


public class EditStudentController {
    //all of the variables declared below correspond with the edit-students.fxml
    @FXML
    private Label yearStudyDisplay;

    @FXML
    private Label mod1Edit;

    @FXML
    private Label mod2Edit;

    @FXML
    private Label mod3Edit;

    @FXML
    private ChoiceBox<String> mod1ChoiceEdit;

    @FXML
    private ChoiceBox<String> mod2ChoiceEdit;

    @FXML
    private ChoiceBox<String> mod3ChoiceEdit;

    //the modChoices variables correspond to the []
    private String mod1S, mod2S, mod3S;

    private String modChoices[] = {"OOP", "Data Algo", "DS", "Maths", "AI",
            "Adv Programming", "Project"};

    public void initialize() {

        /* TODO: add all the modChoices to each ChoiceBox
         */
        //insert 3 lines of code here
        mod1ChoiceEdit.getItems().addAll(modChoices);
        mod2ChoiceEdit.getItems().addAll(modChoices);
        mod3ChoiceEdit.getItems().addAll(modChoices);
        //these lines have been given to you includes the setOnAction if a ChoiceBox is selected
        mod1ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod2ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod3ChoiceEdit.setOnAction(this::getChoiceEdit);
    }

    //to ensure that detail pops up to edit
    public void setToEdit(Student stu) {
        /* TODO: display the student to be edited details
         */
        //insert 4 lines of code
        yearStudyDisplay.setText(stu.getYearOfStudy());
        mod1Edit.setText(stu.getModule1());
        mod2Edit.setText(stu.getModule2());
        mod3Edit.setText(stu.getModule3());
        /* TODO: get the new module choices using mod1S, mod2S and mod3S
         */
        mod1S = mod1ChoiceEdit.getValue();
        mod2S = mod2ChoiceEdit.getValue();
        mod3S = mod3ChoiceEdit.getValue();
    }

    public Student processEdit(Student stu) {
        /* TODO: complete the observableIst statement by getting all students from StudentData
                 to be collected in allStudents
         */
        ObservableList<Student> allStudents = StudentData.getInstance().getStudents();
        /* TODO: get studId and year of study
         */
        /*String studIdS = null;
        String yearStudyS = null;*/
        String studIdS = stu.getStudId();
        String yearStudyS = stu.getYearOfStudy();
        /* TODO: remove the student to be edited from allStudents
         */
        //insert the line to remove the student here
        allStudents.remove(stu);
        StudentData.getInstance().addStudentData(stu);
        /* TODO: add the student back by creating a new object reference and calling the addStudentData()
         */
        Student changedStu = new Student(studIdS,yearStudyS,mod1S,mod2S,mod3S);
        //call the addStudentData()
        /* TODO: return the newly edited student back to the Controller class / editStudent()
         */
        //StudentData.getInstance().addStudentData(changedStu);
        return changedStu;
    }

    public void getChoiceEdit(ActionEvent event) {
        /* TODO: complete the 3 if... statements using event
         */
        if(!(mod1ChoiceEdit.getSelectionModel().isEmpty())) {
            mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();
        }else{
            mod1S=mod1Edit.getText();
        }
        if(!(mod2ChoiceEdit.getSelectionModel().isEmpty())){
            mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();
        }else{
            mod2S=mod2Edit.getText();
        }
        if(!(mod3ChoiceEdit.getSelectionModel().isEmpty())){
            mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();
        }else{
            mod3S=mod3Edit.getText();
        }
    }
}
