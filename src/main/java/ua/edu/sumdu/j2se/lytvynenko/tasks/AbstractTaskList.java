package ua.edu.sumdu.j2se.lytvynenko.tasks;

public abstract class AbstractTaskList {

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract int size();
    public abstract ListTypes.types getType();

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList result = TaskListFactory.createTaskList(getType());
        for (int i = 0; i < this.size(); i++) {
            if ((this.getTask(i).nextTimeAfter(from) >= from) && (this.getTask(i).nextTimeAfter(from) <= to)) {
                result.add(this.getTask(i));
            }
        }
        return result;
    }
}
