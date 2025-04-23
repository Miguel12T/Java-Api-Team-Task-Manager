package teamTaskManager.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserTask {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String userName;
  private String password;
  @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL) // Relacion uno a muchos con tareas asignadas
  private List<Task> tasks;
  // Getters y Setters
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
    public List<Task> getTasks() {
      return tasks;
    }
    public void setTasks(List<Task> tasks) {
      this.tasks = tasks;
    }
}
