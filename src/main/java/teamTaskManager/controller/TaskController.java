package teamTaskManager.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teamTaskManager.domain.Task;
import teamTaskManager.service.TasksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  TasksService tasksService;
  @GetMapping
  public ResponseEntity<?> getAllTasks() {
    List<Task> tasks = tasksService.getAllTasks();
    return ResponseEntity.ok(tasks);
  }
  @PostMapping
  public ResponseEntity<?> createTask(@RequestBody Task task) {
    try {
      Task created = tasksService.createTask(task);
      return ResponseEntity.ok(created);
    }
    catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody Task task) {
    try {
      Task update = tasksService.editTask(id, task);
      return ResponseEntity.ok(update);
    }
    catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
  