package teamTaskManager.dto;

public class TaskDTO {
  // Atributos
    private Long id;
    private String nameTask;
    private String description;
    private String state;
    private Long projectId;
    private Long assignedUserId;
  // Constructor
    public TaskDTO() {}
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
    public Long getProjectId() {
      return projectId;
    }
    public void setProjectId(Long projectId) {
      this.projectId = projectId;
    }
    public Long getAssignedUserId() {
      return assignedUserId;
    }
    public void setAssignedUserId(Long assignedUserId) {
      this.assignedUserId = assignedUserId;
    }
}
