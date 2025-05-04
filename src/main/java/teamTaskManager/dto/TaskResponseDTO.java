package teamTaskManager.dto;

public class TaskResponseDTO {
  // Atributos
    private Long id;
    private String nameTask;
    private String description;
    private String state;
    private String projectName;
    private String assignedUsername;
  // Constructor
    public TaskResponseDTO() {}
  // Getters and Setters
    public Long getId() {
      return id;
    }
    public void setId(Long id) {
      this.id = id;
    }
    public String getNameTask() {
      return nameTask;
    }
    public void setNameTask(String nameTask) {
      this.nameTask = nameTask;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public String getState() {
      return state;
    }
    public void setState(String state) {
      this.state = state;
    }
    public String getProjectName() {
      return projectName;
    }
    public void setProjectName(String projectName) {
      this.projectName = projectName;
    }
    public String getAssignedUsername() {
      return assignedUsername;
    }
    public void setAssignedUsername(String assignedUsername) {
      this.assignedUsername = assignedUsername;
    }
}
