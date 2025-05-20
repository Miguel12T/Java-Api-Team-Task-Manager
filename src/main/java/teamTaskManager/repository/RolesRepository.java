package teamTaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teamTaskManager.domain.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

}
