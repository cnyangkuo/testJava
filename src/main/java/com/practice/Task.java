package com.practice;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangkuo
 * @date 2023/9/5
 * @description
 */
@Data
public class Task {
    public String name;
    List<Task> dependencies;
    public Task(String name) {
        this.name = name;
        dependencies = new ArrayList<>();
    }

    public void addDependencies(Task task) {
        this.dependencies.add(task);
    }

}
