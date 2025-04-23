package teamTaskManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.Project;
import teamTaskManager.repository.ProjectsRepository;

@Service
public class ProjectsService {
  @Autowired
  private ProjectsRepository projectsRepository;
  // Obtiene el listado de proyectos
    public List<Project> getAllProjects() {
      return projectsRepository.findAll();
    }
  // Busca proyectos de acuerdo al texto ingresado
    public List<Project> searchProjects(String keyword) {
      return projectsRepository.findByNameContainingIgnoreCase(keyword);
    }
  // Busca proyectos con tareas
    public List<Project> getProjectsWithTasks() {
      return projectsRepository.findByTasksIsNotEmpty();
    }
  // Busca proyectos sin tareas
    public List<Project> getProjectsWithoutTasks() {
      return projectsRepository.findByTasksIsEmpty();
    }
  // Crea un nuevo proyecto si no existe uno con el mismo nombre
    public Project createProject(Project project) throws IllegalArgumentException {
      if (projectsRepository.findByNameIgnoreCase(project.getName()).isPresent())
        throw new IllegalArgumentException("Ya existe un proyecto con ese nombre");
      return projectsRepository.save(project);
    }
  // Actualizar el proyecto
    public Project editProject(Long id, Project projectEdit) throws IllegalArgumentException {
      Project project = projectsRepository.findById(id)
                        .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      if (projectEdit.getName() != null && !projectEdit.getName().isBlank()) {
        if (projectsRepository.findByNameIgnoreCase(projectEdit.getName()).isPresent())
          throw new IllegalArgumentException("Ya existe un proyecto con ese nombre");
        else
          project.setName(projectEdit.getName());
      }
      return projectsRepository.save(project);
    }
  // Elimina un proyecto solo si no tiene tareas asociadas
    public void deleteProject(Long projectId) throws IllegalStateException {
      Project project = projectsRepository.findById(projectId)
                        .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      if (project.getTasks() != null && !project.getTasks().isEmpty())
        throw new IllegalStateException("No se puede eliminar el proyecto porque tiene tareas asociadas");
      projectsRepository.delete(project);
    }
}
