package uz.nt.uzumproject.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(generator = "productIdSeq")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    @OneToMany(mappedBy = "product")
    private List<Image> images;
    @ManyToOne
    private Category category;
    private Boolean isAvailable;
}
//1. Product qo'shish
//2. Bor productni o'zgartirish
//3. Umumiy productlar ro'yxatini qaytarish
