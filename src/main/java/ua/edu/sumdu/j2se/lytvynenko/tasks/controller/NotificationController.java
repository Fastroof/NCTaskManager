package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import javafx.scene.control.Alert;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationController {

    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static void startCheckTasksThread() {
        executorService.scheduleAtFixedRate(() -> {
            String currentTasks = NCTaskManagerModel.getCalendarForNextMinuteString();
            if (!currentTasks.isEmpty()) {
                showNotification(currentTasks);
            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    public static void stopCheckTasksThread() {
        executorService.shutdownNow();
        SystemTray tray = SystemTray.getSystemTray();
        tray.remove(trayIcon);
    }

    private static final Image image = Toolkit.getDefaultToolkit().createImage("img.png");
    private static final TrayIcon trayIcon = new TrayIcon(image, "NCTaskManager");
    private static boolean initialization;

    private static void showNotification(String text) {
        if (!initialization) {
            SystemTray tray = SystemTray.getSystemTray();
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
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
