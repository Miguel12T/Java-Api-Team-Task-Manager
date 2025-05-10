package teamTaskManager.dto;

public class UserTaskDTO {
  // Atributos
    private Long id;
    private String name;
    private String userName;
    private String password;
  // Constructor
    public UserTaskDTO() {}
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
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
}
