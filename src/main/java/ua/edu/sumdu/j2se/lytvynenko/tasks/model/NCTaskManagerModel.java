package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public interface NCTaskManagerModel {

    String getCalendarForNextMinuteString();

    SortedMap<LocalDateTime, Set<Task>> getCalendar(LocalDateTime from, LocalDateTime to);

    void deleteTask(Task task);

    AbstractTaskList getTasks();

    void addTask(Task task);

    Task getEditedTask();

    void setEditedTask(Task editedTask);

    Task getTempTask();

    void createTempTask(String title, LocalDateTime startTime);

    void setTempTask(Task task);
}
