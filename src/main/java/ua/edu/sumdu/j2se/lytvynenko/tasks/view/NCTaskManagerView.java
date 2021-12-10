package ua.edu.sumdu.j2se.lytvynenko.tasks.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class NCTaskManagerView extends Application {

    public void showNCTaskManager(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            URL sceneUrl = new File("src/main/java/ua/edu/sumdu/j2se/lytvynenko/tasks/view/main_menu.fxml")
                    .toURI().toURL();
            Parent root = FXMLLoader.load(sceneUrl);
            Scene scene = new Scene(root);
            primaryStage.setTitle("NCTaskManager:MainMenu");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
