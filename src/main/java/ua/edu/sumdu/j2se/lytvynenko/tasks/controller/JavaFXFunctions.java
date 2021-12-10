package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JavaFXFunctions {
    public void switchTo(String fileName, Stage stage, String title, boolean onlyShow) throws IOException {
        String pathToView = "src/main/java/ua/edu/sumdu/j2se/lytvynenko/tasks/view/";
        URL sceneUrl = new File(pathToView + fileName).toURI().toURL();
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        if (onlyShow) {
            stage.show();
        } else {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }
}
