package ua.edu.sumdu.j2se.lytvynenko.tasks;

public class LinkedTaskList {

    private static final int increaseInterval = 5;
    private Task[] tasks = new Task[increaseInterval];
    private int size = 0;

    /**
     * Add the specified task to the list.
     * @param task specified task
     */
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        if (size < tasks.length) {
            tasks[size] = task;
        } else {
            Task[] tempArray = new Task[size + increaseInterval];
            for (int i = 0; i < size; i++) {
                tempArray[i] = tasks[i];
            }
            tempArray[size] = task;
            tasks = tempArray;
        }
        size++;
    }

    /**
     * Remove a task from the list and returns the truth if such a task was in the list.
     * If there are several such tasks in the list, the method deletes the first one.
     */
    public boolean remove(Task task) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (tasks[i].equals(task)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            Task[] tempArray;
            if (size - 1 == tasks.length - increaseInterval) {
                tempArray = new Task[size - 1];
            } else {
                tempArray = new Task[tasks.length];
            }
            for (int i = 0; i < index; i++) {
                tempArray[i] = tasks[i];
            }
            for (int i = index + 1; i < size; i++) {
                tempArray[i - 1] = tasks[i];
            }
            tasks = tempArray;
            size--;
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) {
        if (index >= tasks.length) {
            throw new IndexOutOfBoundsException();
        }
        return tasks[index];
    }

    /**
     * Return a list of tasks that are scheduled to run
     * at least once after time from and no later than to.
     */
    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList result = new LinkedTaskList();
        for (int i = 0; i < size; i++) {
            if ((tasks[i].nextTimeAfter(from) >= from) && (tasks[i].nextTimeAfter(from) <= to)) {
                result.add(tasks[i]);
            }
        }
        return result;
    }
}
