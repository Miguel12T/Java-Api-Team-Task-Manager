package teamTaskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teamTaskManager.domain.User;

@Repository
public interface UsersRespository extends JpaRepository<User, String> {
  Optional<User> findByUserName(String userName);
}
