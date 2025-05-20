package teamTaskManager.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import teamTaskManager.domain.User;
import teamTaskManager.repository.UsersRespository;

public class UsersService implements UserDetailsService {
  private UsersRespository usersRespository;
  public UsersService(UsersRespository usersRespository) {
    this.usersRespository = usersRespository;
  }
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
}
