package ua.edu.sumdu.j2se.lytvynenko.tasks;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.lytvynenko.tasks.controller.JavaFXFunctions;
import ua.edu.sumdu.j2se.lytvynenko.tasks.controller.NotificationController;
import org.apache.log4j.Logger;

public class NCTaskManager extends Application {

    private static final Logger log = Logger.getLogger(NCTaskManager.class);

    public static void main(String[] args) {
        log.info("Running program ...");
        NotificationController.startCheckTasksThread();
        log.info("Launch NCTaskManager");
        launch(args);
        log.info("NCTaskManager closed");
        NotificationController.stopCheckTasksThread();
        log.info("The program is finished");
    }

    @Override
    public void start(Stage primaryStage) {
        JavaFXFunctions.switchTo("views/main_menu.fxml", primaryStage, "NCTaskManager:MainMenu", true);
    }
}
