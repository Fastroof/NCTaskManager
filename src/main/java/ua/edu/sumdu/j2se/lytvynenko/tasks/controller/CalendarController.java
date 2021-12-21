package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModelImplementation;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.Task;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class is the controller for the calendar.fxml view.
 */
public class CalendarController implements Initializable {

    private final NCTaskManagerModel model = NCTaskManagerModelImplementation.getInstance();

    /**
     * This method changes the scene to the main menu when button was clicked
     *
     * @param event button click event
     */
    public void switchToMainMenu(ActionEvent event) {
        JavaFXFunctions.switchTo("views/main_menu.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow(),
                "NCTaskManager:MainMenu", true);
    }

    @FXML private TableView<SortedMap.Entry<LocalDateTime, Set<Task>>> calendarTableView;
    @FXML private TableColumn<SortedMap.Entry<LocalDateTime, Set<Task>>, String> timeColumn;
    @FXML private TableColumn<SortedMap.Entry<LocalDateTime, Set<Task>>, String> titlesColumn;

    /**
     * This method initialize calendar table columns for correct display.
     */
    private void initializeCalendarTableColumns() {
        timeColumn.setCellValueFactory(
                param -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                    return new SimpleStringProperty(param.getValue().getKey().format(formatter));
                });
        titlesColumn.setCellValueFactory(
                param -> {
                    List<String> titleList = new ArrayList<>();
                    for (Task task : param.getValue().getValue()) {
                        titleList.add(task.getTitle());
                    }
                    return new SimpleStringProperty(String.join(", ", titleList));
                });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCalendarTableColumns();
        LocalDateTime from = LocalDate.now().with(DayOfWeek.MONDAY).atTime(0,0,0,0);
        LocalDateTime to = from.plusDays(7).minusSeconds(1);
        calendarTableView.getItems().setAll(model.getCalendar(from, to).entrySet());
    }

    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;
    private final NotificationController notificationController = NotificationController.getInstance();

    /**
     * This method changes the calendar in the table on stage.
     * If an error occurs, then it raises a notification about it.
     */
    public void showCalendar() {
        try {
            LocalDateTime from = fromDatePicker.getValue().atTime(0,0,0);
            LocalDateTime to = toDatePicker.getValue().atTime(23,59,59);
            calendarTableView.getItems().setAll(model.getCalendar(from, to).entrySet());
        } catch (Exception e) {
            notificationController.showErrorAlert("Wrong values of DataPickers", "Please select dates by clicking on the calendar icon");
        }
    }
}
