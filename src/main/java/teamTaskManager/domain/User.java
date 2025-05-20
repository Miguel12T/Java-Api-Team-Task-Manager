package teamTaskManager.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data // Crea getters y setters
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con argumentos
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID) // El identificador es de tipo alfanumerico ej:
  private String id;
  @NotBlank // Indica que la columna no esté vacía ni contenga solo espacios en blanco
  @Column(unique = true, nullable = false) // La columna es unica no puede ser nula
  private String userName;
  @NotBlank
  @Column(nullable = false)
  private String password;
  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;
}
