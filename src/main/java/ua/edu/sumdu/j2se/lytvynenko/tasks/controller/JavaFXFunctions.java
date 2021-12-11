package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.lytvynenko.tasks.view.NCTaskManagerView;

import java.io.IOException;
import java.util.Objects;

public class JavaFXFunctions {
    public void switchTo(String fileName, Stage stage, String title, boolean onlyShow) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.getIcons().add(new Image(Objects.requireNonNull(
                NCTaskManagerView.class.getClassLoader().getResourceAsStream("icon/logo.png"))));
        stage.setScene(scene);
        if (onlyShow) {
            stage.show();
        } else {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }
}
