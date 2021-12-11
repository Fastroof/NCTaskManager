package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModelImplementation;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.Task;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainMenuController implements Initializable {

    private static final JavaFXFunctions fxFunctions = new JavaFXFunctions();
    private static final NCTaskManagerModel model = NCTaskManagerModelImplementation.getInstance();

    public void switchToCalendar(ActionEvent event) throws IOException {
        fxFunctions.switchTo("fxml/calendar.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow(),
                "NCTaskManager:Calendar", true);
    }

    public void switchToTaskCreator() throws IOException {
        Stage stage = new Stage();
        fxFunctions.switchTo("fxml/task_manager.fxml", stage, "TaskCreator", false);
        if (model.getTasks().size() > allTasksTableView.getItems().size()) {
            addTaskToTableView();
        }
    }

    private void switchToTaskEditor(Task item) throws IOException {
        model.setEditedTask(item);
        Stage stage = new Stage();
        fxFunctions.switchTo("fxml/task_manager.fxml", stage, "TaskEditor", false);
        model.setEditedTask(null);
        reloadTableView();
    }

    @FXML private TableView<Task> allTasksTableView;
    @FXML private TableColumn<Task, String> taskTimeColumn;
    @FXML private TableColumn<Task, String> taskRepeatIntervalColumn;
    @FXML private TableColumn<Task, String> taskTitleColumn;
    @FXML private TableColumn<Task, Boolean> taskActiveColumn;

    private void initializeMainMenuTableColumns() {
        taskTimeColumn.setCellValueFactory(
                param -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                    if (param.getValue().isRepeated()) {
                        return new SimpleStringProperty(
                                param.getValue().getStartTime().format(formatter) + " -\n"
                                        + param.getValue().getEndTime().format(formatter));
                    } else {
                        return new SimpleStringProperty(param.getValue().getTime().format(formatter));
                    }
                });
        taskRepeatIntervalColumn.setCellValueFactory(
                param -> {
                    if (param.getValue().isRepeated()) {
                        long s = param.getValue().getRepeatInterval().getSeconds();
                        return new SimpleStringProperty(
                                String.format("Every\n%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));
                    } else {
                        return new SimpleStringProperty("No repeat");
                    }
                });
        taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskActiveColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMainMenuTableColumns();
        allTasksTableView.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    try {
                        switchToTaskEditor(row.getItem());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        allTasksTableView.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                model.deleteTask(allTasksTableView.getSelectionModel().getSelectedItem());
                reloadTableView();
            }
        });
        reloadTableView();
    }

    private void reloadTableView() {
        allTasksTableView.getItems().setAll(model.getTasks().toArray());
    }

    private void addTaskToTableView() {
        allTasksTableView.getItems().add(model.getTasks().getTask(model.getTasks().size()-1));
    }
}
