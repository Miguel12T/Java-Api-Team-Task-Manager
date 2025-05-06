package teamTaskManager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.Project;
import teamTaskManager.domain.Task;
import teamTaskManager.domain.UserTask;
import teamTaskManager.dto.TaskDTO;
import teamTaskManager.dto.TaskResponseDTO;
import teamTaskManager.repository.ProjectsRepository;
import teamTaskManager.repository.TasksRepository;
import teamTaskManager.repository.UserTasksRepository;

@Service
public class TasksService {
  private final TasksRepository tasksRepository;
  private final ProjectsRepository projectsRepository;
  private final UserTasksRepository userTasksRepository;
  @Autowired
  public TasksService(TasksRepository tasksRepository, ProjectsRepository projectsRepository, UserTasksRepository userTasksRepository) {
    this.tasksRepository     = tasksRepository;
    this.projectsRepository  = projectsRepository;
    this.userTasksRepository = userTasksRepository;
  }
  // Obtiene el listado de las tareas
    public List<TaskResponseDTO> getAllTasks() {
      List<Task> tasks = tasksRepository.findAll();
      return tasks.stream()
                  .map(this::convertToResponse)
                  .collect(Collectors.toList());
    }
  // Crea las tareas si no existe la misma ya creada en el mismo proyecto
    public Task createTask(TaskDTO taskDto) throws IllegalArgumentException {
      Project project =  projectsRepository.findById(taskDto.getProjectId())
                        .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      UserTask userTask =  userTasksRepository.findById(taskDto.getAssignedUserId())
                        .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado."));
      if (tasksRepository.findByNameTaskIgnoreCaseAndProjectId(taskDto.getNameTask(), taskDto.getProjectId()).isPresent()) {
        throw new IllegalArgumentException("Ya existe una tarea con ese nombre");
      }
      Task task = new Task();
      task.setNameTask(taskDto.getNameTask());
      task.setDescription(taskDto.getDescription());
      task.setState(taskDto.getState());
      task.setProject(project);
      task.setAssignedUser(userTask);
      return tasksRepository.save(task);
    }
  // Actualiza los datos de las tareas
    public Task editTask(Long id, TaskDTO taskDto) throws IllegalArgumentException {
      Task task = tasksRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada."));
      Project project = projectsRepository.findById(taskDto.getProjectId())
                  .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      UserTask user =  userTasksRepository.findById(taskDto.getAssignedUserId())
                  .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado."));
      if (tasksRepository.findByNameTaskIgnoreCaseAndProjectIdAndIdNot(taskDto.getNameTask(), taskDto.getProjectId(), id).isPresent())
        throw new IllegalArgumentException("Ya existe una tarea con ese nombre");
      task.setNameTask(taskDto.getNameTask());
      task.setDescription(taskDto.getDescription());
      task.setState(taskDto.getState());
      task.setProject(project); // Asignar el proyecto completo
      task.setAssignedUser(user); // Asignar el usuario completo
      return tasksRepository.save(task);
    }
  // Elimina la tarea si el estado es 'por comenzar'
    public void deleteTask(Long id) throws IllegalStateException {
      Task task = tasksRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
      if (!task.getState().equalsIgnoreCase("por comenzar"))
        throw new IllegalStateException("No se puede eliminar la tarea esta siendo realizada");
      tasksRepository.delete(task);
    }
  // Convierte una entidad JPA (Task) a un DTO de respuesta (TaskResponseDTO)
    public TaskResponseDTO convertToResponse(Task task) {
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
