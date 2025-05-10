package teamTaskManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teamTaskManager.domain.UserTask;
import teamTaskManager.dto.UserTaskDTO;
import teamTaskManager.dto.UserTaskResponseDTO;
import teamTaskManager.service.UserTasksService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserTasksController {
  @Autowired
  private UserTasksService userTaksService;
  @GetMapping
  public ResponseEntity<?> getAllUserTasks() {
    List<UserTaskResponseDTO> usersTasks = userTaksService.getAllUserTasks();
    return ResponseEntity.ok(usersTasks);
  }
  @GetMapping("/without-tasks")
  public ResponseEntity<?> getUserTasksWithoutTasks() {
    List<UserTaskResponseDTO> usersTasks = userTaksService.getUserTasksWithoutTasks();
    return ResponseEntity.ok(usersTasks);
  }
  @GetMapping("/with-tasks")
  public ResponseEntity<?> getUserTasksWithTasks() {
    List<UserTaskResponseDTO> usersTasks = userTaksService.getUserTasksWithTasks();
    return ResponseEntity.ok(usersTasks);
  }
  @PostMapping
  public ResponseEntity<?> createUserTask(@RequestBody UserTaskDTO userTaskDto) {
    try {
      UserTask created = userTaksService.createUserTask(userTaskDto);
      return ResponseEntity.ok(created);
    }
    catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUserTask(@PathVariable Long id, @RequestBody UserTaskDTO userTaskDto) {
    try {
      UserTask update = userTaksService.updateUserTask(id, userTaskDto);
      return ResponseEntity.ok(update);
    }
    catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserTask(@PathVariable Long id) {
    try {
      userTaksService.deleteUserTask(id);
      return ResponseEntity.noContent().build();
    }
    catch (IllegalStateException | IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
