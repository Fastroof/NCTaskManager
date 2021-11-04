package ua.edu.sumdu.j2se.lytvynenko.tasks;

public class Main {

    public static void main(String[] args) {
        LinkedTaskList list = new LinkedTaskList();
        for (int i = 0; i < 10; i++) {
            list.add(new Task(String.valueOf(i),i));
        }
        System.out.println(list.incoming(1,3).getType());
        System.out.println(list.getTask(4).getTitle());
        System.out.println(list.getTask(7).getTitle());
    }
}
