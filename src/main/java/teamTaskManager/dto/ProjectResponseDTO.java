package teamTaskManager.dto;

import java.util.List;

public class ProjectResponseDTO {
  // Atributos
    private Long id;
    private String projectName;
    private List<TaskResponseDTO> nameTasks;
  // Constructor
    public ProjectResponseDTO() {}
  // Getters y Setters
    public Long getId() {
      return id;
    }
    public void setId(Long id) {
      this.id = id;
    }
    public String getProjectName() {
      return projectName;
    }
    public void setProjectName(String projectName) {
      this.projectName = projectName;
    }
    public List<TaskResponseDTO> getNameTasks() {
      return nameTasks;
    }
    public void setNameTasks(List<TaskResponseDTO> nameTasks) {
      this.nameTasks = nameTasks;
    }
}
