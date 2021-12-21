package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModelImplementation;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * This class is the controller for the task_manager.fxml view.
 */
public class TaskCreatorEditorController implements Initializable {

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
    @FXML public Button eventButton;

    private final ValidationSupport vs = new ValidationSupport();
    private final NCTaskManagerModel model = NCTaskManagerModelImplementation.getInstance();
    private final NotificationController notificationController = NotificationController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vs.registerValidator(titleTextField, true, Validator.createEmptyValidator("Text is required", Severity.ERROR));
        registerValidatorForDateTimePickers(startTimeDatePicker, startTimeHoursTextField, startTimeMinutesTextField, startTimeSecondsTextField);
        registerValidatorForDateTimePickers(endTimeDatePicker, endTimeHoursTextField, endTimeMinutesTextField, endTimeSecondsTextField);
        vs.registerValidator(intervalHoursTextField, Validator.createRegexValidator("Must be digit", "^\\d+$", Severity.ERROR));
        vs.registerValidator(intervalMinutesTextField, Validator.createRegexValidator("Must be digit", "^\\d+$", Severity.ERROR));
        vs.registerValidator(intervalSecondsTextField, Validator.createRegexValidator("Must be digit", "^\\d+$", Severity.ERROR));
        vs.redecorate();
        if (model.getEditedTask() != null) {
            titleTextField.setText(model.getEditedTask().getTitle());
            startTimeDatePicker.setValue(model.getEditedTask().getStartTime().toLocalDate());
            LocalTime startLocalTime = model.getEditedTask().getStartTime().toLocalTime();
            startTimeHoursTextField.setText(String.valueOf(startLocalTime.getHour()));
            startTimeMinutesTextField.setText(String.valueOf(startLocalTime.getMinute()));
            startTimeSecondsTextField.setText(String.valueOf(startLocalTime.getSecond()));
            if (model.getEditedTask().isRepeated()) {
                setRepeatableCheckBox.setSelected(true);
                setRepeatable();
                endTimeDatePicker.setValue(model.getEditedTask().getEndTime().toLocalDate());
                LocalTime endLocalTime = model.getEditedTask().getEndTime().toLocalTime();
                endTimeHoursTextField.setText(String.valueOf(endLocalTime.getHour()));
                endTimeMinutesTextField.setText(String.valueOf(endLocalTime.getMinute()));
                endTimeSecondsTextField.setText(String.valueOf(endLocalTime.getSecond()));
                long interval = model.getEditedTask().getRepeatInterval().getSeconds();
                String hour = String.valueOf((int) (interval / 3600));
                String minute = String.valueOf((int) (interval / 60));
                String second = String.valueOf(interval % 60);
                intervalHoursTextField.setText(hour);
                intervalMinutesTextField.setText(minute);
                intervalSecondsTextField.setText(second);
            }
            setActiveCheckBox.setSelected(model.getEditedTask().isActive());
        } else {
            eventButton.setText("Add new tasks");
        }
    }

    private void registerValidatorForDateTimePickers(DatePicker dp, TextField tfH, TextField tfM, TextField tfS) {
        vs.registerValidator(dp, Validator.createEmptyValidator("Date required", Severity.ERROR));
        vs.registerValidator(tfH, Validator.createRegexValidator(
                "Hour must be from 0 to 23", "\\b2[0-3]\\b|\\b[0-1]?[0-9]\\b", Severity.ERROR));
        vs.registerValidator(tfM, Validator.createRegexValidator(
                "Minute must be from 0 to 59", "^[0-5]?[0-9]$", Severity.ERROR));
        vs.registerValidator(tfS, Validator.createRegexValidator(
                "Second must be from 0 to 59", "^[0-5]?[0-9]$", Severity.ERROR));
    }

    /**
     * This method processes all the required fields, pickers when the button is clicked
     */
    public void onAction() {
        try {
            String title = titleTextField.getText();
            if (title.isEmpty()) {
                throw new IllegalArgumentException("Title is empty");
            }
            LocalDateTime startTime = getLocalDateTimeFromElements(startTimeDatePicker, startTimeHoursTextField,
                    startTimeMinutesTextField, startTimeSecondsTextField);
            model.createTempTask(title, startTime);
            if (setRepeatableCheckBox.isSelected()) {
                LocalDateTime endTime = getLocalDateTimeFromElements(endTimeDatePicker, endTimeHoursTextField,
                        endTimeMinutesTextField, endTimeSecondsTextField);
                long intervalHours = Integer.parseInt(intervalHoursTextField.getText());
                long intervalMinutes = Integer.parseInt(intervalMinutesTextField.getText());
                long intervalSeconds = Integer.parseInt(intervalSecondsTextField.getText());
                Duration interval = Duration.ofSeconds(intervalHours * 3600 + intervalMinutes * 60 + intervalSeconds);
                if (interval.isZero()) {
                    throw new IllegalArgumentException("Wrong interval");
                }
                model.getTempTask().setTime(startTime, endTime, interval);
            }
            model.getTempTask().setActive(setActiveCheckBox.isSelected());
            if (eventButton.getText().equals("Add new tasks")) {
                model.addTask(model.getTempTask());
            } else {
                model.deleteTask(model.getEditedTask());
                model.addTask(model.getTempTask());
            }
            model.setTempTask(null);
            Stage stage = (Stage) eventButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            notificationController.showErrorAlert("Wrong values of fields","Check the validation conditions.\n"
                    + "To do this, point at the red circle with the cross"
            );
        }
    }

    private LocalDateTime getLocalDateTimeFromElements(DatePicker dp, TextField tfH, TextField tfM, TextField tfS) {
        int tH = Integer.parseInt(tfH.getText());
        int tM = Integer.parseInt(tfM.getText());
        int tS = Integer.parseInt(tfS.getText());
        return dp.getValue().atTime(tH, tM, tS);
    }

    /**
     * Make fields available for a recurring task
     */
    public void setRepeatable() {
        endTimeAndIntervalVBox.setDisable(!setRepeatableCheckBox.isSelected());
    }
}
