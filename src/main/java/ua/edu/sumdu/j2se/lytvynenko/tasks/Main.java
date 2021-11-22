package ua.edu.sumdu.j2se.lytvynenko.tasks;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime TOMORROW = today.plusDays(1);
        Task testTask = new Task("test", today, TOMORROW, 3600);
        testTask.setActive(true);
        System.out.println(testTask.nextTimeAfter(TOMORROW.minusSeconds(1)));
    }
}
