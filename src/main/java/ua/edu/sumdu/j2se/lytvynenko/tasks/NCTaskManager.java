package ua.edu.sumdu.j2se.lytvynenko.tasks;

import ua.edu.sumdu.j2se.lytvynenko.tasks.controller.NotificationController;
import ua.edu.sumdu.j2se.lytvynenko.tasks.view.NCTaskManagerView;

public class NCTaskManager {
    public static void main(String[] args) {
        NotificationController.startCheckTasksThread();
        NCTaskManagerView view = new NCTaskManagerView();
        view.showNCTaskManager(args);
        NotificationController.stopCheckTasksThread();
    }
}
