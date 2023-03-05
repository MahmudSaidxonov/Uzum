package uz.nt.uzumproject.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @GetMapping()
    public ResponseDto<List<ProductDto>>getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("by-id")
    public ResponseDto<ProductDto>getProductById(@RequestParam Integer id, HttpServletRequest req){
        String authorization = req.getHeader("Authorization");
        return productService.getProductById(id);
    }

    @GetMapping("/expensive-by-category")
    public ResponseDto<List<ProductDto>> getExpensiveProducts() {
        return productService.getExpensiveProducts();
    }

    @GetMapping("search")
    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto){
        return productService.universalSearch(productDto);
    }

    @GetMapping("search-2")
    public ResponseDto<List<ProductDto>> universalSearch(@RequestParam Map<String, String> params){
        return productService.universalSearch2(params);
    }

}
