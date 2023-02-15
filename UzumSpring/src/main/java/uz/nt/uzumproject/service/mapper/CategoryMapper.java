package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends CommonMapper<CategoryDto, Category> {
}
