package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.service.mapper.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = userMapper.toEntity(dto);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(userMapper.toDto(users))
                .message("OK")
                .build();
    }

    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message("UserID is null")
                    .code(-2)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findById(usersDto.getId());

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message("User with ID " + usersDto.getId() + " is not found")
                    .code(-1)
                    .data(usersDto)
                    .build();
        }
        Users user = userOptional.get();
        if (usersDto.getGender() != null){
            user.setGender(usersDto.getGender());
        }
        if (usersDto.getEmail() != null){
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getLastName() != null){
            user.setLastName(usersDto.getLastName());
        }
        //...
        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .success(true)
                    .message("OK")
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumber(phoneNumber)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(userMapper.toDto(u))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }
}