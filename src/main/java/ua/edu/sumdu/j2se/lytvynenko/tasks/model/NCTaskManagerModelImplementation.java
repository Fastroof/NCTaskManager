package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class NCTaskManagerModelImplementation implements NCTaskManagerModel {

    private static NCTaskManagerModelImplementation instance;
    private final AbstractTaskList tasks;

    private NCTaskManagerModelImplementation() {
        tasks = parseTasks();
    }

    public static NCTaskManagerModelImplementation getInstance() {
        if (instance == null) {
            instance = new NCTaskManagerModelImplementation();
        }
        return instance;
    }

    private AbstractTaskList parseTasks() {
        AbstractTaskList result = new ArrayTaskList();
        InputStream is = NCTaskManagerModel.class.getClassLoader().getResourceAsStream("save/savedTasks");
        TaskIO.read(result, is);
        return result;
    }

    private void saveTasks() {
        File saveFile = new File(Objects.requireNonNull(NCTaskManagerModel.class.getClassLoader().getResource("save/savedTasks")).getPath());
        TaskIO.writeBinary(tasks,saveFile);
    }

    public String getCalendarForNextMinuteString() {
        SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(tasks, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        return map.keySet().stream()
                .map(key -> key.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\t"
                        + map.get(key).stream().map(Task::getTitle).collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public SortedMap<LocalDateTime, Set<Task>> getCalendar(LocalDateTime from, LocalDateTime to) {
        return Tasks.calendar(tasks, from, to);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
        saveTasks();
    }

    public AbstractTaskList getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    private Task editedTask;

    public Task getEditedTask() {
        return editedTask;
    }

    public void setEditedTask(Task editedTask) {
        this.editedTask = editedTask;
    }
}
