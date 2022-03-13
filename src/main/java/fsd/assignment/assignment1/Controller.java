package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class Controller {

    //these variables correspond to the <top> of main-view.fxml
    @FXML
    private TextField studId;

    @FXML
    private TextField yearStudy;

    @FXML
    private ChoiceBox<String> mod1Choice;

    @FXML
    private ChoiceBox<String> mod2Choice;

    @FXML
    private ChoiceBox<String> mod3Choice;

    private String choice1, choice2, choice3;

    private String modChoices[] = {"OOP", "Data Algo", "DS", "Maths", "AI",
            "Adv Programming", "Project"};

    @FXML
    private Label validateStudent; //remember this is the Label that you only see when there is an invalid "add"

    //validateStudent is the last element corresponding to <top>

    //these variables correspond to the <left> i.e. the studentListView
    @FXML
    private ListView<Student> studentListView;

    //these variables correspond to the <bottom> part of the border
    @FXML
    private Label yearStudyView;

    @FXML
    private Label mod1View;

    @FXML
    private Label mod2View;

    @FXML
    private Label mod3View;

    //mod3View is the last element for the bottom part of the border

    //the contextMenu is used for the right-click regarding Edit / Delete
    @FXML
    private ContextMenu listContextMenu;

    //this variable is used when switching windows from add to edit
    @FXML
    private BorderPane mainWindow;

    //used to add a student to the ArrayList for addStudentData()
    public Student studentToAdd;


    public void initialize() {
        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                /* TODO: complete the code so that if a studId is selected then the changed()
                         should ensure that the other fields related to the selected item appear at the bottom of the window
                */
                if(newValue != null) {
                    yearStudyView.setText(newValue.getYearOfStudy());
                    mod1View.setText(newValue.getModule1());
                    mod2View.setText(newValue.getModule2());
                    mod3View.setText(newValue.getModule3());
                }
            }
        });
        //the setOnAction ensures that when a ChoiceBox is selected the getChoice() grabs the selected choice
        //mod1Choice.setOnAction(this::getChoice());
        mod1Choice.setOnAction(this::getChoice);
        mod2Choice.setOnAction(this::getChoice);
        mod3Choice.setOnAction(this::getChoice);
        /*mod1Choice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getChoice(actionEvent);
            }
        });*/
        // mod1Choice.setOnAction(actionEvent -> getChoice(actionEvent));
        /* TODO: the array declared above for modChoices must be added to each Choicebox
                 include the code here to addAll()
        */
        //insert the code to addAll() the modChoices [] to each ChoiceBox here
        mod1Choice.getItems().addAll(modChoices);
        mod2Choice.getItems().addAll(modChoices);
        mod3Choice.getItems().addAll(modChoices);
        //deleting a student
        /* TODO: create a new listContextMenu -> defined above in the variables
        */
        listContextMenu = new ContextMenu();


        /* TODO: create a MenuItem object so that when the user right-clicks a studId
                 the word Delete? appears
         */
        // TODO: 2022/3/9 贴贴贴？
        MenuItem deleteStudent = new MenuItem("Delete?");
        // listContextMenu.getItems().add(deleteStudent);
        // studentListView.setContextMenu(listContextMenu);

        deleteStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /* TODO: get the item to be deleted and call the deleteStudent()
                 */
                Student item = studentListView.getSelectionModel().getSelectedItem();
                deleteStudent(item);
            }
        });

        //editing a student
        /* TODO: create a new listContextMenu -> defined above in the variables
         */
        //listContextMenu = null;

        /* TODO: create a MenuItem object so that when the user right-clicks a studId
                 the word Edit?? appears
         */
        MenuItem editStudent = new MenuItem("Edit??");

        editStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /* TODO: get the item to be edited and call the editStudent()
                */
                Student item = studentListView.getSelectionModel().getSelectedItem();
                editStudent(item);
            }
        });

        //code provided to ensure that contextMenu appears as part of the above actions
        listContextMenu.getItems().add(deleteStudent);
        listContextMenu.getItems().add(editStudent);
        studentListView.setContextMenu(listContextMenu);
        //to ensure access to a particular cell in the studentListView
        studentListView.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            public ListCell<Student> call(ListView<Student> param) {
                ListCell<Student> cell = new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student stu, boolean empty) {
                        /* TODO: ensure that the studentListView has studId's or not when
                                 the delete a student takes place

                         */
                        super.updateItem(stu,empty);
                        /*if (empty){
                            setText(null);
                        }*/
                        if(empty){
                            setText("");
                        }else {
                            setText(stu.getStudId());
                        }
                    }//end of update()
                };
                //code included as part of the delete
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                return cell;
            }
        }); //end of setting the cell factory

        /* TODO: ensure that the studId's are sorted according to year of study in ascending order
        */
        Comparator<Student> comparator = Comparator.comparing(Student::getYearOfStudy);

        SortedList<Student> sortedByYear = new SortedList<>(StudentData.getInstance().getStudents(),comparator);
        /*SortedList<Student> sortedByYear = new SortedList<>(studentListView.getItems(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (Integer.parseInt(o1.getYearOfStudy())== Integer.parseInt(o2.getYearOfStudy())){
                    return 0;
                }else if (Integer.parseInt(o1.getYearOfStudy())> Integer.parseInt(o2.getYearOfStudy())){
                    return 1;
                }else return -1;
            }
        });*/

        /* TODO: step 1 - set items using the sorted list
                 step 2 - ensure that only one studId can be selected at one time
                 step 3 - ensure that the first studId is highlighted when the application commences
         */
        studentListView.getItems().addAll(sortedByYear);
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentListView.getSelectionModel().selectFirst();
    }

    public void getChoice(ActionEvent event) {
        /* TODO: make use of event to determine each choice and assign each module choice to
                 choice1, choice2 and choice3
         */

        choice1 = mod1Choice.getValue();
        choice2 = mod2Choice.getValue();
        choice3 = mod3Choice.getValue();

    }


    @FXML
    public void addStudentData() {
        /* TODO: get the values from the textfields
         */
        String studIdS = studId.getText();
        String yearStudyS = yearStudy.getText();

        /* TODO: validate whether the studIdS and yearStudyS are occupied, BOTH have to be occupied
                 for the add to take place, if one or both are unoccupied print the following message
                 in the validateStudent label -> message to be printed is
                 "Error: cannot add student if studId or year of study not filled in"
                 If both are occupied then go ahead with the addStudentData()
         */
        //do the if...here
        /*StudentData studentData = StudentData.getInstance();
        try {
            studentData.loadStudentData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Student> students = studentData.getStudents();*/
        /*Iterator<Student> iterator = studentListView.getItems().iterator();*/
        /*f (iterator.hasNext()){
            Student item = iterator.next();
            if (studIdS.equals(item.getStudId())&&yearStudyS.equals(item.getYearOfStudy())){
                validateStudent.setText(null);
                studentToAdd = item;
                studentListView.getItems().add(studentToAdd);
                studentListView.getSelectionModel().select(item);
            }else {
                validateStudent.setText("Error: cannot add student if studId or year of study not filled in");
            }
        }*/
        if (!studIdS.trim().equals("") && !yearStudyS.trim().equals("")){
            validateStudent.setText(null);
            studentToAdd = new Student(studIdS,yearStudyS,choice1,choice2,choice3);
            studentListView.getItems().add(studentToAdd);
            //add
            StudentData.getInstance().addStudentData(studentToAdd);
            studentListView.getSelectionModel().select(studentToAdd);
        }else {
            validateStudent.setText("Error: cannot add student if studId or year of study not filled in");
        }
        //do the else...here, first ensure that the validateStudent label is clear of any text
            //studentToAdd = null;
        //use the getInstance() to addStudentData()
        //select the student that has been added so that it is highlighted on the list
    }

    public void deleteStudent(Student stu) {
        /* TODO: create an alert object to confirm that a user wants to delete
         */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        /* TODO: set the title with "Delete a student from the list"
         */
        //insert the line of code here
        alert.setTitle("Delete a student from the list");
        /* TODO: set the header text with "Deleting student " xxx - where xxx is the studId
         */
        //insert the line of code here
        alert.setHeaderText("Deleting student "+stu.getStudId());
        /* TODO: have a message that asks the user "Are you sure you want to delete the student?"
         */
        //insert the line of code here
        alert.setContentText("Are you sure you want to delete the student?");
        //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = alert.showAndWait();
        /* TODO: include a test to verify if the result is present and whether the OK button was
                 pressed, if so go ahead and call the deleteStudent()
         */
        //insert the 2 lines of code here
        if (result.isPresent()&&result.get().equals(ButtonType.OK)){
            studentListView.getItems().remove(stu);
            StudentData.getInstance().deleteStudent(stu);
        }
    }

    public void editStudent(Student stu) {
        //the dialog object has been completed for you
        Dialog<ButtonType> dialog = new Dialog<>();
        /* TODO: use the dialog object to set the owner, the title and the header text
                 the title must state "Edit a student's details"
                 the header text must state: Editing student Id: xxx - where xxx is the studId
         */
         //insert the 3 lines of code here
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a student's details");
        dialog.setHeaderText("Editing student Id: "+stu.getStudId());
        /* TODO: complete the FXMLLoader statement
         */
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("edit-students.fxml"));
        /* TODO: use the fxmlLoader to set the edit-students.fxml
         */

        //insert the line of code here
        //remove the comments and complete the try...catch
        try {
            /* TODO: load the fxml
             */
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException event) {
            /* TODO: print an appropriate message if it cannot be loaded
                     print stacktrace
             */
            System.out.println("Cannot load the fxml file!");
            event.printStackTrace();
            return;
        }
        /* TODO: complete the ec controller statement
             */
        EditStudentController ec = fxmlLoader.getController();
        /* TODO: use the ec object to call setToEdit()
             */
        //insert the line of code here
        ec.setToEdit(stu);
        /* TODO: create the OK and CANCEL buttons using dialog
         */
        //insert the 2 lines of code here
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
       //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = dialog.showAndWait();

         /* TODO: verify if there is an edit to complete, complete the editStudent call processEdit()
                  ensure that the student edited is selected
         */
        //remove the comments and complete the if...
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            Student editStudent = ec.processEdit(stu);
            studentListView.getItems().remove(stu);
            studentListView.getItems().add(editStudent);
            //tudent changedStudent = ec.processEdit(editStudent);
            //select the edited studId here
            /*StudentData.getInstance().addStudentData(editStudent);
            studentListView.getItems().add(editStudent);*/
            studentListView.getSelectionModel().select(editStudent);
        }
    }
}