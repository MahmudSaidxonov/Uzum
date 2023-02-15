package uz.nt.uzumproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
    /**
     * Response code for defining type of error:
     * <p> -2 - Validation error </p>
     * <p> -1 - Not found </p>
     * <p> 0 - OK </p>
     * <p> 1 - Database error </p>
     * <p>2 - Unexpected error </p>
     */
    private Integer code;
    private String message;
    private boolean success;
    private T data;
    private List<ErrorDto> errors;
}