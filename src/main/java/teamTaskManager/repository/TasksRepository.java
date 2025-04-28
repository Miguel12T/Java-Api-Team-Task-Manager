package teamTaskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import teamTaskManager.domain.Task;

public interface TasksRepository extends JpaRepository<Task, Long> {
  // Busca tareas por el nameTask excepto las de otro proyecto
    Optional<Task> findByNameTaskIgnoreCaseAndProjectId(String nameTask, Long projectId);
}
