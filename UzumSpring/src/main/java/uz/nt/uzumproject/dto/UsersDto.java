package uz.nt.uzumproject.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private Date birthDate;
}