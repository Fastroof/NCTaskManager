package ua.edu.sumdu.j2se.lytvynenko.tasks;

public class Main {

    public static void main(String[] args) {
        Task task = new Task("Зустріч у кафе", 10);
        System.out.println(task.getTitle());
        System.out.println(task.getTime());
        System.out.println(task.isActive());
        System.out.println(task.isRepeated());

        Task repeatedTask = new Task("Ранкова пробіжка", 8, 200, 24);
        System.out.println(repeatedTask.getTitle());
        System.out.println(repeatedTask.getStartTime());
        System.out.println(repeatedTask.getEndTime());
        System.out.println(repeatedTask.getRepeatInterval());
        System.out.println(repeatedTask.isActive());
        System.out.println(repeatedTask.isRepeated());
        repeatedTask.getTime();
    }
}
