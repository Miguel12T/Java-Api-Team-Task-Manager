package teamTaskManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teamTaskManager.domain.Task;
import teamTaskManager.dto.TaskDTO;
import teamTaskManager.dto.TaskResponseDTO;
import teamTaskManager.service.TasksService;

import org.springframework.web.bind.annotation.DeleteMapping;
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
    List<TaskResponseDTO> tasks = tasksService.getAllTasks();
    return ResponseEntity.ok(tasks);
  }
  @PostMapping
  public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDto) {
    try {
      Task created = tasksService.createTask(taskDto);
      return ResponseEntity.ok(created);
    }
    catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody TaskDTO taskDto) {
    try {
      Task update = tasksService.editTask(id, taskDto);
      return ResponseEntity.ok(update);
    }
    catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTask(@PathVariable Long id) {
    try {
      tasksService.deleteTask(id);
      return ResponseEntity.noContent().build();
    }
    catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
