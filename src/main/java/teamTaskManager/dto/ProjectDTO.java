package teamTaskManager.dto;

public class ProjectDTO {
  // Atributos
    private Long id;
    private String nameProject;
  // Constructo
    public ProjectDTO() {}
  // Getters y Setters
    public Long getId() {
      return id;
    }
    public void setId(Long id) {
      this.id = id;
    }
    public String getNameProject() {
      return nameProject;
    }
    public void setNameProject(String nameProject) {
      this.nameProject = nameProject;
    }
}
