package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Model interface
 */
public interface NCTaskManagerModel {

    /**
     * This method gets calendar from current time to next time from tasks list
     *
     * @return calendar for next time
     */
    String getCalendarForNextSomeTimeString();

    /**
     * This method gets calendar from some date to another from tasks list
     *
     * @param from start date
     * @param to end date
     * @return calendar of tasks
     */
    SortedMap<LocalDateTime, Set<Task>> getCalendar(LocalDateTime from, LocalDateTime to);

    /**
     * This method delete task from task list
     *
     * @param task task what will be deleted
     */
    void deleteTask(Task task);

    /**
     * This method return tasks list
     *
     * @return tasks list
     */
    AbstractTaskList getTasks();

    /**
     * This method add task to task list
     *
     * @param task task what will be added
     */
    void addTask(Task task);

    Task getEditedTask();

    void setEditedTask(Task editedTask);

    Task getTempTask();

    void createTempTask(String title, LocalDateTime startTime);

    void setTempTask(Task task);
}
