package teamTaskManager.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamTaskManager.enums.RoleList;

@Data // Crea getters y setters
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con argumentos
@Entity
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private RoleList name;
}
