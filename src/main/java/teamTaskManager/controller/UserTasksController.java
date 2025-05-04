package teamTaskManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teamTaskManager.domain.UserTask;
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
    List<UserTask> userTask = userTaksService.getAllUserTasks();
    return ResponseEntity.ok(userTask);
  }
  @GetMapping("/without-tasks")
  public List<UserTask> getUserTasksWithoutTasks() {
    return userTaksService.getUserTasksWithoutTasks();
  }
  @GetMapping("/with-tasks")
  public List<UserTask> getUserTasksWithTasks() {
    return userTaksService.getUserTasksWithTasks();
  }
  @PostMapping
  public ResponseEntity<?> createUserTask(@RequestBody UserTask userTask) {
    try {
      UserTask created = userTaksService.createUserTask(userTask);
      return ResponseEntity.ok(created);
    }
    catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUserTask(@PathVariable Long id, @RequestBody UserTask userTask) {
    try {
      UserTask update = userTaksService.updateUserTask(id, userTask);
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
