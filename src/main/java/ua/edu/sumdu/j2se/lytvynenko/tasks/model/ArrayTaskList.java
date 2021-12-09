package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Cloneable, Serializable {

    private static final int increaseInterval = 5;
    private Task[] tasks = new Task[increaseInterval];
    private int size = 0;

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
            System.arraycopy(tasks, 0, tempArray, 0, size);
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
            System.arraycopy(tasks, 0, tempArray, 0, index);
            if (size - (index + 1) >= 0)
                System.arraycopy(tasks, index + 1, tempArray, index + 1 - 1, size - (index + 1));
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
        if ((index >= size) && (index < 0)) {
            throw new IndexOutOfBoundsException();
        }
        return tasks[index];
    }

    @Override
    public ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }

    @Override
    public Stream<Task> getStream() {
        return super.getStream();
    }


    @Override
    public Task[] toArray() {
        Task[] result = new Task[size];
        System.arraycopy(tasks, 0, result, 0, size);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList tasks1 = (ArrayTaskList) o;
        return Arrays.equals(tasks, tasks1.tasks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(tasks);
        return result;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "\n\tsize=" + size +
                "\n\ttasks=" + Arrays.toString(tasks) +
                '}';
    }

    @Override
    public ArrayTaskList clone() {
        try {
            ArrayTaskList clone = (ArrayTaskList) super.clone();
            clone.tasks = tasks.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
