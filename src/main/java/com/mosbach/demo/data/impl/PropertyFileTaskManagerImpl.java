package com.mosbach.demo.data.impl;

import com.mosbach.demo.data.api.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PropertyFileTaskManagerImpl implements TaskManager {

    String taskPropertyFile;

    // Singleton
    static PropertyFileTaskManagerImpl propertyFileTaskManager = null;
    private PropertyFileTaskManagerImpl(String taskPropertyFile) {
        this.taskPropertyFile = taskPropertyFile;
    }
    public static PropertyFileTaskManagerImpl getPropertyFileTaskManagerImpl(String userPropertyFile) {
        if (propertyFileTaskManager == null)
            propertyFileTaskManager = new PropertyFileTaskManagerImpl(userPropertyFile);
        return propertyFileTaskManager;
    }

    public List<Task> readAllTasks(String email, SortOrder sortOrder) {

        final Logger readTaskLogger = Logger.getLogger("ReadTaskLogger");
        readTaskLogger.log(Level.INFO,"Start reading ");

        List<Task> tasks = new ArrayList<>();
        Properties properties = new Properties();

        // todo

        return tasks;
    }

    public List<Task> readAllTasks() {

        final Logger readTaskLogger = Logger.getLogger("ReadTaskLogger");
        readTaskLogger.log(Level.INFO,"Start reading ");

        List<Task> tasks = new ArrayList<>();
        Properties properties = new Properties();

        // todo

        return tasks;
    }

    @Override
    public void addTask(String email, Task task) {
        final Logger createTaskLogger = Logger.getLogger("CreateTaskLogger");
        createTaskLogger.log(Level.INFO,"Start creating task for " + email);

        List<Task> tasks = readAllTasks();

        createTaskLogger.log(Level.INFO,"Adding new task. Until now entries " + tasks.size());
        tasks.add(task);

        createTaskLogger.log(Level.INFO,"Start storing all tasks.");
        storeAllTasks(tasks);

    }

    @Override
    public void removeTask(String email, int id) {
        final Logger removeTaskLogger = Logger.getLogger("RemoveTaskLogger");
        removeTaskLogger.log(Level.INFO,"Start removing task for " + email);

        final List<Task> listWithRemovedTask =
                readAllTasks()
                    .stream()
                    .filter(task -> task.getId() != id)
                    .collect(Collectors.toList());

        removeTaskLogger.log(Level.INFO,"Start storing all tasks.");
        storeAllTasks(listWithRemovedTask);
    }

    public void storeAllTasks(List<Task> tasks) {
        Properties properties = new Properties();
        final AtomicLong counter = new AtomicLong();
        counter.set(0);

       // todo

    }


}
