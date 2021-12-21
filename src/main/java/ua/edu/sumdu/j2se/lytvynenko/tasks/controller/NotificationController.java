package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.scene.control.Alert;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModelImplementation;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class creates a thread that checks the arrival of the task.
 * It also shows an error notification.
 */
public class NotificationController {

    private static NotificationController instance;

    private NotificationController() {}

    public static NotificationController getInstance() {
        if (instance == null) {
            instance = new NotificationController();
        }
        return instance;
    }

    private final Logger log = Logger.getLogger(NotificationController.class);
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final NCTaskManagerModel model = NCTaskManagerModelImplementation.getInstance();

    /**
     * This method run the thread for checking incoming tasks
     */
    public void startCheckTasksThread() {
        log.info("Start notification manager");
        executorService.scheduleAtFixedRate(() -> {
            String currentTasks = model.getCalendarForNextSomeTimeString();
            if (!currentTasks.isEmpty()) {
                showNotification(currentTasks);
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    /**
     * This method stop the thread for checking incoming tasks
     */
    public void stopCheckTasksThread() {
        executorService.shutdownNow();
        SystemTray tray = SystemTray.getSystemTray();
        tray.remove(trayIcon);
        log.info("Stop notification manager");
    }

    private final Image image = Toolkit.getDefaultToolkit().getImage(NotificationController.class.getClassLoader().getResource("icon/logo.png"));
    private final TrayIcon trayIcon = new TrayIcon(image, "NCTaskManager");
    private boolean initialization;

    /**
     * This method show notification about incoming tasks
     *
     * @param text list of incoming tasks as a string
     */
    private void showNotification(String text) {
        if (!initialization) {
            SystemTray tray = SystemTray.getSystemTray();
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                log.error("Adding trayIcon failed", e);
            }
            initialization = true;
        }
        String[] lines = text.split("\\n");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            if ((i != 0) && (i % 4 == 0)) {
                trayIcon.displayMessage("Oh, these tasks need to be done soon",
                        sb.toString(), TrayIcon.MessageType.NONE);
                sb.setLength(0);
                sb.append(lines[i]).append("\n");
            } else if (i == lines.length - 1) {
                sb.append(lines[i]).append("\n");
                trayIcon.displayMessage("Oh, these tasks need to be done soon",
                        sb.toString(), TrayIcon.MessageType.NONE);
            } else {
                sb.append(lines[i]).append("\n");
            }
        }
    }

    /**
     * This method show error alert.
     *
     * @param title title of alert
     * @param text text of alert
     */
    public void showErrorAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(title);
        alert.setContentText(text);
        alert.setResizable(false);
        alert.showAndWait();
    }
}
