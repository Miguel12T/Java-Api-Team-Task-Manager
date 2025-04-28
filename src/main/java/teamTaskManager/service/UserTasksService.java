package teamTaskManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.UserTask;
import teamTaskManager.repository.UserTasksRepository;

@Service
public class UserTasksService {
  @Autowired
  UserTasksRepository userTasksRepository;
  // Obtiene el listado de tareas
    public List<UserTask> getAllUserTasks() {
      return userTasksRepository.findAll();
    }
  // Obtiene los usuarios que no tienen tareas asignadas
    public List<UserTask> getUserTasksWithoutTasks() {
      return userTasksRepository.findByTasksIsEmpty();
    }
  // Obtiene los usuarios que tienen tareas asignadas
    public List<UserTask> getUserTasksWithTasks() {
      return userTasksRepository.findByTasksIsNotEmpty();
    }
  // Crea los usuarios si no existe el mismo nombre de usuario
    public UserTask createUserTask(UserTask userTask) throws IllegalArgumentException {
      if (userTasksRepository.findByUserNameIgnoreCase(userTask.getUserName()).isPresent())
        throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario");
      return userTasksRepository.save(userTask);
    }
  // Actualiza los datos del usuario
    public UserTask updateUserTask(Long id, UserTask userTaskEdit) throws IllegalArgumentException {
      UserTask userTask = userTasksRepository.findById(id)
                          .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));
      if (userTasksRepository.findByUserNameIgnoreCaseAndIdNot(userTaskEdit.getUserName(), id).isPresent())
        throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario");
      else {
        userTask.setName(userTaskEdit.getName());
        userTask.setUserName(userTaskEdit.getUserName());
        userTask.setPassword(userTaskEdit.getPassword());
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
}
