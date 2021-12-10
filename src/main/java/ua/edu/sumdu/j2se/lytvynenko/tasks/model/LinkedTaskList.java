package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList implements Cloneable, Serializable {

    private Node head;
    private Node tail;
    private int size = 0;

    /**
     * Add the specified task to the list.
     * @param task specified task
     */
    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.task = task;
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    /**
     * Remove a task from the list and returns the truth if such a task was in the list.
     * If there are several such tasks in the list, the method deletes the first one.
     */
    @Override
    public boolean remove(Task task) {
        boolean result = false;

        if ((size == 1) && (head.task.equals(task))) {
            head = null;
            tail = null;
            result = true;
        } else if (size > 1) {
            if (head.task.equals(task)) {
                head = head.next;
                head.prev = null;
                result = true;
            } else if (tail.task.equals(task)) {
                tail = tail.prev;
                tail.next = null;
                result = true;
            } else {
                Node temp = head;
                for (int i = 0; i < size - 2; i++) {
                    if (temp.next.task.equals(task)) {
                        temp.next = temp.next.next;
                        temp.next.prev = temp.next.prev.prev;
                        result = true;
                        break;
                    }
                    temp = temp.next;
                }
            }
        }

        if (result) {
            size--;
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Task getTask(int index) {
        if ((index >= size) && (index < 0)) {
            throw new IndexOutOfBoundsException();
        }
        Node temp = head;
        if (index + 1 <= size/2) {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp.task;
    }

    @Override
    public ListTypes.types getType() {
        return ListTypes.types.LINKED;
    }

    @Override
    public Stream<Task> getStream() {
        return super.getStream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList list = (LinkedTaskList) o;
        return Arrays.equals(toArray(), list.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(toArray()), size);
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "\n\tsize=" + size +
                "\n\ttasks=" + Arrays.toString(toArray()) +
                '}';
    }

    @Override
    public LinkedTaskList clone() {
        try {
            LinkedTaskList clone = (LinkedTaskList) super.clone();
            clone.head = head.clone();
            clone.tail = tail.clone();
            for (Node node = clone.head; node.next!=null; node = node.next) {
                node.next = node.next.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
