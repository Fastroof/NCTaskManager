package ua.edu.sumdu.j2se.lytvynenko.tasks;

import java.util.Objects;

public class Node implements Cloneable {
    Task task;
    Node next;
    Node prev;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return task.equals(node.task) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, next, prev);
    }

    @Override
    public String toString() {
        return "Node{" +
                "task=" + task +
                ", next=" + next +
                ", prev=" + prev +
                '}';
    }

    @Override
    public Node clone() {
        try {
            return (Node) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
