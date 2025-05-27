package teamTaskManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import teamTaskManager.domain.Role;
import teamTaskManager.domain.User;
import teamTaskManager.dto.NewUserDTO;
import teamTaskManager.enums.RoleList;
import teamTaskManager.jwt.JwtUtil;
import teamTaskManager.repository.RolesRepository;

@Service
public class AuthService {
  private final UsersService usersService;
  private final RolesRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  @Autowired
  public AuthService(UsersService usersService, RolesRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
    this.usersService = usersService;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
  }
  public String authenticate(String username, String password) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authResult);
    return jwtUtil.generateToken(authResult);
  }
  public void registerUser(NewUserDTO newUserDTO) {
    if (usersService.existsByUserName(newUserDTO.getUserName())) {
      throw new IllegalArgumentException("El nombre de usuario ya existe");
    }
    Role roleUser = roleRepository.findByName(RoleList.ROLE_USER).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    User user = new User(newUserDTO.getUserName(), passwordEncoder.encode(newUserDTO.getPassword()), roleUser);
    usersService.save(user);
  }
}
