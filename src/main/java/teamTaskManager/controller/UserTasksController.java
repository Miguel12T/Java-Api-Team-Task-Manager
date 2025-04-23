package teamTaskManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teamTaskManager.domain.UserTask;
import teamTaskManager.service.UserTaksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserTasksController {
  @Autowired
  private UserTaksService userTaksService;
  @GetMapping
  public ResponseEntity<?> getAllUserTasks() {
    List<UserTask> userTask = userTaksService.getAllUserTasks();
    return ResponseEntity.ok(userTask);
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
}
