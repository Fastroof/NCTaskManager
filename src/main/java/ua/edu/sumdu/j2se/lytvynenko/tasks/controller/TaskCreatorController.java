package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class TaskCreatorController {

    @FXML private TextField titleTextField;

    @FXML private DatePicker startTimeDatePicker;
    @FXML private TextField startTimeHoursTextField;
    @FXML private TextField startTimeMinutesTextField;
    @FXML private TextField startTimeSecondsTextField;

    @FXML private CheckBox setRepeatableCheckBox;
    @FXML private VBox endTimeAndIntervalVBox;

    @FXML private DatePicker endTimeDatePicker;
    @FXML private TextField endTimeHoursTextField;
    @FXML private TextField endTimeMinutesTextField;
    @FXML private TextField endTimeSecondsTextField;

    @FXML private TextField intervalHoursTextField;
    @FXML private TextField intervalMinutesTextField;
    @FXML private TextField intervalSecondsTextField;

    @FXML private CheckBox setActiveCheckBox;

    public void addNewTask(ActionEvent actionEvent) {
        String title = titleTextField.getText();
        LocalDateTime startTime = getLocalDateTimeFromElements(startTimeDatePicker, startTimeHoursTextField,
                startTimeMinutesTextField, startTimeSecondsTextField);
        Task tempTask = new Task(title, startTime);
        if (setRepeatableCheckBox.isSelected()) {
            LocalDateTime endTime = getLocalDateTimeFromElements(endTimeDatePicker, endTimeHoursTextField,
                    endTimeMinutesTextField, endTimeSecondsTextField);
            long intervalHours = Integer.parseInt(intervalHoursTextField.getText());
            long intervalMinutes = Integer.parseInt(intervalMinutesTextField.getText());
            long intervalSeconds = Integer.parseInt(intervalSecondsTextField.getText());
            Duration interval = Duration.ofSeconds(intervalHours*3600 + intervalMinutes*60 + intervalSeconds);
            tempTask.setTime(startTime, endTime, interval);
        }
        tempTask.setActive(setActiveCheckBox.isSelected());
        NCTaskManagerModel.addTask(tempTask);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private LocalDateTime getLocalDateTimeFromElements(DatePicker dp, TextField tfH, TextField tfM, TextField tfS) {
        int tH = Integer.parseInt(tfH.getText());
        int tM = Integer.parseInt(tfM.getText());
        int tS = Integer.parseInt(tfS.getText());
        return dp.getValue().atTime(tH, tM, tS);
    }

    public void setRepeatable() {
        endTimeAndIntervalVBox.setDisable(!setRepeatableCheckBox.isSelected());
    }

}
