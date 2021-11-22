package ua.edu.sumdu.j2se.lytvynenko.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable {

    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active = false;
    private boolean repeat = false;

    /**
     * A constructor that constructs an inactive task
     * that runs at a specified time without repetition, with a given name.
     *
     * @param title task name
     * @param time task execution time
     */
    public Task(String title, LocalDateTime time) {
        if (title == null || time == null) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.time = time;
    }


    /**
     * A constructor that constructs an inactive task
     * that runs at a specified time (since start time to end time)
     * at a specified interval and has a specified name.
     *
     * @param title task name
     * @param start start time
     * @param end end time
     * @param interval repeat interval
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        if (title == null || start == null || end == null || !end.isAfter(start) || interval <= 0) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeat = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get the execution time of a task that is not repeated.
     * If the task is repeated, the method returns the start time of the repetition.
     */
    public LocalDateTime getTime() {
        return (repeat ? start : time);
    }

    /**
     * Set the execution time of a task that is not repeated.
     * If the task is repeated, it becomes non-repetitive.
     */
    public void setTime(LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException();
        }
        this.time = time;
        if (repeat) {
            repeat = false;
        }
    }

    /**
     * Get the start time of a task that is repeated.
     * If the task is not repeated, the method returns the execution time.
     */
    public LocalDateTime getStartTime() {
        return (repeat ? start : time);
    }

    /**
     * Get the end time of a task that is repeated.
     * If the task is not repeated, the method returns the execution time.
     */
    public LocalDateTime getEndTime() {
        return (repeat ? end : time);
    }

    /**
     * Get the repeat interval time of a task that is repeated.
     * If the task is not repeated, the method returns 0.
     */
    public int getRepeatInterval() {
        return (repeat ? interval : 0);
    }

    /**
     * Set the start, end, repeat interval time of a task that is repeated.
     * If the task is not repeated, it becomes repeated.
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (start == null || end == null || !end.isAfter(start) || interval <= 0) {
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        if (!repeat) {
            repeat = true;
        }
    }

    public boolean isRepeated() {
        return repeat;
    }

    /**
     * Return the time of the next execution of the task after the current time.
     * If after the specified time the task isn't executed, the method return null.
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null) {
            throw new IllegalArgumentException();
        }
        LocalDateTime nextTimeAfter = null;

        if (active) {
            if (repeat) {
                if (start.isAfter(current)) {
                    nextTimeAfter = start;
                } else if (end.isAfter(current)) {
                    for (LocalDateTime i = start.plusSeconds(interval); !end.isBefore(i); i = i.plusSeconds(interval)) {
                        if (current.isBefore(i)) {
                            nextTimeAfter = i;
                            break;
                        }
                    }
                }
            } else {
                if (time.isAfter(current)) {
                    nextTimeAfter = time;
                }
            }
        }

        return nextTimeAfter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time && start == task.start && end == task.end && interval == task.interval && active == task.active && repeat == task.repeat && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active, repeat);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                ", repeat=" + repeat +
                '}';
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
