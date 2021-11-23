package ua.edu.sumdu.j2se.lytvynenko.tasks;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out) {
        if (out == null) {
            throw new IllegalArgumentException("OutputStream is null");
        }
        DataOutput data = new DataOutputStream(out);
        try {
            data.writeInt(tasks.size());
            for (Task task : tasks) {
                data.writeInt(task.getTitle().length());
                data.writeChars(task.getTitle());
                data.writeBoolean(task.isActive());
                data.writeInt(task.getRepeatInterval());
                if (task.isRepeated()) {
                    data.writeLong(task.getStartTime().toEpochSecond(ZoneOffset.UTC));
                    data.writeLong(task.getEndTime().toEpochSecond(ZoneOffset.UTC));
                } else {
                    data.writeLong(task.getTime().toEpochSecond(ZoneOffset.UTC));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        if (in == null) {
            throw new IllegalArgumentException("InputStream is null");
        }
        DataInput data = new DataInputStream(in);
        try {
            int size = data.readInt();
            for (int i = 0; i < size; i++) {
                int titleLength = data.readInt();
                StringBuilder titleBuilder = new StringBuilder();
                for (int j = 0; j < titleLength; j++) {
                    titleBuilder.append(data.readChar());
                }
                String title = titleBuilder.toString();
                boolean active = data.readBoolean();
                int repeatInterval = data.readInt();
                if (repeatInterval == 0) {
                    LocalDateTime time = LocalDateTime.ofEpochSecond(data.readLong(), 0, ZoneOffset.UTC);
                    Task tmp = new Task(title, time);
                    tmp.setActive(active);
                    tasks.add(tmp);
                } else {
                    LocalDateTime start = LocalDateTime.ofEpochSecond(data.readLong(), 0, ZoneOffset.UTC);
                    LocalDateTime end = LocalDateTime.ofEpochSecond(data.readLong(), 0, ZoneOffset.UTC);
                    Task tmp = new Task(title, start, end, repeatInterval);
                    tmp.setActive(active);
                    tasks.add(tmp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            write(tasks, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            read(tasks, inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        Gson gson = new Gson();
        gson.toJson(tasks, out);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        if (tasks == null) {
            throw new IllegalArgumentException();
        }
        Gson gson = new Gson();
        gson.fromJson(in, tasks.getClass()).getStream().forEach(tasks::add);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try {
            Writer writer = new FileWriter(file);
            write(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try {
            Reader reader = new FileReader(file);
            read(tasks, reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
