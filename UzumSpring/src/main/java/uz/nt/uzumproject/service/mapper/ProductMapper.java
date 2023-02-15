package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;

@Mapper(componentModel = "spring")
public abstract class ProductMapper implements CommonMapper<ProductDto, Product> {

    @Autowired
    protected CategoryMapper categoryMapper;
    @Mapping(target = "category", expression = "java(categoryMapper.toDto(product.getCategory()))")
    public abstract ProductDto toDto(Product product);
}
