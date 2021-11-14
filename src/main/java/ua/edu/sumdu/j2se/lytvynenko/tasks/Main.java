package ua.edu.sumdu.j2se.lytvynenko.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LinkedTaskList list = new LinkedTaskList();
        for (int i = 0; i < 10; i++) {
            list.add(new Task(String.valueOf(i),i));
        }
        Iterator<Task> tmp1 = list.iterator();
        Iterator<Task> tmp2 = list.iterator();
        System.out.println(tmp1);

        Task t1 = new Task("333",333);
        Task t2 = t1.clone();
        t2.setActive(true);
        System.out.println(t1.equals(t2));

        AbstractTaskList taskList = new ArrayTaskList();
        taskList.add(new Task("A",5));
        taskList.add(new Task("B",5));
        taskList.add(new Task("C",5));
        taskList.add(new Task("D",5));

        List<String> etalon = new ArrayList<>();
        for (Iterator<Task> it = taskList.iterator(); it.hasNext();) {
            etalon.add(it.next().getTitle());
        }
        Iterator<Task> it = taskList.iterator();
        Iterator<String> etalonIt = etalon.iterator();

        String actual = it.next().getTitle();
        String expected = etalonIt.next();

        System.out.println(actual);
        System.out.println(expected);

        it.remove(); etalonIt.remove();
        actual = it.next().getTitle();
        expected = etalonIt.next();

        System.out.println(actual);
        System.out.println(expected);
    }
}
