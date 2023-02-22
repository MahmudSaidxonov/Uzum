package uz.nt.uzumproject.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.AuthorityRepository;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.security.JwtService;
import uz.nt.uzumproject.security.UserAuthorities;
import uz.nt.uzumproject.service.mapper.UserMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final AuthorityRepository authorityRepository;
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = userMapper.toEntity(dto);
        usersRepository.save(users);
//        users.setPassword(passwordEncoder.encode(dto.getPassword()));
//        authorityRepository.save(new Authorities(users.getUsername(), UserAuthorities.READ));
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

    @Override
    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findFirstByEmail(username);
        if (users.isEmpty()) throw new UsernameNotFoundException("User with email " +  username + " is not found");

        return userMapper.toDto(users.get());
    }

    public ResponseDto<String> login(LoginDto loginDto) {
        UsersDto users = loadUserByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())) {
            return ResponseDto .<String>builder()
                    .message("Password is not correct")
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<String>builder()
                .success(true)
                .message(AppStatusMessages.OK)
                .data(jwtService.generateToken(users))
                .build();
    }
}