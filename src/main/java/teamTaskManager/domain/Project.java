package teamTaskManager.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Relaciona el id de forma incremental
  private Long id;
  private String name;
  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL) // Relación uno a muchos con tareas
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
    public List<Task> getTasks() {
      return tasks;
    }
    public void setTasks(List<Task> tasks) {
      this.tasks = tasks;
    }
}
