package uz.nt.uzumproject.repository;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
@SecurityRequirement(name = "Authorization")

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product t where (t.category_id, t.price) in (select p.category_id, max(p.price) from product p\n" +
            "group by p.category_id)",
            nativeQuery = true)
    List<Product> getExpensiveProducts();

    @Query(value = "select t from Product t where (t.category.id, t.price) in (select p.category.id, max(p.price) from Product p\n" +
            "group by p.category.id)")
    List<Product> getExpensiveProducts2();

    @Query(name = "findProductById")
    List<Product> findProductById(@Param("id") Integer id,
                                  @Param("name") String name,
                                  @Param("amount") Integer amount,
                                  @Param("price") Integer price);

    List<Product> findAllByAmountLessThanEqual(Integer amount);
}
