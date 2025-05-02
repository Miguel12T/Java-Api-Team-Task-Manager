package teamTaskManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.Project;
import teamTaskManager.domain.Task;
import teamTaskManager.domain.UserTask;
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
    public List<Task> getAllTasks() {
      return tasksRepository.findAll();
    }
  // Crea las tareas si no existe la misma ya creada en el mismo proyecto
    public Task createTask(Task task) throws IllegalArgumentException {
      projectsRepository.findById(task.getProject().getId())
                        .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      userTasksRepository.findById(task.getAssignedUser().getId())
                        .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado."));
      if (tasksRepository.findByNameTaskIgnoreCaseAndProjectId(task.getNameTask(), task.getProject().getId()).isPresent()) {
        throw new IllegalArgumentException("Ya existe una tarea con ese nombre");
      }
      return tasksRepository.save(task);
    }
  // Actualiza los datos de las tareas
    public Task editTask(Long id, Task taskEdit) throws IllegalArgumentException {
      Task task = tasksRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada."));
      Project project = projectsRepository.findById(taskEdit.getProject().getId())
                  .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      UserTask user =  userTasksRepository.findById(taskEdit.getAssignedUser().getId())
                  .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado."));
      if (tasksRepository.findByNameTaskIgnoreCaseAndProjectIdAndIdNot(taskEdit.getNameTask(), taskEdit.getProject().getId(), id).isPresent())
        throw new IllegalArgumentException("Ya existe una tarea con ese nombre");
      task.setNameTask(taskEdit.getNameTask());
      task.setDescription(taskEdit.getDescription());
      task.setState(taskEdit.getState());
      task.setProject(project); // Asignar el proyecto completo
      task.setAssignedUser(user); // Asignar el usuario completo
      return tasksRepository.save(task);
    }
}
