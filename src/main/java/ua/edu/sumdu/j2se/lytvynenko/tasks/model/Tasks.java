package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        if (tasks == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }
        return () -> StreamSupport.stream(tasks.spliterator(), false)
                .filter(task -> task.nextTimeAfter(start) != null)
                .filter(task -> !start.isAfter(task.nextTimeAfter(start)) && !end.isBefore(task.nextTimeAfter(start)))
                .iterator();
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();
        Iterable<Task> taskIterable = incoming(tasks, start, end);
        for (Task task : taskIterable) {
            if (task.isRepeated()) {
                for (LocalDateTime i = task.nextTimeAfter(start); !i.isAfter(end); i = i.plusSeconds(task.getRepeatInterval().getSeconds())) {
                    addTaskToCalendar(task, calendar, i);
                }
            } else {
                addTaskToCalendar(task, calendar, task.getTime());
            }
        }
        return calendar;
    }

    private static void addTaskToCalendar(Task task, SortedMap<LocalDateTime, Set<Task>> calendar, LocalDateTime time){
        Set<Task> taskSet = calendar.computeIfAbsent(time, k -> new HashSet<>());
        taskSet.add(task);
    }
}
