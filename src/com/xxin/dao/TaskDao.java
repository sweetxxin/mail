package com.xxin.dao;

import com.xxin.entity.Task;
import com.xxin.utils.DBUtils;

public class TaskDao {
    public int save(Task task){
        return DBUtils.save(task);
    }
}
