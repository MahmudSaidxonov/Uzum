package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {

        if (categoryDto.getParentId() == null) {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(OK)
                    .success(true)
                    .build();
        }

        Optional<Category> optional = categoryRepository.findById(categoryDto.getParentId());
        if (optional.isEmpty()) {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto((
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(VALIDATION_ERROR)
                    .success(false)
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(OK)
                    .success(true)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }


    public ResponseDto<List<CategoryDto>> listCategory(Integer categoryId) {
        List<Category> list = new ArrayList<>(categoryRepository.findAllByParentId(categoryId));
        return  ResponseDto.<List<CategoryDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(list.stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                .build();
    }
}
