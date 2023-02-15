package uz.nt.uzumproject.service.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@AllArgsConstructor
@Component
public class ProductValidator {
    public List<ErrorDto> validateProduct(ProductDto dto) {

        List<ErrorDto> errors = new ArrayList<>();

        if (dto.getName() == null) errors.add(new ErrorDto("name", NULL_VALUE));
        else if (dto.getName().trim().length() == 0) errors.add(new ErrorDto("name", EMPTY_STRING));
        if (dto.getPrice() == null) errors.add(new ErrorDto("price", NULL_VALUE));
        else if (dto.getPrice() <= 0) errors.add(new ErrorDto("price", NEGATIVE_VALUE));
        if (dto.getDescription() == null) errors.add(new ErrorDto("description", NULL_VALUE));
        else if (dto.getDescription().trim().length() == 0) errors.add(new ErrorDto("description", EMPTY_STRING));
        if (dto.getAmount() == null) errors.add(new ErrorDto("amount", NULL_VALUE));
        else if (dto.getAmount() <= 0) errors.add(new ErrorDto("amount", NEGATIVE_VALUE));

        return errors;
    }
}
