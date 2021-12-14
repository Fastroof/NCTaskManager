package ua.edu.sumdu.j2se.lytvynenko.tasks.model;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {

    private static final Logger log = Logger.getLogger(TaskIO.class);

    public static void write(AbstractTaskList tasks, OutputStream out) {
        if (out == null) {
            throw new IllegalArgumentException("OutputStream is null");
        }
        try {
            DataOutput data = new DataOutputStream(out);
            data.writeInt(tasks.size());
            for (Task task : tasks) {
                data.writeInt(task.getTitle().length());
                data.writeChars(task.getTitle());
                data.writeBoolean(task.isActive());
                data.writeLong(task.getRepeatInterval().getSeconds());
                if (task.isRepeated()) {
                    data.writeLong(task.getStartTime().toEpochSecond(ZoneOffset.UTC));
                    data.writeLong(task.getEndTime().toEpochSecond(ZoneOffset.UTC));
                } else {
                    data.writeLong(task.getTime().toEpochSecond(ZoneOffset.UTC));
                }
            }
        } catch (IOException e) {
            log.error("Binary write to OutputStream failed", e);
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        if (in == null) {
            throw new IllegalArgumentException("InputStream is null");
        }
        try {
            DataInput data = new DataInputStream(in);
            int size = data.readInt();
            for (int i = 0; i < size; i++) {
                int titleLength = data.readInt();
                StringBuilder titleBuilder = new StringBuilder();
                for (int j = 0; j < titleLength; j++) {
                    titleBuilder.append(data.readChar());
                }
                String title = titleBuilder.toString();
                boolean active = data.readBoolean();
                long repeatInterval = data.readLong();
                if (repeatInterval == 0) {
                    LocalDateTime time = LocalDateTime.ofEpochSecond(data.readLong(), 0, ZoneOffset.UTC);
                    Task tmp = new Task(title, time);
                    tmp.setActive(active);
                    tasks.add(tmp);
                } else {
                    LocalDateTime start = LocalDateTime.ofEpochSecond(data.readLong(), 0, ZoneOffset.UTC);
                    LocalDateTime end = LocalDateTime.ofEpochSecond(data.readLong(), 0, ZoneOffset.UTC);
                    Task tmp = new Task(title, start, end, Duration.ofSeconds(repeatInterval));
                    tmp.setActive(active);
                    tasks.add(tmp);
                }
            }
        } catch (IOException e) {
            log.error("Binary read from InputStream failed", e);
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            write(tasks, outputStream);
        } catch (IOException e) {
            log.error("Binary write to file failed", e);
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        if (file.length() == 0) {
            System.out.println("File is empty");
        } else {
            try (InputStream inputStream = new FileInputStream(file)) {
                read(tasks, inputStream);
            } catch (IOException e) {
                log.error("Binary read from file failed", e);
            }
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        Gson gson = new Gson();
        gson.toJson(tasks, out);
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        if (tasks == null) {
            throw new IllegalArgumentException();
        }
        Gson gson = new Gson();
        gson.fromJson(in, tasks.getClass()).getStream().forEach(tasks::add);
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try (Writer writer = new FileWriter(file)) {
            write(tasks, writer);
        } catch (IOException e) {
            log.error("Text write to file failed", e);
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try (Reader reader = new FileReader(file)) {
            read(tasks, reader);
        } catch (IOException e) {
            log.error("Text read from file failed", e);
        }
    }
}
