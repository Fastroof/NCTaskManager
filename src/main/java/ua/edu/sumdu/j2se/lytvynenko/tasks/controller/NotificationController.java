package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.scene.control.Alert;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModelImplementation;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationController {

    private static final Logger log = Logger.getLogger(NotificationController.class);
    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static final NCTaskManagerModel model = NCTaskManagerModelImplementation.getInstance();

    public static void startCheckTasksThread() {
        log.info("Start notification manager");
        executorService.scheduleAtFixedRate(() -> {
            String currentTasks = model.getCalendarForNextMinuteString();
            if (!currentTasks.isEmpty()) {
                showNotification(currentTasks);
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public static void stopCheckTasksThread() {
        executorService.shutdownNow();
        SystemTray tray = SystemTray.getSystemTray();
        tray.remove(trayIcon);
        log.info("Stop notification manager");
    }

    private static final Image image = Toolkit.getDefaultToolkit().getImage(NotificationController.class.getClassLoader().getResource("icon/logo.png"));
    private static final TrayIcon trayIcon = new TrayIcon(image, "NCTaskManager");
    private static boolean initialization;

    private static void showNotification(String text) {
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

    public static void showErrorAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(title);
        alert.setContentText(text);
        alert.setResizable(false);
        alert.showAndWait();
    }
}
