package ua.edu.sumdu.j2se.lytvynenko.tasks;

import java.io.File;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime tomorrow = today.plusDays(1);
        Task testTask = new Task("test", today, tomorrow, 3600);
        testTask.setActive(true);
        System.out.println(testTask.nextTimeAfter(tomorrow.minusSeconds(1)));

        AbstractTaskList tasks = new ArrayTaskList();
        tasks.add(new Task("A", today));
        tasks.add(new Task("B", today.plusSeconds(100), tomorrow, 3600));
        Task t = new Task("C", today);
        t.setActive(true);
        tasks.add(t);
        t = new Task("B", today, tomorrow, 3600);
        t.setActive(true);
        tasks.add(t);

        File file = new File("src/main/java/ua/edu/sumdu/j2se/lytvynenko/tasks/bin.txt");
        TaskIO.writeBinary(tasks, file);
        AbstractTaskList result = new ArrayTaskList();
        TaskIO.readBinary(result, file);
        System.out.println(result.toString());

        TaskIO.writeText(tasks, file);
        result = new ArrayTaskList();
        TaskIO.readText(result, file);
        System.out.println(result.toString());
    }
}
