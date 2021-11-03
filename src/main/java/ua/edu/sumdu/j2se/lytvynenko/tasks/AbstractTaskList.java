package ua.edu.sumdu.j2se.lytvynenko.tasks;

public abstract class AbstractTaskList {

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract int size();

    public ListTypes.types getType() {
        if (this instanceof ArrayTaskList) {
            return ListTypes.types.ARRAY;
        } else if (this instanceof LinkedTaskList){
            return ListTypes.types.LINKED;
        } else {
            throw new RuntimeException();
        }
    }

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList result = TaskListFactory.createTaskList(this.getType());
        for (int i = 0; i < this.size(); i++) {
            if ((this.getTask(i).nextTimeAfter(from) >= from) && (this.getTask(i).nextTimeAfter(from) <= to)) {
                result.add(this.getTask(i));
            }
        }
        return result;
    }
}
