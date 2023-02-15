package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.ProductValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        List<ErrorDto> errors = productValidator.validateProduct(productDto);
        if (!errors.isEmpty()) {

            return ResponseDto.<ProductDto>builder()
                    .errors(errors)
                    .code(VALIDATION_ERROR_CODE)
                    .message(VALIDATION_ERROR)
                    .data(productDto)
                    .build();
        }
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
        return ResponseDto.<ProductDto>builder()
                .success(true)
                .code(OK_CODE)
                .data(productMapper.toDto(product))
                .message(OK)
                .build();


    }

    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Product> optional = productRepository.findById(productDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message("Product with ID " + productDto.getId() + " is not found!")
                    .build();
        }

        Product product = optional.get();

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null && productDto.getPrice() > 0) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getAmount() != null && productDto.getAmount() > 0) {
            product.setIsAvailable(true);
            product.setAmount(productDto.getAmount());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
//        if (productDto.getImageUrl() != null) {
//            product.setImages(productDto.getImageUrl());
//        }
        try {

            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message(OK)
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message("Error while saving product: " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    public ResponseDto<List<ProductDto>> getAllProducts() {
        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data( productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList()))
                .build();
    }
    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(products -> ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(products))
                        .success(true)
                        .message(OK)
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .message("Product with id " + id + " not found")
                        .code(NOT_FOUND_ERROR_CODE)
                        .build()
                );
    }
}
