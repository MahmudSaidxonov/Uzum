package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Authorities;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authorities, String> {
    List<Authorities> findAllByUsername(String username);
    boolean existsByUsernameAndAuthority(String username, String auth);
}
