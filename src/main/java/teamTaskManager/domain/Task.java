package teamTaskManager.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // Atributos
    private Long id;
    private String nameTask;
    private String description;
    private String state;
  // Relación de muchas tareas pueden pertenecer a un solo proyecto
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
  // Relación de muchas tareas pueden estar asignadas a un solo usuario
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserTask assignedUser;
  // Getters y Setters
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
    public Project getProject() {
      return project;
    }
    public void setProject(Project project) {
      this.project = project;
    }
    public UserTask getAssignedUser() {
      return assignedUser;
    }
    public void setAssignedUser(UserTask user) {
      this.assignedUser = user;
    }
}
