package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.Task;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.Tasks;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalendarController implements Initializable {

    public void switchToMainMenu(ActionEvent event) throws IOException {
        JavaFXFunctions fxFunctions = new JavaFXFunctions();
        fxFunctions.switchTo("fxml/main_menu.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow(),
                "NCTaskManager:MainMenu", true);
    }

    @FXML private TableView<SortedMap.Entry<LocalDateTime, Set<Task>>> calendarTableView;
    @FXML private TableColumn<SortedMap.Entry<LocalDateTime, Set<Task>>, String> timeColumn;
    @FXML private TableColumn<SortedMap.Entry<LocalDateTime, Set<Task>>, String> titlesColumn;

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
                    return new SimpleStringProperty(String.join(",", titleList));
                });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCalendarTableColumns();
        LocalDateTime from = LocalDate.now().with(DayOfWeek.MONDAY).atTime(0,0,0,0);
        LocalDateTime to = from.plusDays(7).minusSeconds(1);
        SortedMap<LocalDateTime, Set<Task>> sortedMap = Tasks.calendar(NCTaskManagerModel.getTasks(), from, to);
        calendarTableView.getItems().setAll(sortedMap.entrySet());
    }
}
