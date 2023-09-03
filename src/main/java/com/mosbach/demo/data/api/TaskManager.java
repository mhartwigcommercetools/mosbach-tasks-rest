package com.mosbach.demo.data.api;

import java.util.List;

public interface TaskManager {

    List<Task> readAllTasks(String email, SortOrder sortOrder);
    List<Task> readAllTasks();
    void addTask(String email, Task task);
    void removeTask(String email, int id);

}
