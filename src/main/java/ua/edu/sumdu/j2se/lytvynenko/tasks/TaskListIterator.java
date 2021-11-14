package ua.edu.sumdu.j2se.lytvynenko.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class TaskListIterator implements Iterator<Task> {

    private final AbstractTaskList tasks;
    private final int size;
    private int nextIndex = 0;

    public TaskListIterator(AbstractTaskList taskList) {
        size = taskList.size();
        tasks = taskList;
    }

    @Override
    public boolean hasNext() {
        return nextIndex < size;
    }

    @Override
    public Task next() {
        if (!hasNext()) throw new NoSuchElementException();
        return tasks.getTask(nextIndex++);
    }

    @Override
    public void remove() {
        if (nextIndex == 0) {
            throw new IllegalStateException();
        }
        nextIndex = nextIndex - 1;
        tasks.remove(tasks.getTask(nextIndex));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskListIterator that = (TaskListIterator) o;
        return size == that.size && nextIndex == that.nextIndex && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, size, nextIndex);
    }

    @Override
    public String toString() {
        return "TaskListIterator{" +
                "size=" + size +
                ", nextIndex=" + nextIndex +
                ", tasks=" + tasks +
                '}';
    }
}
