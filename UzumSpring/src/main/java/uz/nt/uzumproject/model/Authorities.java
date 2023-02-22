package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import uz.nt.uzumproject.service.AuthorityService;

@Entity
@Getter
@Setter
@Table(
        name = "authorities",
        uniqueConstraints = @UniqueConstraint(name = "username_auth_unique",
        columnNames = {"username", "authority"})
)
@AllArgsConstructor
@NoArgsConstructor
public class Authorities {
    @Id
    @GeneratedValue(generator = "auth_id_seq")
    @SequenceGenerator(name = "auth_id_seq", sequenceName = "auth_id_seq", allocationSize = 1)
    private Integer id;
    private String username;
    private String authority;

    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}
