package teamTaskManager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import teamTaskManager.domain.UserTask;

public interface UserTasksRepository extends JpaRepository<UserTask, Long> {
  // Busca usuarios por el userName excepto el mismo se que esta actualizando
    Optional<UserTask> findByUserNameIgnoreCaseAndIdNot(String userName, Long id);
  // Busca usuarios por el userName
    Optional<UserTask> findByUserNameIgnoreCase(String userName);
  // Busca usuarios sin tareas asignadas
    List<UserTask> findByTasksIsEmpty();
  // Busca usuarios con tareas asignadas
    List<UserTask> findByTasksIsNotEmpty();
}
