package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product p where (p.price, p.category_id) \n" +
            "in (select max(p1.price), p1.category_id from product p1 group by p1.category_id)"
    , nativeQuery = true)
    List<Product> getExpensiveProducts();
}
