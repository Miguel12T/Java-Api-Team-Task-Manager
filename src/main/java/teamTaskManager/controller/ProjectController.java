package teamTaskManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teamTaskManager.domain.Project;
import teamTaskManager.service.ProjectsService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/projects")
public class ProjectController {
  @Autowired
  private ProjectsService projectsService;
  @GetMapping
  public ResponseEntity<?> getProjects() {
    List<Project> projects = projectsService.getAllProjects();
    return ResponseEntity.ok(projects);
  }
  @GetMapping("/with-tasks")
  public ResponseEntity<List<Project>> getProjectsWithTasks() {
    List<Project> projects = projectsService.getProjectsWithTasks();
    return ResponseEntity.ok(projects);
  }
  @GetMapping("/without-tasks")
  public ResponseEntity<List<Project>> getProjectsWithoutTasks() {
    List<Project> projects = projectsService.getProjectsWithoutTasks();
    return ResponseEntity.ok(projects);
  }
  @GetMapping("/search")
  public List<Project> searchProjects(@RequestParam String keyword) {
    return projectsService.searchProjects(keyword);
  }
  @PostMapping
  public ResponseEntity<?> createProject(@RequestBody Project project) {
    try {
      Project created = projectsService.createProject(project);
      return ResponseEntity.ok(created);
    }
    catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> editProject(@PathVariable Long id, @RequestBody Project project) {
    try {
      Project update = projectsService.editProject(id, project);
      return ResponseEntity.ok(update);
    }
    catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProject(@PathVariable Long id) {
    try {
      projectsService.deleteProject(id);
      return ResponseEntity.noContent().build();
    }
    catch (IllegalStateException | IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
