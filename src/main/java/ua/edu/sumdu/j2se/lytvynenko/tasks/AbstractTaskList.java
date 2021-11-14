package ua.edu.sumdu.j2se.lytvynenko.tasks;

import java.util.Iterator;

public abstract class AbstractTaskList implements Iterable<Task> {

    @Override
    public Iterator<Task> iterator() {
        return new TaskListIterator(this);
    }

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract int size();
    public abstract ListTypes.types getType();

    /**
     * Converts a list to an array. Task links remain unchanged.
     * @return array of tasks
     */
    public Task[] toArray() {
        Task[] result = new Task[size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = getTask(i);
        }
        return result;
    }

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList result = TaskListFactory.createTaskList(getType());
        for (int i = 0; i < this.size(); i++) {
            if ((this.getTask(i).nextTimeAfter(from) >= from) && (this.getTask(i).nextTimeAfter(from) <= to)) {
                result.add(this.getTask(i));
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "AbstractTaskList{}";
    }
}
