package teamTaskManager.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import teamTaskManager.domain.User;
import teamTaskManager.repository.UsersRespository;

@NoArgsConstructor
@Service
public class UsersService implements UserDetailsService {
  @Autowired
  private UsersRespository usersRespository;
  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = usersRespository.findByUserName(userName)
                 .orElseThrow(() -> new  UsernameNotFoundException("User not found"));
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().toString());
    return new org.springframework.security.core.userdetails.User(
      user.getUserName(),
      user.getPassword(),
      Collections.singleton(authority)
    );
  }
  public boolean existsByUserName(String username) {
    return usersRespository.existsByUserName(username);
  }
  public void save(User user) {
    usersRespository.save(user);
  }
}
