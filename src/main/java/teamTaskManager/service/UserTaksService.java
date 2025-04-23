package teamTaskManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.UserTask;
import teamTaskManager.repository.UserTasksRepository;

@Service
public class UserTaksService {
  @Autowired
  UserTasksRepository userTasksRepository;
  // Obtiene el listado de tareas
    public List<UserTask> getAllUserTasks() {
      return userTasksRepository.findAll();
    }
  // Crea los usuarios si no existe el mismo nombre de usuario
    public UserTask createUserTask(UserTask userTask) throws IllegalArgumentException {
      if (userTasksRepository.findByUserNameIgnoreCase(userTask.getUserName()).isPresent())
        throw new IllegalArgumentException("Ya existe un usuario con ese user name");
      return userTasksRepository.save(userTask);
    }
}
