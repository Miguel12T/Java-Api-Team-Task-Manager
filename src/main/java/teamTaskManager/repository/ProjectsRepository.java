package teamTaskManager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import teamTaskManager.domain.Project;

public interface ProjectsRepository extends JpaRepository<Project, Long> {
  // Busca por nombre (ignora mayúsculas/minúsculas)
    Optional<Project> findByNameIgnoreCase(String name);
  // Proyectos que tienen tareas
    List<Project> findByTasksIsNotEmpty();
  // Proyectos sin tareas (útil para eliminar de forma segura)
    List<Project> findByTasksIsEmpty();
  // Proyectos que contienen cierto texto en el nombre
    List<Project> findByNameContainingIgnoreCase(String keyword);
}