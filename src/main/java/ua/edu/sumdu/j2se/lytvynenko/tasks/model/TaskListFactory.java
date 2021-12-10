package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

public class TaskListFactory {

    /**
     * Method according to parameter type, returns an object of class Array TaskList or LinkedTaskList
     */
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        switch (type) {
            case ARRAY: return new ArrayTaskList();
            case LINKED: return new LinkedTaskList();
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return "TaskListFactory{}";
    }
}
