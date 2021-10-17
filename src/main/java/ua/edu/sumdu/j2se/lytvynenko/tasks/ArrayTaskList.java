package ua.edu.sumdu.j2se.lytvynenko.tasks;

public class ArrayTaskList {

    private Task[] tasks;
    private int size;

    /**
     * Add the specified task to the list.
     * @param task specified task
     */
    public void add(Task task) {
        size++;
        Task[] tempArray = new Task[size];
        for (int i = 0; i < size - 1; i++) {
            tempArray[i] = tasks[i];
        }
        tempArray[size - 1] = task;
        tasks = tempArray;
    }

    /**
     * Remove a task from the list and returns the truth if such a task was in the list.
     * If there are several such tasks in the list, the method deletes the first one.
     */
    public boolean remove(Task task) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (tasks[i] == task) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            size--;
            Task[] tempArray = new Task[size];
            for (int i = 0; i < index; i++) {
                tempArray[i] = tasks[i];
            }
            for (int i = index + 1; i < size + 1; i++) {
                tempArray[i - 1] = tasks[i];
            }
            tasks = tempArray;
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) {
        return tasks[index];
    }

    /**
     * Return a list of tasks that are scheduled to run
     * at least once after time from and no later than to.
     */
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList result = new ArrayTaskList();

        for (Task task: tasks) {
            if (task.isActive()) {
                if (task.isRepeated()) {
                    if ((task.nextTimeAfter(from) >= from) && (task.nextTimeAfter(from) <= to)) {
                        result.add(task);
                    }
                } else {
                    if ((task.getTime() > from) && (task.getTime() < to)) {
                        result.add(task);
                    }
                }
            }
        }

        return result;
    }

}