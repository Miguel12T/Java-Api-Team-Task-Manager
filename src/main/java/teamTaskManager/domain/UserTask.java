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
  private String user_name;
  private String password;
  @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL) // Relacion uno a muchos con tareas asignadas
  private List<Task> tasks;
  // Getters y Setters
    public Long getid() {
      return id;
    }
    public void setid(Long id) {
      this.id = id;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getUser_name() {
      return user_name;
    }
    public void setUser_name(String user_name) {
      this.user_name = user_name;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
}
