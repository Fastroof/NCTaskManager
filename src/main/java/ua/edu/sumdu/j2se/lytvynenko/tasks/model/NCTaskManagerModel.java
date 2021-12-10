package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

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

    public static String getCalendarForNextMinuteString() {
        SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(tasks, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        return map.keySet().stream()
                .map(key -> key.toLocalTime() + "\t" + map.get(key).stream().map(Task::getTitle).collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n"));
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
