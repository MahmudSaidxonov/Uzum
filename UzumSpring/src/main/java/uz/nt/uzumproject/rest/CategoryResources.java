package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryResources {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping
    public ResponseDto<List<CategoryDto>> listCategory(@RequestParam Integer categoryId) {
        return categoryService.listCategory(categoryId);
    }
}
