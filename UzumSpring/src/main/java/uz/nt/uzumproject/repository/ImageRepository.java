package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
