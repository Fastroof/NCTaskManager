package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.io.File;

public class NCTaskManagerModel {
    private static AbstractTaskList tasks = parseTasks();

    private static AbstractTaskList parseTasks() {
        AbstractTaskList result = new ArrayTaskList();
        File parseFile = new File("src/main/java/ua/edu/sumdu/j2se/lytvynenko/tasks/saves/savedTasks");
        TaskIO.readBinary(result, parseFile);
        return result;
    }

    private static void saveTasks() {
        File saveFile = new File("src/main/java/ua/edu/sumdu/j2se/lytvynenko/tasks/saves/savedTasks");
        TaskIO.writeBinary(tasks,saveFile);
    }

    public static void deleteTask(Task task) {
        tasks.remove(task);
        saveTasks();
    }

    public static AbstractTaskList getTasks() {
        return tasks;
    }

    public static void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    private static Task editedTask;

    public static Task getEditedTask() {
        return editedTask;
    }

    public static void setEditedTask(Task editedTask) {
        NCTaskManagerModel.editedTask = editedTask;
    }
}
