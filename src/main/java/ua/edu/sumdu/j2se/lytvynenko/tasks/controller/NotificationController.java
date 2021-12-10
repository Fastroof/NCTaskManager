package ua.edu.sumdu.j2se.lytvynenko.tasks.controller;

import org.controlsfx.control.Notifications;
import ua.edu.sumdu.j2se.lytvynenko.tasks.model.NCTaskManagerModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationController {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void startCheckTasksThread() {
        executorService.scheduleAtFixedRate(() -> {
            String currentTasks = NCTaskManagerModel.getCalendarForNextMinuteString();
            displayTray("Oh, these tasks will have to be done soon", currentTasks);
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void stopCheckTasksThread() {
        executorService.shutdownNow();
    }
    
    public void displayTray(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showConfirm();
    }
}
