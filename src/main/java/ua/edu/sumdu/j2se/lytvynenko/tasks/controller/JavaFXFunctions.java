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

/**
 * This class contains various methods for working with javafx.
 */
public class JavaFXFunctions {

    private static final Logger log = Logger.getLogger(JavaFXFunctions.class);

    /**
     * This method changes the scene on the stage to the one specified in the parameters.
     *
     * @param fileName name of the fxml view file
     * @param stage the stage in which the scene will change
     * @param title title of the stage
     * @param onlyShow if true - only show stage, else - show stage and wait closing
     */
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
            log.error("Switching failed", e);
        }
    }
}
