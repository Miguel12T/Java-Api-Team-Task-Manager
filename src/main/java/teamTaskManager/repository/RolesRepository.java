package teamTaskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teamTaskManager.domain.Role;
import teamTaskManager.enums.RoleList;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleList name);
}
