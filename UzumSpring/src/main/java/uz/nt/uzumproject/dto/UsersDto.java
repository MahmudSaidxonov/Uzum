package uz.nt.uzumproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.nt.uzumproject.security.UserRoles;
import uz.nt.uzumproject.service.validator.IsValidGender;
import uz.nt.uzumproject.service.validator.IsValidPhoneNumber;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password", "role", "authorities", "username"}, allowSetters = true)
public class UsersDto implements UserDetails {
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
//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private Boolean enabled;
    private String role; // = "USER";

    @Override
//    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRoles.valueOf(role)
                .getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}