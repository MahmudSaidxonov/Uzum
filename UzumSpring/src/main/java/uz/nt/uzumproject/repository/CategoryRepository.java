package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.model.Users;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    List<Category> findAllByParentId(Integer categoryId);
}
