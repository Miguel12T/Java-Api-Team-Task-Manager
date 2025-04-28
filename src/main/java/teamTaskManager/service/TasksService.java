package teamTaskManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.Task;
import teamTaskManager.repository.ProjectsRepository;
import teamTaskManager.repository.TasksRepository;
import teamTaskManager.repository.UserTasksRepository;

@Service
public class TasksService {
  @Autowired
  private TasksRepository tasksRepository;
  private ProjectsRepository projectsRepository;
  UserTasksRepository userTasksRepository;
  // Obtiene el listado de las tareas
    public List<Task> getAllTasks() {
      return tasksRepository.findAll();
    }
  // Crea las tareas si no existe la misma ya creada en el mismo proyecto
    public Task createTask(Task task) throws IllegalArgumentException {
      projectsRepository.findById(task.getProject().getId())
                        .orElseThrow(()-> new IllegalArgumentException("Proyecto no encontrado."));
      userTasksRepository.findById(task.getProject().getId())
                        .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado."));
      if (tasksRepository.findByNameTaskIgnoreCaseAndProjectId(task.getNameTask(), task.getProject().getId()).isPresent()) {
        throw new IllegalArgumentException("Ya existe una tarea con ese nombre");
      }
      return tasksRepository.save(task);
    }
}
