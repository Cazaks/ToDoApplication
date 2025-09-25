package com.todoApplication.datas.repositories;

import com.todoApplication.datas.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
    Optional<Task> findById(String id);
    List<Task> findTaskByTitle(String regex);
}
