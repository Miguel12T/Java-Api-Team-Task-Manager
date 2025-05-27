package teamTaskManager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import teamTaskManager.dto.LoginUserDTO;
import teamTaskManager.dto.NewUserDTO;
import teamTaskManager.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;
  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }
  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Revise sus credenciales");
    }
    try {
      String jwt = authService.authenticate(loginUserDTO.userName, loginUserDTO.getPassword());
      return ResponseEntity.ok(jwt);
    }
    catch (Exception e) {
      return ResponseEntity.badRequest().body("Revise sus credenciales");
    }
  }
  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody NewUserDTO newUserDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Revise los campos");
    }
    try {
      authService.registerUser(newUserDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
    }
    catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @GetMapping("/check-auth")
  public ResponseEntity<String> checkAuth() {
    return ResponseEntity.ok().body("Atenticado");
  }
}
