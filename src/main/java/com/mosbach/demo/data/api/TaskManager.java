package com.mosbach.demo.data.api;

import com.mosbach.demo.model.student.Student;
import com.mosbach.demo.model.task.Task;


import java.util.List;

public interface TaskManager {

    List<Task> getAllTasks(String email);

    // all Todo
    void addTask(Task task, String studentEmail);
    void deleteTask(String name, Student student);


}
