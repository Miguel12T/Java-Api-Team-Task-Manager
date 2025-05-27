package teamTaskManager.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import teamTaskManager.jwt.JwtAuthenticationFilter;
import teamTaskManager.jwt.JwtEntryPoint;
import teamTaskManager.service.UsersService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  /**
   * Configura la cadena de filtros de seguridad de Spring Security.
   *
   * - Habilita CORS con configuración por defecto.
   * - Desactiva CSRF (recomendado para APIs REST con JWT).
   * - Permite acceso libre a las rutas de login y registro.
   * - Requiere autenticación para cualquier otra solicitud.
   * - Usa autenticación básica como mecanismo por defecto (opcional).
   * - Establece un manejador de errores personalizado para JWT.
   * - Agrega un filtro personalizado para procesar tokens JWT antes del filtro de autenticación.
   *
   * @param http objeto HttpSecurity para configurar la seguridad web.
   * @return el filtro de seguridad configurado.
   * @throws Exception en caso de error en la configuración.
   */
  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults()) // Habilita CORS con configuración por defecto (usa corsConfigurationSource si está declarado).
        .csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF (no necesario para APIs con JWT).
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/teamtask/api/v1/auth/login", "/teamtask/api/v1/auth/register")
          .permitAll() // Permite el acceso público a login y registro.
          .anyRequest()
          .authenticated() // Requiere autenticación para cualquier otra ruta.
        )
        .httpBasic(Customizer.withDefaults()) // Habilita autenticación HTTP básica (útil para pruebas, puede omitirse).
        .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint())) // Manejador personalizado de errores de autenticación.
        .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Agrega el filtro de JWT antes del filtro de usuario/contraseña.
    return http.build(); // Devuelve la configuración final.
  }
  /**
   * Define el filtro personalizado que intercepta las peticiones para extraer
   * y validar el token JWT antes de que llegue al filtro de autenticación.
   *
   * @return instancia del filtro JwtAuthenticationFilter.
   */
  @Bean
  public JwtAuthenticationFilter jwtTokenFilter() {
    return new JwtAuthenticationFilter();
  }
  /**
   * Manejador personalizado que responde cuando un usuario intenta acceder a una ruta
   * protegida sin autenticación válida.
   *
   * @return instancia de JwtEntryPoint que maneja errores de autenticación.
   */
  @Bean
  public JwtEntryPoint jwtEntryPoint() {
    return new JwtEntryPoint();
  }
  /**
   * Codificador de contraseñas que usa BCrypt, recomendado por Spring Security.
   * Es utilizado al guardar y comparar contraseñas de usuarios.
   *
   * @return instancia de BCryptPasswordEncoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  /**
   * Servicio que proporciona la lógica para cargar usuarios desde la base de datos.
   * Es utilizado por el proveedor de autenticación.
   *
   * @return implementación personalizada de UserDetailsService.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return new UsersService();
  }
  /**
   * Proveedor de autenticación que usa DAO y el servicio de usuarios definido.
   * Compara las credenciales proporcionadas con las almacenadas, usando el encoder definido.
   *
   * @return instancia de AuthenticationProvider con el encoder y el servicio de usuarios.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }
  /**
   * Configura la política de CORS (Cross-Origin Resource Sharing) para la aplicación.
   *
   * Esta configuración permite que el frontend ubicado en http://localhost:4200
   * pueda comunicarse con el backend, permitiendo ciertos métodos y encabezados.
   *
   * - Origen permitido: http://localhost:4200
   * - Métodos permitidos: GET, POST, PUT, DELETE, OPTIONS
   * - Encabezados permitidos: Authorization, Content-Type
   * - Se permite el envío de credenciales (cookies, tokens, etc.)
   *
   * @return CorsConfigurationSource con la configuración aplicada a todas las rutas.
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Permite solicitudes solo desde este origen (el frontend)
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos desde el frontend
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Encabezados que puede enviar el frontend (como JWT en Authorization)
    configuration.setAllowCredentials(true); // Permite enviar cookies/tokens en las solicitudes (útil para JWT en headers)
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Aplica esta configuración a todas las rutas del backen
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
