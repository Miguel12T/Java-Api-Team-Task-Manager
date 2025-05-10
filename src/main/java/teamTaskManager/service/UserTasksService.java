package teamTaskManager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.Task;
import teamTaskManager.domain.UserTask;
import teamTaskManager.dto.TaskResponseDTO;
import teamTaskManager.dto.UserTaskDTO;
import teamTaskManager.dto.UserTaskResponseDTO;
import teamTaskManager.repository.UserTasksRepository;

@Service
public class UserTasksService {
  @Autowired
  UserTasksRepository userTasksRepository;
  // Obtiene el listado de tareas
    public List<UserTaskResponseDTO> getAllUserTasks() {
      List<UserTask> userTasks = userTasksRepository.findAll();
      return userTasks.stream()
                      .map(this::convertTUserTaskResponseDTO)
                      .collect(Collectors.toList());
    }
  // Obtiene los usuarios que no tienen tareas asignadas
    public List<UserTaskResponseDTO> getUserTasksWithoutTasks() {
      List<UserTask> userTasks = userTasksRepository.findByTasksIsEmpty();
      return userTasks.stream()
                      .map(this::convertTUserTaskResponseDTO)
                      .collect(Collectors.toList());
    }
  // Obtiene los usuarios que tienen tareas asignadas
    public List<UserTaskResponseDTO> getUserTasksWithTasks() {
      List<UserTask> userTasks = userTasksRepository.findByTasksIsNotEmpty();
      return userTasks.stream()
                      .map(this::convertTUserTaskResponseDTO)
                      .collect(Collectors.toList());
    }
  // Crea los usuarios si no existe el mismo nombre de usuario
    public UserTask createUserTask(UserTaskDTO userTaskDto) throws IllegalArgumentException {
      if (userTasksRepository.findByUserNameIgnoreCase(userTaskDto.getUserName()).isPresent())
        throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario");
      UserTask userTask = new UserTask();
      userTask.setName(userTaskDto.getName());
      userTask.setUserName(userTaskDto.getUserName());
      userTask.setPassword(userTaskDto.getPassword());
      return userTasksRepository.save(userTask);
    }
  // Actualiza los datos del usuario
    public UserTask updateUserTask(Long id, UserTaskDTO userTaskDto) throws IllegalArgumentException {
      UserTask userTask = userTasksRepository.findById(id)
                          .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));
      if (userTasksRepository.findByUserNameIgnoreCaseAndIdNot(userTaskDto.getUserName(), id).isPresent())
        throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario");
      else {
        userTask.setName(userTaskDto.getName());
        userTask.setUserName(userTaskDto.getUserName());
        userTask.setPassword(userTaskDto.getPassword());
      }
      return userTasksRepository.save(userTask);
    }
  // Elimina el usuario si no tiene tareas asignadas
    public void deleteUserTask(Long id) throws IllegalStateException {
      UserTask userTask = userTasksRepository.findById(id)
                          .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));
      if (userTask.getTasks() != null && !userTask.getTasks().isEmpty())
        throw new IllegalStateException("No se puede eliminar el usuario porque tiene tareas asociadas");
      userTasksRepository.delete(userTask);
    }
  // Convierte una entidad JPA (Project) a un DTO de respuesta (ProjectResponseDTO)
    public UserTaskResponseDTO convertTUserTaskResponseDTO(UserTask userTask) {
      UserTaskResponseDTO dto        = new UserTaskResponseDTO();
      List<TaskResponseDTO> taskName = userTask.getTasks().stream()
                                                          .map(this::convertToResponseTasks)
                                                          .collect(Collectors.toList());
      dto.setId(userTask.getId());
      dto.setName(userTask.getName());
      dto.setUserName(userTask.getUserName());
      dto.setTasks(taskName);
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
