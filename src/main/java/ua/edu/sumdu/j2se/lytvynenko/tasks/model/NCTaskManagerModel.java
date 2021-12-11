package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class NCTaskManagerModel {
    private static final AbstractTaskList tasks = parseTasks();

    private static AbstractTaskList parseTasks() {
        AbstractTaskList result = new ArrayTaskList();
        InputStream is = NCTaskManagerModel.class.getClassLoader().getResourceAsStream("save/savedTasks");
        TaskIO.read(result, is);
        return result;
    }

    private static void saveTasks() {
        File saveFile = new File(Objects.requireNonNull(NCTaskManagerModel.class.getClassLoader().getResource("save/savedTasks")).getPath());
        TaskIO.writeBinary(tasks,saveFile);
    }

    public static String getCalendarForNextMinuteString() {
        SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(tasks, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        return map.keySet().stream()
                .map(key -> key.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\t"
                        + map.get(key).stream().map(Task::getTitle).collect(Collectors.joining(", ")))
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
