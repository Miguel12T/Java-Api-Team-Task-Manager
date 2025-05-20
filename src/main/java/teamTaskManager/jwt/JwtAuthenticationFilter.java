package teamTaskManager.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import teamTaskManager.service.UsersService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private JwtUtil jwtUtil;
  private UsersService usersService;
  public JwtAuthenticationFilter(JwtUtil jwtUtil, UsersService usersService) {
    this.jwtUtil      = jwtUtil;
    this.usersService = usersService;
  }
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");
    String userName = null;
    String jwt      = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      userName = jwtUtil.extractUserName(jwt);
    }
    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = usersService.loadUserByUsername(userName);
      if (jwtUtil.validateToken(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
