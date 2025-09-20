package com.mosbach.demo.data.api;

import com.mosbach.demo.model.student.Student;


import java.util.List;

public interface TaskManager {

    List<Task> getAllTasksPerEmail(String email);
    boolean addTask(Task task);

}
