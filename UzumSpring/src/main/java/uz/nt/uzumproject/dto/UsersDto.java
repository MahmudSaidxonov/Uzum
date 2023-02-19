package uz.nt.uzumproject.dto;

import lombok.*;
import uz.nt.uzumproject.service.validator.IsValidGender;
import uz.nt.uzumproject.service.validator.IsValidPhoneNumber;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    @IsValidPhoneNumber
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    @IsValidGender
    private String gender;
    private Date birthDate;
    private String password;
    private Boolean enabled;
}