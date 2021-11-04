package ua.edu.sumdu.j2se.lytvynenko.tasks;

public class ArrayTaskList extends AbstractTaskList {

    private static final int increaseInterval = 5;
    private Task[] tasks = new Task[increaseInterval];
    private int size;

    /**
     * Add the specified task to the list.
     * @param task specified task
     */
    @Override
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
    @Override
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public Task getTask(int index) {
        if (index >= tasks.length) {
            throw new IndexOutOfBoundsException();
        }
        return tasks[index];
    }

    @Override
    public ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }
}
