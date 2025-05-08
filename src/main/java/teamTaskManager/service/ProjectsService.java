package teamTaskManager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.Project;
import teamTaskManager.domain.Task;
import teamTaskManager.dto.ProjectDTO;
import teamTaskManager.dto.ProjectResponseDTO;
import teamTaskManager.dto.TaskResponseDTO;
import teamTaskManager.repository.ProjectsRepository;

@Service
public class ProjectsService {
  @Autowired
  private ProjectsRepository projectsRepository;
  // Obtiene el listado de proyectos
    public List<ProjectResponseDTO> getAllProjects() {
      List<Project> projects = projectsRepository.findAll();
      return projects.stream()
                     .map(this::convertToResponseProjects)
                     .collect(Collectors.toList());
    }
  // Busca proyectos de acuerdo al texto ingresado
    public List<ProjectResponseDTO> searchProjects(String keyword) {
      List<Project> projects = projectsRepository.findByNameContainingIgnoreCase(keyword);
      return projects.stream()
                     .map(this::convertToResponseProjects)
                     .collect(Collectors.toList());
    }
  // Busca proyectos con tareas
    public List<ProjectResponseDTO> getProjectsWithTasks() {
      List<Project> projects = projectsRepository.findByTasksIsNotEmpty();
      return projects.stream()
                     .map(this::convertToResponseProjects)
                     .collect(Collectors.toList());
    }
  // Busca proyectos sin tareas
    public List<ProjectResponseDTO> getProjectsWithoutTasks() {
      List<Project> projects =projectsRepository.findByTasksIsEmpty();
      return projects.stream()
                     .map(this::convertToResponseProjects)
                     .collect(Collectors.toList());
    }
  // Crea un nuevo proyecto si no existe uno con el mismo nombre
    public Project createProject(ProjectDTO projectDto) throws IllegalArgumentException {
      if (projectsRepository.findByNameIgnoreCase(projectDto.getNameProject()).isPresent())
        throw new IllegalArgumentException("Ya existe un proyecto con ese nombre");
      Project project = new Project();
      project.setName(projectDto.getNameProject());
      return projectsRepository.save(project);
    }
  // Actualizar el proyecto
    public Project editProject(Long id, ProjectDTO projectDto) throws IllegalArgumentException {
      Project project = projectsRepository.findById(id)
                        .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      if (projectDto.getNameProject() != null && !projectDto.getNameProject().isBlank()) {
        if (projectsRepository.findByNameIgnoreCase(projectDto.getNameProject()).isPresent())
          throw new IllegalArgumentException("Ya existe un proyecto con ese nombre");
        else
          project.setName(projectDto.getNameProject());
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
  // Convierte una entidad JPA (Project) a un DTO de respuesta (ProjectResponseDTO)
    public ProjectResponseDTO convertToResponseProjects(Project project) {
      ProjectResponseDTO dto          = new ProjectResponseDTO();
      List<TaskResponseDTO> taskNames = project.getTasks().stream()
                                                          .map(this::convertToResponseTasks)
                                                          .collect(Collectors.toList());
      dto.setId(project.getId());
      dto.setProjectName(project.getName());
      dto.setNameTasks(taskNames);
      return dto;
    }
  // Convierte una entidad JPA (Task) a un DTO de respuesta (TaskResponseDTO)
    public TaskResponseDTO convertToResponseTasks(Task task) {
      TaskResponseDTO dto = new TaskResponseDTO();
      dto.setId(task.getId());
      dto.setNameTask(task.getNameTask());
      dto.setDescription(task.getDescription());
      dto.setState(task.getState());
      dto.setProjectName(task.getProject().getName());
      dto.setAssignedUsername(task.getAssignedUser().getUserName());
      dto.setNameUser(task.getAssignedUser().getName());
      return dto;
    }
}
