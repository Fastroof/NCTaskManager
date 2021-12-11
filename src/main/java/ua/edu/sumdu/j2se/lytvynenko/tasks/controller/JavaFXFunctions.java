package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class JavaFXFunctions {

    private static final Logger log = Logger.getLogger(JavaFXFunctions.class);

    public static void switchTo(String fileName, Stage stage, String title, boolean onlyShow) {
        try {
            log.info("Try load " + title + " view file");
            Parent root = FXMLLoader.load(Objects.requireNonNull(JavaFXFunctions.class.getClassLoader().getResource(fileName)));
            log.info( title + " view file load success");
            Scene scene = new Scene(root);
            log.info("Set stage parameters");
            stage.setTitle(title);
            stage.getIcons().add(new Image(Objects.requireNonNull(JavaFXFunctions.class.getClassLoader().getResourceAsStream("icon/logo.png"))));
            stage.setScene(scene);
            log.info("Parameters are set");
            if (onlyShow) {
                stage.show();
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }
        } catch (IOException e) {
            log.fatal(e.getStackTrace());
        }
    }
}
