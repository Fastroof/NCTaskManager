package ua.edu.sumdu.j2se.lytvynenko.tasks;

public class Task {

    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    private boolean repeat;

    /**
     * A constructor that constructs an inactive task
     * that runs at a specified time without repetition, with a given name.
     *
     * @param title task name
     * @param time task execution time
     */
    public Task(String title, int time) {
        this.title = title;
        this.time = time;
        this.active = false;
        this.repeat = false;
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
    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = false;
        this.repeat = true;
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
    public int getTime() {
        return (repeat ? start : time);
    }

    /**
     * Set the execution time of a task that is not repeated.
     * If the task is repeated, it becomes non-repetitive.
     */
    public void setTime(int time) {
        this.time = time;
        if (repeat) {
            repeat = false;
        }
    }

    /**
     * Get the start time of a task that is repeated.
     * If the task is not repeated, the method returns the execution time.
     */
    public int getStartTime() {
        return (repeat ? start : time);
    }

    /**
     * Get the end time of a task that is repeated.
     * If the task is not repeated, the method returns the execution time.
     */
    public int getEndTime() {
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
    public void setTime(int start, int end, int interval) {
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
     * If after the specified time the task isn't executed, the method return -1.
     */
    public int nextTimeAfter(int current) {
        int nextTimeAfter = -1;

        if (active) {
            if (repeat) {
                if (current < start) {
                    nextTimeAfter = start;
                } else if (current < end) {
                    for (int i = start + interval; i < end; i += interval) {
                        if (current < i) {
                            nextTimeAfter = i;
                            break;
                        }
                    }
                }
            } else {
                if (current < time) {
                    nextTimeAfter = time;
                }
            }
        }

        return nextTimeAfter;
    }

}
