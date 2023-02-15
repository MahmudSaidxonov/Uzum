package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toEntity(UsersDto dto);
    UsersDto toDto(Users entity);
}
