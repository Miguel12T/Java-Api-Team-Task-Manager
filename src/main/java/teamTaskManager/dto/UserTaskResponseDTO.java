package teamTaskManager.dto;

import java.util.List;

public class UserTaskResponseDTO {
  // Atributos
    private Long id;
    private String name;
    private String userName;
    private List<TaskResponseDTO> tasks;
  // Constructor
    public UserTaskResponseDTO() {}
  // Getters and Setters
    public Long getId() {
      return id;
    }
    public void setId(Long id) {
      this.id = id;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getUserName() {
      return userName;
    }
    public void setUserName(String userName) {
      this.userName = userName;
    }
    public List<TaskResponseDTO> getTasks() {
      return tasks;
    }
    public void setTasks(List<TaskResponseDTO> tasks) {
      this.tasks = tasks;
    }
}
