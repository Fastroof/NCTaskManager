package ua.edu.sumdu.j2se.lytvynenko.tasks.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class NCTaskManagerView extends Application {

    public void showNCTaskManager(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main_menu.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("NCTaskManager:MainMenu");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
