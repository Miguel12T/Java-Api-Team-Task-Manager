package teamTaskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import teamTaskManager.domain.UserTask;

public interface UserTasksRepository extends JpaRepository<UserTask, Long> {
  // Busca usuarios por el userName
    Optional<UserTask> findByUserNameIgnoreCase(String userName);
}
